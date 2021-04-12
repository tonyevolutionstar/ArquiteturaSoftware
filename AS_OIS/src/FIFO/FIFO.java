package FIFO;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Não pretende ser a versão final do FIFO. Cada grupo deve usar o FIFO que mais 
 * lhe convier. Tb pode e deve alterar o aqui apresentado.
 * 
 * Nesta versão, os Customers entram no fifo e dp ficam a aguardar autorização
 * para sair. Tudo isto ocorre no método in.
 * 
 * A autorização para sair é dada pela Manager em out. Dp de ser dada a 
 * autorização para sair, Manager fica a aguardar que o Customer confirme a
 * saída.
 * 
 * No caso de o fifo ser criado com a dimensão = 99, não seria necessário 
 * verificar se está cheio.
 * 
 * @author omp
 */
public class FIFO implements IFIFO {
       
    // lock para acesso à área partilhada 
    private final ReentrantLock rl = new ReentrantLock( true );

    // array para ids dos customers
    private final int customerId[];
    // array para Condition de bloqueio (uma por customer)
    private final Condition cStay[];
    // Conditio  de bloqueio de fifo cheio
    private final Condition cFull;
    // Condition de bloqueio de fifo vazio
    private final Condition cEmpty;
    // Condition de bloqueio para aguardar saída do fifo
    private final Condition cLeaving;
    // array para condição de bloqueio de cada customer
    private final boolean leave[];
    // número máximo de customer (dimensão dos arrays)
    private final int maxCustomers;
    
    // próxima posição de escrita no fifo
    private int idxIn;
    // próxima posição de leitura no fifo
    private int idxOut;
    // número de customers no fifo
    private int count = 0;

    
    private static int id_in = 0;
    private static int id_out = 0;
    
    public FIFO( int maxCustomers ) {
        this.maxCustomers = maxCustomers;
        customerId = new int[ maxCustomers ];
        cStay = new Condition[ maxCustomers ];
        leave = new boolean[ maxCustomers ];
        for ( int i = 0; i < maxCustomers; i++ ) {
            cStay[ i ] = rl.newCondition();
            leave[ i ] = false;
        }
        cFull = rl.newCondition();
        cEmpty = rl.newCondition();
        cLeaving = rl.newCondition();
        idxIn = 0;
        idxOut = 0; 
    }
    // Entrada no fifo. NOTA: o thead Customer pode ficar bloqueado à espera de 
    // autorização para sair do fifo
    @Override
    public int in( int customerId ) {
        // variáveis locais
        int idx;
        int id = 0;
        try {
            // garantir acesso em exclusividade
            rl.lock();
                                  
            // se fifo cheio, espera na Condition cFull
            while ( count == maxCustomers )
                cFull.await();
                                   
            // esta operação não pode ser feita antes da anterior para
            // garantir q o idxIn utilizado é apenas deste Thread activo
            idx = idxIn;
            // incrementar apontador de escrita
            idxIn = (++idxIn) % maxCustomers;
            // inserir customer no fifo
            this.customerId[ idx ] = customerId;
            
            // o fifo poderá estar vazio, pelo q neste caso a Customer poderá
            // estar à espera q um Customer chegue. Necessério avisar Manager
            // q se encontra em espera na Condition cEmpty
            if ( count == 0 )
                cEmpty.signal();
            
            // incrementar número customers no fifo
            count++;
            
            // ciclo à espera de autorização para sair do fifo
            while ( !leave[ idx ] )
                // qd se faz await, permite-se q outros thread tenham acesso
                // à zona protegida pelo lock
                cStay[ idx ].await();

            // id do Customer q está a sair do fifo
            id = this.customerId[ idx ];
                    
            // atualizar variável de bloqueio
            leave[ idx ] = false;
            // avisar Manager que Customer vai sair. Manager está à espera na
            // Condition cLeaving
            cLeaving.signal();
            
            
            
            // testar se Customer q vai sair é o q está há mais tempo no fifo
            assert idx == ( idxOut == 0 ? maxCustomers - 1 : idxOut - 1 );
            // testar se o id do fifo corresponde ao id do Thread (Customer)
            assert customerId == id;
            
            
            
            // se fifo estava cheio, acordar Customer q esteja à espera de entrar
            if ( count == maxCustomers )
                cFull.signalAll();
            // decrementar número de customers no fifo
            count--;
            
        } catch ( Exception ex ) {}
        finally {
            rl.unlock();
        }
        // Customer a sair não só do fifo como tb a permitir q outros
        // threads possam entrar na zona crítica
        return id;

    }
    // acordar o customer q há mais tempo está no fifo (sem ter sido desbloqueado!)
    @Override
    public void out() {
        try {
            rl.lock();
            // se fifo vazio, espera
            while ( count == 0 )
                cEmpty.await();
            int idx = idxOut;
            // atualizar idxOut
            idxOut = (++idxOut) % maxCustomers; 
            // autorizar a saída do customer q há mais tempo está no fifo
            leave[ idx ] = true;
            // acordar o customer
            cStay[ idx ].signal();
            // aguardar até q Customer saia do fifo
            while ( leave[ idx ] == true )
                // qd se faz await, permite-se q outros thread tenham acesso
                // à zona protegida pelo lock
                cLeaving.await();  

        } catch ( Exception ex ) {}
        finally {
            rl.unlock();
        }
    }
}
