package SACorridorHall;
import FIFOCorridor.FIFOCorridor;

public class SACorridorHall implements ICorridorHall_Customer,
                                       ICorridorHall_Manager,
                                       ICorridorHall_Control {
    
    private final FIFOCorridor fifo;
    
    public SACorridorHall( int maxCustomer) {
        this.fifo = new FIFOCorridor(maxCustomer);
    } 
    
    
    @Override
    public int getNumberOfCostumers()
    {
        return getFifo().returnCount();
    }
    
    @Override
    public void in(int customerId, int nextCountCorridor, boolean firstSlotOpen) {
        getFifo().in(customerId, nextCountCorridor, firstSlotOpen);
    }

    @Override
    public FIFOCorridor getFifo() {
        return fifo;
    }
    
}
