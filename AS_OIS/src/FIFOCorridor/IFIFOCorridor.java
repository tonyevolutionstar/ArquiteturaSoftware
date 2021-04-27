package FIFOCorridor;


public interface IFIFOCorridor {
    public int in( int customerId, int nextCountCorridor, boolean firstSlotOpen);
    public void out();
    public int inCostumer( int customerId, int nTimesWalked, FIFOCorridor fifoCorridorHall);
    public boolean returnFirstSlot();
    public boolean check();
    public void outCostumer();
}
