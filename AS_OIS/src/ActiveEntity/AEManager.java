package ActiveEntity;
import Communication.ListeningChanges;
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
    private int findBug = 0;
    private boolean exitFlag = true;
    
    public enum State{
        IDLE,
        OUTSIDEHALL,
        ENTRANCEHALL
    }
    
    private State manager_state;
    
    public AEManager(int sto ,IOutsideHall_Manager outsideHall , IEntranceHall_Manager entranceHall, ICorridorHall_Manager corridorHalls []) {
        this.sto = sto;
        this.outsideHall = outsideHall;
        this.entranceHall = entranceHall;
        this.corridorHalls = corridorHalls;
        manager_state = State.IDLE;
        synchronized(ListeningChanges.class){
            ListeningChanges.getInstance().setST_Manager(manager_state);
        }
    }

    @Override
    public void run() {
        while ( exitFlag ) {
            try {
                sleep(sto);
            } catch (InterruptedException ex) {
                Logger.getLogger(AEManager.class.getName()).log(Level.SEVERE, null, ex);
            }            

            if(entranceHall.getNumberOfCostumers() != 6){
                manager_state = State.OUTSIDEHALL;
                System.out.println("MANAGER ------ Thread manager vai chamar um costumer ao OutSideHall");
                   manager_state = State.OUTSIDEHALL;
                synchronized(ListeningChanges.class){
                    ListeningChanges.getInstance().setST_Manager(manager_state);
                    ListeningChanges.getInstance().send_information();
                    ListeningChanges.getInstance().send_message();
                }
                outsideHall.call();   
                findBug++;
            }else{
             //System.out.println("MANAGER ---- Entrance Hall cheio  "+ entranceHall.getNumberOfCostumers()+ "  "+ outsideHall.getNumberOfCostumers()+"---_"+findBug);
            }
            
            int fullCorridors = 0;
            
            for(int i=0; i<3 ; i++)
            {
                manager_state = State.ENTRANCEHALL;
                synchronized(ListeningChanges.class){
                    ListeningChanges.getInstance().setST_Manager(manager_state);
                    ListeningChanges.getInstance().send_information();
                                        ListeningChanges.getInstance().send_message();

                }
                
                if(corridorHalls[i].getNumberOfCostumers()== 3)
                {
                    fullCorridors++;
                }
            }

           // System.out.println("MANAGER ------ NUMERO DE CORREDORES CHEIOS -- "+fullCorridors);
            if(fullCorridors != 3)
            {
                try {
                    sleep(sto);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AEManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                entranceHall.call();
            }
            else
            {
           //     System.out.println("MANAGER ----CorridorHalls are full");
            }
           // System.out.println("ENTRANCEHALL--"+entranceHall.getNumberOfCostumers()+"OUTSIDEHALL"+outsideHall.getNumberOfCostumers());

        }
    }
}
