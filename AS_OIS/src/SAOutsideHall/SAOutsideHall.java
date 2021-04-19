package SAOutsideHall;

import FIFO.FIFO;




public class SAOutsideHall implements IOutsideHall_Manager,
                                      IOutsideHall_Customer,
                                      IOutsideHall_Control {
    
    final FIFO fifo;
    

    public SAOutsideHall( int maxCustomer) {
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
