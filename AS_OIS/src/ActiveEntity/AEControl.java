package ActiveEntity;

import SACorridor.SACorridor;
import SACorridorHall.SACorridorHall;
import SAEntranceHall.SAEntranceHall;
import SAIdle.IIdle_Control;
import SAIdle.SAIdle;
import SAOutsideHall.SAOutsideHall;
import SAPaymentHall.SAPaymentHall;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AEControl extends Thread {

    private final IIdle_Control idle;
    private final SAOutsideHall outsideHall;
    private final SAEntranceHall entranceHall;
    private final SACorridorHall [] corridorHalls;
    private final SACorridor [] corridors;
    private final SAPaymentHall paymentHall;
    private final AECustomer[] aeCustomer;
    private final AEManager aeManager;
    private final AECashier aeCashier;
    private int signalToSTOP = 1;
    


    public AEControl(AECustomer[] aeCustomer, AEManager aeManager, AECashier aeCashier, SAIdle idle, SAOutsideHall outsideHall, SAEntranceHall entranceHall,SACorridorHall [] corridorHalls, SACorridor [] corridors, SAPaymentHall paymentHall) {
        this.idle = idle;
        this.outsideHall = outsideHall;
        this.entranceHall = entranceHall;
        this.corridorHalls = corridorHalls;
        this.corridors = corridors;
        this.paymentHall = paymentHall;
        this.aeCashier = aeCashier;
        this.aeCustomer = aeCustomer;
        this.aeManager = aeManager;
    }
    
    /**
     * Function that starts the simulation
     * @param nCustomers Number of costumer to start
     */
    public void start( int nCustomers ) {
        aeManager.resume();
        aeCashier.resume();
        idle.start( nCustomers );
    }
    
    /**
     * Terminates the Simulation
     */
    public void end() {
        System.exit(0);
    }
    
    /**
     * Suspends the currentShopping
     */
    public void suspendShopping()
    {
        aeManager.suspend();
        for(int i=0;i<aeCustomer.length;i++)
        {
            aeCustomer[i].suspend();
        }

        aeCashier.suspend();
        
    }
    
    /**
     * Resumes the pausedShopping
     */
    public void resumeShopping() {
        aeManager.resume();
        for(int i=0;i<aeCustomer.length;i++)
        {
            aeCustomer[i].resume();
        }
        
        aeCashier.resume();
    }
    
    /**
     * Function that stops All the operations of costumers
     */
    public void stopShopping()
    {
        for(int i=0;i<aeCustomer.length;i++)
           {
               aeCustomer[i].changeCostumerStateIdle();
           }
        while(this.outsideHall.getNumberOfCostumers()!=1)
        {        
            this.outsideHall.call();
        }

        while(this.entranceHall.getNumberOfCostumers()!=0)
        {
            this.entranceHall.call();
        }  
        while(this.corridorHalls[0].getNumberOfCostumers()!=0)
        {
            this.corridorHalls[0].out();
        }
        while(this.corridorHalls[1].getNumberOfCostumers()!=0)
        {
            this.corridorHalls[1].out();
        }
        while(this.corridorHalls[2].getNumberOfCostumers()!=0)
        {
            this.corridorHalls[2].out();
        }    
        while(this.paymentHall.getNumberOfCostumers()!=0)
        {
            this.paymentHall.call();
        }  
        for(int i=0;i<3;i++)
        {
            this.corridors[i].cleanCorridors();
        }        
    }
    
    
    
    @Override
    public void run() {
        
        //RECEBER DADOS DO SOCKET
        try {
            sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(AEControl.class.getName()).log(Level.SEVERE, null, ex);
        }
            this.start(50);  
        try {
            sleep(350000);
        } catch (InterruptedException ex) {
            Logger.getLogger(AEControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
        if(signalToSTOP==1)
        {
            this.aeCashier.suspend();
            this.aeManager.suspend();            
            //THIS SLEEP IS CRITITAL, SO IT CAN GIVE TIME TO COSTUMERS TO FINISH WHAT THEY WERE DOING
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(AEControl.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.stopShopping();     
            signalToSTOP=0;
        }
        try {
            sleep(2000);
                    } catch (InterruptedException ex) {
            Logger.getLogger(AEControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.start(50);
            */
        
            
            /*     try {
            sleep(1500);
            } catch (InterruptedException ex) {
            Logger.getLogger(AEControl.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.suspendShopping();
            try {
            sleep(10000);
            } catch (InterruptedException ex) {
            Logger.getLogger(AEControl.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.resumeShopping();
            try {
            sleep(10000);
            } catch (InterruptedException ex) {
            Logger.getLogger(AEControl.class.getName()).log(Level.SEVERE, null, ex);
            }   
            this.start(50);
            
            */
            
            
            
            /*
            try {
            sleep(2500);
            } catch (InterruptedException ex) {
            Logger.getLogger(AEControl.class.getName()).log(Level.SEVERE, null, ex);
            }
            // this.aeManager.letCostumerInEntranceHall();
            try {
            sleep(2500);
            } catch (InterruptedException ex) {
            Logger.getLogger(AEControl.class.getName()).log(Level.SEVERE, null, ex);
            }
            // this.aeManager.letCostumerInCorridorHall();
            try {
            sleep(2500);
            } catch (InterruptedException ex) {
            Logger.getLogger(AEControl.class.getName()).log(Level.SEVERE, null, ex);
            }
            // this.aeManager.letCostumerInEntranceHall();
            try {
            sleep(2500);
            } catch (InterruptedException ex) {
            Logger.getLogger(AEControl.class.getName()).log(Level.SEVERE, null, ex);
            }
            // this.aeManager.letCostumerInEntranceHall();
            try {
            sleep(2500);
            } catch (InterruptedException ex) {
            Logger.getLogger(AEControl.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.aeManager.letCostumerInCorridorHall();
            
            
            
            try {
            sleep(100000);
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
            } catch (InterruptedException ex) {
            Logger.getLogger(AEControl.class.getName()).log(Level.SEVERE, null, ex);
            }
            */
    }
}
