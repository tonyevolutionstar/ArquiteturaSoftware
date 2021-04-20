/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAIdle;

import FIFO.FIFO;

/**
 * @author omp
 */
public class SAIdle implements IIdle_Customer,                        
                               IIdle_Control {
    final FIFO fifo;

    public SAIdle(int maxCustomer) {
        this.fifo = new FIFO(maxCustomer);
    }
    public void idle() {
    }
    @Override
    public void idle( int customerId ) {
        fifo.in(customerId);
    }
    @Override
    public void start( int nCustmers ) {
        for(int i = 0; i<nCustmers;i++)
        {
            fifo.out();
        }
    }
    @Override
    public void end() {   
    }
   
}
