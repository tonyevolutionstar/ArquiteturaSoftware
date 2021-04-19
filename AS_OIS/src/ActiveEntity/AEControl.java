package ActiveEntity;

import SAEntranceHall.SAEntranceHall;
import SAIdle.IIdle_Control;
import SAOutsideHall.SAOutsideHall;
import java.net.Socket;

/**
 * Esta entidade é responsável por fazer executar os comandos originados no OCC
 * tais como start, stop, end, etc....
 * 
 * @author omp
 */
public class AEControl extends Thread {

    private final IIdle_Control idle;
    private final SAOutsideHall outsideHall;
    private final SAEntranceHall entranceHall;
    
    public AEControl( IIdle_Control idle, SAOutsideHall outsideHall, SAEntranceHall entranceHall) {
        this.idle = idle;
        this.outsideHall = outsideHall;
        this.entranceHall = entranceHall;
    }
    public void start( int nCustomers, Socket socket ) {
        idle.start( nCustomers );
    }
    public void end() {
        // terminar Customers em idle
        idle.end();
        // terminar restantes Customers e outras AE
    }
    // mais comandos 
    
    
    @Override
    public void run() {
        // ver qual a msg recebida, executar comando e responder
    }
}
