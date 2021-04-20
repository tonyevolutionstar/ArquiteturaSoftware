/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SACorridorHall;

import FIFOCorridor.FIFOCorridor;

/**
 *
 * @author migue
 */
public interface ICorridorHall_Customer {
    public void in(int customerId, int nextCountCorridor, boolean firstSlotOpen);
    public int getNumberOfCostumers();
    public void callTeste();
    public FIFOCorridor getFifo();
}
