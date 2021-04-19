
package SAEntranceHall;
import FIFO.FIFO;

public class SAEntranceHall implements IEntranceHall_Customer,
                                       IEntranceHall_Manager,
                                       IEntranceHall_Control {
    
    final FIFO fifo;
    
    public SAEntranceHall( int maxCustomer) {
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
