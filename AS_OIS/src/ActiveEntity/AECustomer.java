package ActiveEntity;


import SACorridor.SACorridor;
import SACorridorHall.ICorridorHall_Customer;
import SAEntranceHall.IEntranceHall_Customer;
import SAIdle.IIdle_Customer;
import SAOutsideHall.IOutsideHall_Customer;
import SAPaymentHall.SAPaymentHall;
import java.util.logging.Level;
import java.util.logging.Logger;



public class AECustomer extends Thread { 
    
    // id of customer
    private final int customerId;   
    // state of costumer -1 for idle, 0 for outsideHall, 1 for entrancehall, 2 for corridorhall, 3 for corridor , 4 for paymenthall e 5 for paymentPoint e ir idle 
    private int whereTheCostumerIs = -1;    
    // time it takes for each step
    private final int cto;    
    // id of the corridorhall and corridor where the costumer will pass
    private int idCorridor = 0;
    // variable that says how many steps the customer has done 
    private int nTimesWalked = 0;
    
    // shared region Idle
    private final IIdle_Customer idle;
    // shared region OutsideHall
    private final IOutsideHall_Customer outsideHall;
    // shared region EntranceHall
    private final IEntranceHall_Customer entranceHall;
    // shared region CorridorHall
    private final ICorridorHall_Customer corridorHalls [];
    // shared region Corridors
    private final SACorridor corridors [];
    // shared region PaymentHall
    private final SAPaymentHall paymentHall;
    //Variable that controls the while of the Thread
    private final boolean exitFlag = true;
    
    
    
    public AECustomer( int customerId,int cto ,IIdle_Customer idle, IOutsideHall_Customer outsideHall , IEntranceHall_Customer entranceHall, ICorridorHall_Customer corridorHalls [], SACorridor corridors [], SAPaymentHall paymentHall) {
        this.customerId = customerId;
        this.idle = idle;
        this.outsideHall = outsideHall;
        this.entranceHall = entranceHall;
        this.corridorHalls = corridorHalls;
        this.cto = cto;
        this.corridors = corridors;
        this.paymentHall = paymentHall;
    }
    
    /**
     * Function that changes costumer to idle (AEControl uses this function)
     */
    public void changeCostumerStateIdle()
    {
        this.whereTheCostumerIs = -1;
    }
        
    @Override
    public void run() {
        while ( exitFlag ) {

            //If costumer enters here he will stay idle
            if(whereTheCostumerIs == -1)
            {
                idle.idle(customerId);
                whereTheCostumerIs ++;
            }
            //If costumer enters here he will enter outsideHall
            if(whereTheCostumerIs == 0)
            {
                whereTheCostumerIs++;
                outsideHall.in(customerId); 
            }
            //If costumer enters here he will enter entranceHall
            if(whereTheCostumerIs == 1)
            {
                whereTheCostumerIs++;
                entranceHall.in(customerId);
            }
            //If costumer enters here he will enter a corridorHall with the least costumers inside
            if(whereTheCostumerIs == 2)
            {
                whereTheCostumerIs++;
                int lowerCorridorHall = 3;
                int idLowerCorridorHall = 0;
                for(int i=0; i<3 ; i++)
                {
                    if(lowerCorridorHall > corridorHalls[i].getNumberOfCostumers())
                    {
                        lowerCorridorHall = corridorHalls[i].getNumberOfCostumers();
                        idLowerCorridorHall = i;
                        idCorridor = i;
                    }
                }
                corridorHalls[idLowerCorridorHall].in(customerId,corridors[idCorridor].getNumberOfCostumers(),corridors[idCorridor].firstSlotOpen());  
            }
            //If costumer enters here he will enter a corridor that is connected to the previous corridorHall and enter every time until it hits the 10 step
            if(whereTheCostumerIs == 3)
            {
                corridors[idCorridor].inCorridor(customerId,nTimesWalked,corridorHalls[idCorridor].getFifo());
                try {
                    sleep(cto);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AECustomer.class.getName()).log(Level.SEVERE, null, ex);
                }
                nTimesWalked++;
                if(nTimesWalked == 10)
                {                    
                    whereTheCostumerIs++;            
                }                      
            }
            //If costumer enters here he will enter the paymentHall
            if(whereTheCostumerIs == 4)
            {
                whereTheCostumerIs++;
                paymentHall.in(customerId);
            }
            //If costumer enters here he will its the paymentPoint and then goes idle
            if(whereTheCostumerIs == 5)
            {
                System.out.println("Costumer: "+ customerId + " Just Finished");
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AECustomer.class.getName()).log(Level.SEVERE, null, ex);
                }
                nTimesWalked = 0;
                whereTheCostumerIs=-1;
            }            
        }
    }
}
