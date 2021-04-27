package FIFOCorridor;

import static java.lang.Thread.sleep;
import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class FIFOCorridor implements IFIFOCorridor {
       
    // lock para acesso à área partilhada 
    private final ReentrantLock rl = new ReentrantLock( true );

    // array para ids dos customers
    private final int customerId[];
    // array para Condition de bloqueio (uma por customer)
    private final Condition cStay[];
    // Condition  de bloqueio de fifo cheio
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
    
    private int totalCount = 0;

    
    private static int id_in = 0;
    private static int id_out = 0;
    
    public FIFOCorridor( int maxCustomers ) {
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
        for(int i=0;i<maxCustomers;i++)
        {
            this.customerId[i]=999;
        }
    }
    
    public int returnCount()
    {
        return count;
    }
    
    public boolean check()
    {
        return this.customerId[9]!=999;
    }
    
    @Override
    public boolean returnFirstSlot()
    {
        if(this.customerId[0]==999 && this.count <= 2)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //CORRIDORS
    @Override
    public int inCostumer( int customerId, int nTimesWalked, FIFOCorridor fifoCorridorHall) {
        // variáveis locais
        int idx;
        int id = 0;
        try {
            // garantir acesso em exclusividade
            rl.lock();
                           
            int stuck8 = 0;
            
            if(nTimesWalked == 0)
            {
                this.customerId[0] = customerId; 
                count++;
            }
            else
            {
                if(nTimesWalked == 8 && (this.customerId[9]!=999 ))
                {
                    stuck8 = 1;
                }
                this.customerId[nTimesWalked] = customerId;
                this.customerId[nTimesWalked-1] = 999;
            }
            if ( this.customerId[0] == 999 )
            {
                cFull.signalAll();
            }

            // incrementar número customers no fifo

            System.out.println("IN("+customerId+")"+Arrays.toString(this.customerId));
            System.out.println("TESTE-"+this.customerId[9]+"--"+count);
            
            //Verificar se é possivle chamar outro costumer para o corridor
            if(nTimesWalked >= 2 && count==1 &&fifoCorridorHall.returnCount()>0)
            {
                System.out.println("VAI CHAMAR OUTRO COSTUMER PRESO NO CORRIDORHALL--"+fifoCorridorHall.returnCount());
                fifoCorridorHall.outCostumer();
            }
            
            // ciclo à espera de autorização para sair do fifo
            if(this.customerId[9]==customerId || stuck8 == 1)
            {
                if(stuck8==1)
                {
                    while ( !leave[ 8 ] )
                    // qd se faz await, permite-se q outros thread tenham acesso
                    // à zona protegida pelo lock
                    cStay[ 8 ].await();

                }
                else
                {
                    while ( !leave[ 9 ] )
                   // qd se faz await, permite-se q outros thread tenham acesso
                   // à zona protegida pelo lock
                   cStay[ 9 ].await();

                   // id do Customer q está a sair do fifo
                   id = this.customerId[ 9 ];

                   // atualizar variável de bloqueio
                   leave[ 9 ] = false;
                   // avisar Manager que Customer vai sair. Manager está à espera na
                   // Condition cLeaving
                   cLeaving.signal();


    
                   this.customerId[9] = 999;
                   count--;   
                }
            }
        } catch ( Exception ex ) {}
        finally {
            rl.unlock();
        }
       
        return id;

    }
    //CORRIDOR HALLS
    @Override
    public int in( int customerId, int nextCountCorridor, boolean firstSlotOpen ) {
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
            {
                cEmpty.signal();
            }
                
            
            // incrementar número customers no fifo
            count++;
            totalCount++;
            
            
            // System.out.println("DENTRO COSTUMER "+customerId+"IDX"+idx+"------"+totalCount+firstSlotOpen+nextCountCorridor);
            // ciclo à espera de autorização para sair do fifo
            if(nextCountCorridor >= 1 || firstSlotOpen == false)
               {
                   while ( !leave[ idx ] )
                   // qd se faz await, permite-se q outros thread tenham acesso
                   // à zona protegida pelo lock
                   cStay[ idx ].await();  
               }   
            
            // id do Customer q está a sair do fifo
            id = this.customerId[ idx ];
                    
            // atualizar variável de bloqueio
            leave[ idx ] = false;
            // avisar Manager que Customer vai sair. Manager está à espera na
            // Condition cLeaving
            cLeaving.signal();
            
            
            // se fifo estava cheio, acordar Customer q esteja à espera de entrar
            if ( count == maxCustomers )
                cEmpty.signalAll();
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
    //CORRIDOR OUT
    @Override
    public void out() {
        try {
            rl.lock();
            
            while ( count == 0 )
                cEmpty.await();            
           
            if(this.customerId[8]!= 999)
            {
                leave [8] = true;
                cStay [8].signal();
            }
            
            // autorizar a saída do customer q há mais tempo está no fifo
            leave[ 9 ] = true;
            // acordar o customer
            cStay[ 9 ].signal();
            // aguardar até q Customer saia do fifo
            
            this.customerId[ 9 ] = 999;
            
            while ( leave[ 9 ] == true )
                // qd se faz await, permite-se q outros thread tenham acesso
                // à zona protegida pelo lock
                cLeaving.await();  
        } catch ( Exception ex ) {}
        finally {
            rl.unlock();
        }
    }
    
    @Override
    public void outCostumer() {
        try {
            rl.lock();
           
            // se fifo vazio, espera
            while ( count == 0 )
                cEmpty.await();
            int idx = idxOut;
            // atualizar idxOut
            idxOut = (++idxOut) % maxCustomers; 
                       // System.out.println("SAIR-"+idxOut);
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