package SAOutsideHall;

import FIFO.FIFO;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;




public class SAOutsideHall implements IOutsideHall_Manager,
                                      IOutsideHall_Customer,
                                      IOutsideHall_Control {
    private final ReentrantLock rl = new ReentrantLock( true );
    private final boolean leave[];
    private final int array[];
    private int count;
    private final int maxCustomers;
        private final Condition cStay[];

    public SAOutsideHall( int maxCustomer) {
        this.array= new int[maxCustomer];
        for(int i =0 ; i<maxCustomer;i++)
        {
            array[i]=999;
        }
        maxCustomers = maxCustomer;
        
                cStay = new Condition[ maxCustomers ];
        leave = new boolean[ maxCustomers ];
        for ( int i = 0; i < maxCustomers; i++ ) {
            cStay[ i ] = rl.newCondition();
            leave[ i ] = false;
        }
    } 
    
    @Override
    public int getNumberOfCostumers()
    {
        return count;
    }
    
    @Override
    public void call() {
                try {
            rl.lock();

            int lowestId=999;
            int arrayId = 99;
            for(int i =0 ; i<this.maxCustomers;i++)
            {
                if(lowestId > array[i])
                {
                    lowestId = array[i];
                    arrayId = i;
                }
            }
                    System.out.println("LEAVING ----"+lowestId);
            leave[ arrayId ] = true;
            // acordar o customer
            cStay[ arrayId ].signal();
  
        } catch ( Exception ex ) {}
        finally {
            rl.unlock();
        }
    }

    @Override
    public void in(int customerId) {
        
                try {
            // garantir acesso em exclusividade
            rl.lock();
         
                       
        for(int i =0;i<this.maxCustomers;i++)
        {
            if(array[i]==999)
            {
                array[i]=customerId;
                count++;
                while ( !leave[ i ] )
                cStay[ i ].await();
                
                count--;
                array[i]=999;
                leave[ i ] = false;
                break;
            }
        }
        
                    
        } catch ( Exception ex ) {}
        finally {
            rl.unlock();
        }
    }
}
