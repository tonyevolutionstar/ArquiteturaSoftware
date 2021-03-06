package ActiveEntity;

import SAEntranceHall.SAEntranceHall;
import SAIdle.IIdle_Control;
import SAIdle.SAIdle;
import SAOutsideHall.SAOutsideHall;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private final AECustomer[] aeCustomer;
    private final AEManager aeManager;
    private final AECashier[] aeCashier;

    public AEControl(AECustomer[] aeCustomer, AEManager aeManager, AECashier[] aeCashier, SAIdle idle, SAOutsideHall outsideHall, SAEntranceHall entranceHall) {
        this.idle = idle;
        this.outsideHall = outsideHall;
        this.entranceHall = entranceHall;
        this.aeCashier = aeCashier;
        this.aeCustomer = aeCustomer;
        this.aeManager = aeManager;
    }
    public void start( int nCustomers ) {
        idle.start( nCustomers );
    }
    public void end() {
        System.exit(0);
    }
    
    public void suspendShopping()
    {
        aeManager.suspend();
        for(int i=0;i<aeCustomer.length;i++)
        {
            aeCustomer[i].suspend();
        }
        for(int i=0;i<3;i++)
        {
            aeCashier[i].suspend();
        }
    }
    
    public void resumeShopping() {
        aeManager.resume();
        for(int i=0;i<aeCustomer.length;i++)
        {
            aeCustomer[i].resume();
        }
        for(int i=0;i<3;i++)
        {
            aeCashier[i].resume();
        }
    }
    
    public void stopShopping()
    {
        
    }
    
    
    
    @Override
    public void run() {
        
        //RECEBER DADOS DO SOCKET
        try {
            sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(AEControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.start(12);
     /*   try {
            sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(AEControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.exit(0);
        suspendShopping();
        try {
            sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(AEControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        resumeShopping();
        try {
            sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(AEControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        start(12);
*/
    }
}
