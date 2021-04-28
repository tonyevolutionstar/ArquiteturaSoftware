package ActiveEntity;
import SACorridor.SACorridor;
import SAPaymentHall.IPaymentHall_Cashier;
import SAPaymentHall.SAPaymentHall;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AECashier extends Thread { 

    private final IPaymentHall_Cashier paymentHall;
    private final SACorridor [] corridors;
    private final int customerCto;

    public AECashier(SACorridor[] corridors, SAPaymentHall paymentHall, int cto) {
            this.corridors = corridors;
            this.paymentHall = paymentHall;
            this.customerCto = cto;
    }


    @Override
    public void run() {
        while ( true ) {
         
            //sleep so it synchronizes with the costumers steps
            try {
                sleep(this.customerCto);
            } catch (InterruptedException ex) {
                Logger.getLogger(AECashier.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            int numberCorridorsReady = 0;
            long lowestTime=0;
            int lowestTimeId=0;
            for(int i =0; i<3;i++)
            {
                //check if there is any costumer in the last step of each corridor
                if(corridors[i].checkFinal()==true && paymentHall.getNumberOfCostumers() <= 2)
                {
                    if(lowestTime == 0)
                    {
                       lowestTime= corridors[i].getTimer();
                       numberCorridorsReady++;
                       lowestTimeId=i;
                       continue;
                    }
                    //in case there are more then 1 costumer waiting to enter the paymentHall, cashier chooses the one who has been waiting longer
                    if(lowestTime>corridors[i].getTimer())
                    {
                       numberCorridorsReady++;
                       lowestTime = corridors[i].getTimer();
                       lowestTimeId=i;
                    }                    
                    numberCorridorsReady++;
                }                
            }   
            //Cashier pick one costumer
            if(numberCorridorsReady>0)
            {
                corridors[lowestTimeId].call();                
            }
            //pick one costumer from the paymentHall
            if(paymentHall.getNumberOfCostumers() > 0)
            {
                paymentHall.call();
            } 
        }
    }
}
