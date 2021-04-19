/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SACorridor;

import FIFOCorridor.FIFOCorridor;

/**
 *
 * @author migue
 */
public interface ICorridor_Customer {
    public void inCorridor(int customerId,int nTimesWalked,FIFOCorridor corridorHallFifo);
    public void call();
    public int getNumberOfCostumers();
    public boolean firstSlotOpen();
}
