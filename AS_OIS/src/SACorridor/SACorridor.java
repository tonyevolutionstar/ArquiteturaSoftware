package SACorridor;
import ActiveEntity.AEManager;
import FIFOCorridor.FIFOCorridor;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SACorridor implements ICorridor_Customer,
                                   ICorridor_Control {
    
    final FIFOCorridor fifo;
    final FIFOCorridor corridorHallFifo;
    
    public SACorridor( int maxCustomer, FIFOCorridor corridorHallFifo) {
        this.fifo = new FIFOCorridor(maxCustomer);
        this.corridorHallFifo = corridorHallFifo;
    } 
    
    
    @Override
    public int getNumberOfCostumers()
    {
        return fifo.returnCount();
    }
    
    @Override
    public boolean firstSlotOpen()
    {
        return fifo.returnFirstSlot();
    }
    
    @Override
    public void call() {
        fifo.out();
        //System.out.println("VAI ACORDAR OUTRO THREAD AO HALL");
        //System.out.println("PORQUE È QUE NÃO ENTRAS?"+corridorHallFifo.returnCount()+"---"+fifo.returnFirstSlot()+"----"+fifo.returnCount());
        if(corridorHallFifo.returnCount()>=1 && fifo.returnFirstSlot()==true && fifo.returnCount()<2)
        {
         //   corridorHallFifo.outCostumer();                  
        }
    }
    
    public boolean checkFinal()
    {
        return fifo.check();
    }
   

    @Override
    public void inCorridor(int customerId,int nTimesWalked,FIFOCorridor corridorHallFifo) {
        fifo.inCostumer(customerId,nTimesWalked,corridorHallFifo);
    }
}
