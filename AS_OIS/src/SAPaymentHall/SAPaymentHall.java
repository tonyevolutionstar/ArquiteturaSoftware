
package SAPaymentHall;
import FIFO.FIFO;

public class SAPaymentHall implements IPaymentHall_Customer,
                                       IPaymentHall_Cashier,
                                       IPaymentHall_Control {
    
    final FIFO fifo;
    
    public SAPaymentHall( int maxCustomer) {
        this.fifo = new FIFO(maxCustomer);
    } 
    
    
    @Override
    public int getNumberOfCostumers()
    {
        return fifo.returnCount();
    }
    
    @Override
    public void call() {
        fifo.out();
    }

    @Override
    public void in(int customerId) {
        fifo.in(customerId);
    }
}
