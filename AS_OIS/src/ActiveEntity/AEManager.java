package ActiveEntity;

import SACorridor.SACorridor;
import SACorridorHall.ICorridorHall_Manager;
import SACorridorHall.SACorridorHall;
import SAEntranceHall.IEntranceHall_Manager;
import SAOutsideHall.IOutsideHall_Manager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AEManager extends Thread {

    //Variable that says the speed of transition of the manager 
    private final int sto;
    //Variable that says if the manager is manual or auto
    private String managerState;
    //Shared region for the outsideHall
    private final IOutsideHall_Manager outsideHall;
    //Shared region for the entranceHall
    private final IEntranceHall_Manager entranceHall;
    //Shared region for all the corridorHalls
    private final ICorridorHall_Manager corridorHalls[];
    //Variable that controls the while of the Thread
    private final boolean exitFlag = true;
    //variable for manual option that lets the costumer enter the corridorHall
    private boolean letCostumerInCorridorHall;
    //variable for manual option that lets the costumer enter the entranceHall
    private boolean letCostumerInEntranceHall;
    //1 for calling costumer to entrancehall and 2 for calling costumer to corridorhall
    private int whereTheManagerIs;

    public AEManager(String managerState, int sto, IOutsideHall_Manager outsideHall, IEntranceHall_Manager entranceHall, ICorridorHall_Manager corridorHalls[]) {
        this.managerState = managerState;
        this.sto = sto;
        this.outsideHall = outsideHall;
        this.entranceHall = entranceHall;
        this.corridorHalls = corridorHalls;
        this.letCostumerInCorridorHall = false;
    }

    /**
     * Function that changes the status of the manager
     *
     * @param status
     */
    public void changeManagerStatus(String status) {
        this.managerState = status;
    }

    /**
     * Function that lets 1 costumer enter the CorridorHall
     */
    public void letCostumerInCorridorHall() {
        this.letCostumerInCorridorHall = true;
    }

    /**
     * Function that lets 1 costumer enter the entranceHall
     */
    public void letCostumerInEntranceHall() {
        this.letCostumerInEntranceHall = true;
    }

    @Override
    public void run() {
        while (exitFlag) {

            //Pauses the manager in case there is nothing to do
            if (entranceHall.getNumberOfCostumers() == 0 && outsideHall.getNumberOfCostumers() == 0 || "manual".equals(this.managerState)) {
                try {
                    sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AEManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //If the mode is auto, means that its always open to let new costumers go
            if ("auto".equals(this.managerState)) {
                this.letCostumerInCorridorHall = true;
                this.letCostumerInEntranceHall = true;
            }
            //to call a costumer to the entrance hall
            if (entranceHall.getNumberOfCostumers() != 6 && outsideHall.getNumberOfCostumers() != 0 && this.letCostumerInEntranceHall == true) {
                whereTheManagerIs = 1;
                if ("auto".equals(this.managerState)) {
                    try {
                        sleep(sto);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AEManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                outsideHall.call();
                this.letCostumerInEntranceHall = false;
            }

            //check if there are full corridorHalls
            int fullCorridors = 0;
            for (int i = 0; i < 3; i++) {
                if (corridorHalls[i].getNumberOfCostumers() == 3) {
                    fullCorridors++;
                }
            }

            //Calls a costumer from the entranceHall
            if (fullCorridors != 3 && entranceHall.getNumberOfCostumers() > 0 && this.letCostumerInCorridorHall == true) {
                whereTheManagerIs = 2;
                System.out.println("ENTROU CORRIDORS");
                if ("auto".equals(this.managerState)) {
                    try {
                        sleep(sto);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AEManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                entranceHall.call();
                this.letCostumerInCorridorHall = false;
            }
        }
    }
}
