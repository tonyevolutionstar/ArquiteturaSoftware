
package SAPaymentPoint;

import FIFO.FIFO;

public class SAPaymentPoint implements IPaymentPoint_Customer,
                                       IPaymentPoint_Cashier,
                                       IPaymentPoint_Control {
    
    final FIFO fifo;
    
    public SAPaymentPoint( int maxCustomer) {
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
