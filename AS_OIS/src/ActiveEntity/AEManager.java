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
    private String managerState;
    private final IOutsideHall_Manager outsideHall;
    private final IEntranceHall_Manager entranceHall;
    private final ICorridorHall_Manager corridorHalls [];
    private boolean exitFlag = true;
    private boolean letCostumerInCorridorHall;
    private boolean letCostumerInEntranceHall;
    private int whereTheManagerIs; //1 for calling costumer to entrancehall and 2 for calling costumer to corridorhall
    
    public AEManager(String managerState,int sto ,IOutsideHall_Manager outsideHall , IEntranceHall_Manager entranceHall, ICorridorHall_Manager corridorHalls []) {
        this.managerState = managerState;
        this.sto = sto;
        this.outsideHall = outsideHall;
        this.entranceHall = entranceHall;
        this.corridorHalls = corridorHalls;
        this.letCostumerInCorridorHall = false;
    }
    
    public void changeManagerStatus(String status)
    {
        this.managerState = status;
    }
    public void letCostumerInCorridorHall()
    {
        this.letCostumerInCorridorHall=true;
    }
    public void letCostumerInEntranceHall()
    {
        this.letCostumerInEntranceHall=true;
    }

    @Override
    public void run() {
        while ( exitFlag ) {
                         
            
            if(entranceHall.getNumberOfCostumers()==0 && outsideHall.getNumberOfCostumers()==0 || "manual".equals(this.managerState))
            {
                try {
                    sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AEManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if("auto".equals(this.managerState))
            {
                this.letCostumerInCorridorHall=true;
                this.letCostumerInEntranceHall=true;
            }
            
            
               
            if(entranceHall.getNumberOfCostumers() != 6 && outsideHall.getNumberOfCostumers()!=0 && this.letCostumerInEntranceHall == true)
            {
                    whereTheManagerIs=1;
                    if("auto".equals(this.managerState))
                    {
                        try {
                            sleep(sto);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(AEManager.class.getName()).log(Level.SEVERE, null, ex);
                        }                    
                    }
  
             System.out.println("MANAGER ------ Thread manager vai chamar um costumer ao OutSideHall");
             outsideHall.call();   
             this.letCostumerInEntranceHall = false;
            }
            else
            {
             //System.out.println("MANAGER ---- Entrance Hall cheio  "+ entranceHall.getNumberOfCostumers()+ "  "+ outsideHall.getNumberOfCostumers()+"---_"+findBug);
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
            if(fullCorridors != 3 && entranceHall.getNumberOfCostumers()>0 && this.letCostumerInCorridorHall==true)
            {
                whereTheManagerIs=2;
                System.out.println("ENTROU CORRIDORS");
                    if("auto".equals(this.managerState))
                    {
                        try {
                            sleep(sto);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(AEManager.class.getName()).log(Level.SEVERE, null, ex);
                        }                    
                    }
                entranceHall.call();
                this.letCostumerInCorridorHall = false;
            }
            else
            {
             //   System.out.println("MANAGER ----CorridorHalls are full");
            }
            
           // System.out.println("ENTRANCEHALL--"+entranceHall.getNumberOfCostumers()+"OUTSIDEHALL"+outsideHall.getNumberOfCostumers()+"CORRIDOR1"+);
        }
    }
}
