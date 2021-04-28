package ActiveEntity;
import SACorridor.ICorridor_Cashier;
import SACorridor.SACorridor;
import SAPaymentHall.IPaymentHall_Cashier;
import SAPaymentHall.SAPaymentHall;
import SAPaymentPoint.IPaymentPoint_Cashier;
import SAPaymentPoint.SAPaymentPoint;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AECashier extends Thread { 

    private final IPaymentHall_Cashier paymentHall;
    private final IPaymentPoint_Cashier paymentPoint;
    private final SACorridor [] corridors;
    private int customerCto;
    private int totalFinished;
   

    public AECashier(SACorridor[] corridors, SAPaymentHall paymentHall, SAPaymentPoint paymentPoint, int cto) {
            this.corridors = corridors;
            this.paymentHall = paymentHall;
            this.paymentPoint = paymentPoint;
            this.customerCto = cto;
    }


    @Override
    public void run() {
        while ( true ) {
         
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
                if(corridors[i].checkFinal()==true && paymentHall.getNumberOfCostumers() <= 2)
                {
                    if(lowestTime == 0)
                    {
                       lowestTime= corridors[i].getTimer();
                       numberCorridorsReady++;
                       lowestTimeId=i;
                       continue;
                    }
                    if(lowestTime>corridors[i].getTimer())
                    {
                       numberCorridorsReady++;
                       lowestTime = corridors[i].getTimer();
                       lowestTimeId=i;
                    }                    
                    numberCorridorsReady++;
                }                
            }   
            if(numberCorridorsReady>0)
            {
                    System.out.println("CASHIER ---- Chamar Costumer para o PaymentHall--"+paymentHall.getNumberOfCostumers());                    
                    corridors[lowestTimeId].call();                
            }
          
          if(paymentHall.getNumberOfCostumers() == 2)
          {
              System.out.println("CASHIER ---- PAYMENTHALL cheio");
          }
          
          if(paymentHall.getNumberOfCostumers() > 0)
          {
              System.out.println("CASHIER ---- Chamar Costumer para o PaymentPoint--"+totalFinished);
              paymentHall.call();
              totalFinished++;
          } 
        }
    }
}
