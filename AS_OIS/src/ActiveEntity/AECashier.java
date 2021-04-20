package ActiveEntity;
import SACorridor.ICorridor_Cashier;
import SACorridor.SACorridor;
import SAPaymentHall.IPaymentHall_Cashier;
import SAPaymentHall.SAPaymentHall;
import SAPaymentPoint.IPaymentPoint_Cashier;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AECashier extends Thread { 

    private final IPaymentHall_Cashier paymentHall;
    private final IPaymentPoint_Cashier paymentPoint;
    private final SACorridor corridor;
    private int customerCto;
    private int totalFinished;
   
    
    public AECashier(SACorridor  corridor,IPaymentHall_Cashier paymentHall, IPaymentPoint_Cashier paymentPoint, int customerCto) {
        this.corridor = corridor;
        this.paymentHall = paymentHall;
        this.paymentPoint = paymentPoint;
        this.customerCto = customerCto;
    }


    @Override
    public void run() {
        while ( true ) {
         
            try {
                sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(AECashier.class.getName()).log(Level.SEVERE, null, ex);
            }
            //System.out.println(corridor.getNumberOfCostumers()+"----"+paymentHall.getNumberOfCostumers());
            if(corridor.checkFinal())
            {
               // System.out.println("ENTROU");
            }
            if(corridor.checkFinal() && paymentHall.getNumberOfCostumers() <= 2)
            {
              //  System.out.println("CASHIER ---- Chamar Costumer para o PaymentHall--"+paymentHall.getNumberOfCostumers());
       
                corridor.call();
               // System.out.println(totalFinished);
                //System.out.println("CASHIER ---- Chamar Costumer para o PaymentHall--"+paymentHall.getNumberOfCostumers());
            }          
          
          if(paymentHall.getNumberOfCostumers() == 2)
          {
             // System.out.println("CASHIER ---- PAYMENTHALL cheio");
          }
          
          if(paymentHall.getNumberOfCostumers() > 0)
          {
            //  System.out.println("CASHIER ---- Chamar Costumer para o PaymentPoint");
              paymentHall.call();
              totalFinished++;
          }
          
          
          if(corridor.getNumberOfCostumers()==0 && paymentHall.getNumberOfCostumers() ==0)
          {
             // System.out.println(totalFinished);
          }

        }
    }
}
