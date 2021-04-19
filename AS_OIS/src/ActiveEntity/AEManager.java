package ActiveEntity;
import SACorridor.SACorridor;
import SACorridorHall.ICorridorHall_Manager;
import SACorridorHall.SACorridorHall;
import SAEntranceHall.IEntranceHall_Manager;
import SAOutsideHall.IOutsideHall_Manager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AEManager extends Thread { 

    private final int sto;
    private final IOutsideHall_Manager outsideHall;
    private final IEntranceHall_Manager entranceHall;
    private final ICorridorHall_Manager corridorHalls [];
    private  int findBug = 0;
    private boolean exitFlag = true;
    
    public AEManager(int sto ,IOutsideHall_Manager outsideHall , IEntranceHall_Manager entranceHall, ICorridorHall_Manager corridorHalls []) {
        this.sto = sto;
        this.outsideHall = outsideHall;
        this.entranceHall = entranceHall;
        this.corridorHalls = corridorHalls;
    }

    @Override
    public void run() {
        while ( exitFlag ) {
            try {
                sleep(sto);
            } catch (InterruptedException ex) {
                Logger.getLogger(AEManager.class.getName()).log(Level.SEVERE, null, ex);
            }

            if(outsideHall.getNumberOfCostumers()!=0 && entranceHall.getNumberOfCostumers() != 6)
            {
             //System.out.println("MANAGER ------ Thread manager vai chamar um costumer ao OutSideHall");
             outsideHall.call();   
             findBug++;
            }
            else
            {
             System.out.println("MANAGER ---- Entrance Hall cheio  "+ entranceHall.getNumberOfCostumers()+ "  "+ outsideHall.getNumberOfCostumers()+"---_"+findBug);
            }
            
            int fullCorridors = 0;
            
            for(int i=0; i<3 ; i++)
            {
                if(corridorHalls[i].getNumberOfCostumers()== 3)
                {
                    fullCorridors++;
                }
            }
            

           // System.out.println("MANAGER ------ NUMERO DE CORREDORES CHEIOS -- "+fullCorridors);
            if(fullCorridors != 3)
            {
                try {
                    sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AEManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                entranceHall.call();
            }
            else
            {
                System.out.println("MANAGER ----CorridorHalls are full");
            }
            if(entranceHall.getNumberOfCostumers()== 0 && outsideHall.getNumberOfCostumers()==0)
            {
                System.out.println("MANAGER ----ACAVB");
                exitFlag= false;
            }
        }
    }
}
