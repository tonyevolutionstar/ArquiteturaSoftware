package ActiveEntity;

import Communication.ListeningChanges;
import SACorridor.SACorridor;
import SACorridorHall.ICorridorHall_Customer;
import SAEntranceHall.IEntranceHall_Customer;
import SAIdle.IIdle_Customer;
import SAOutsideHall.IOutsideHall_Customer;
import SAPaymentHall.SAPaymentHall;
import SAPaymentPoint.SAPaymentPoint;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Não pretende implementar a entidade activa Customer. Serve apenas para dar
 * pistas como o Thread Custumer deve recorrer a àreas partilhadas para gerir as
 * transições de estado.
 *
 * @author omp
 */
public class AECustomer extends Thread {

    // id do customer
    private final int customerId;

    private int whereTheCostumerIs = -1;
    private int cto;
    private int idCorridor = 0;
    private int nTimesWalked = 0;

    // área partilhada Idle
    private final IIdle_Customer idle;
    // área partilhada OutsideHall
    private final IOutsideHall_Customer outsideHall;
    // área partilhada EntranceHall
    private final IEntranceHall_Customer entranceHall;
    // área partilhada CorridorHall
    private final ICorridorHall_Customer corridorHalls[];
    //área partilhada Corridors
    private final SACorridor corridors[];

    private final SAPaymentHall paymentHalls[];

    private final SAPaymentPoint paymentPoints[];

    private boolean exitFlag = true;

    public enum State {
        IDLE,
        OUTSIDEHALL,
        ENTRANCEHALL,
        CORRIDORHALL,
        CORRIDOR,
        PAYMENTHALL,
        PAYMENTBOX
    }

    public State customer_state;

    public AECustomer(int customerId, int cto, IIdle_Customer idle, IOutsideHall_Customer outsideHall, IEntranceHall_Customer entranceHall, ICorridorHall_Customer corridorHalls[], SACorridor corridors[], SAPaymentHall paymentHalls[], SAPaymentPoint paymentPoints[]) {
        this.customerId = customerId;
        this.idle = idle;
        this.outsideHall = outsideHall;
        this.entranceHall = entranceHall;
        this.corridorHalls = corridorHalls;
        this.cto = cto;
        this.corridors = corridors;
        this.paymentHalls = paymentHalls;
        this.paymentPoints = paymentPoints;

        customer_state = State.IDLE;
        synchronized (ListeningChanges.class) {
            ListeningChanges.getInstance().setST_Customer(customerId, customer_state);
        }
    }

    @Override
    public void run() {
        while (exitFlag) {

            if (whereTheCostumerIs == -1) {
                System.out.println("VOU DORMIR" + customerId);

                idle.idle(customerId);
                customer_state = State.IDLE;
                synchronized (ListeningChanges.class) {
                    ListeningChanges.getInstance().setST_Customer(customerId, customer_state);
                    ListeningChanges.getInstance().send_information();
                    ListeningChanges.getInstance().send_message();

                }
                System.out.println("ACORDEI!" + customerId);
                whereTheCostumerIs++;
            }

            if (whereTheCostumerIs == 0) {

                System.out.println("COSTUMER " + customerId + " - OutSideHall->" + customerId + "----" + whereTheCostumerIs);
                whereTheCostumerIs++;
                outsideHall.in(customerId);
                customer_state = State.OUTSIDEHALL;
                synchronized (ListeningChanges.class) {
                    ListeningChanges.getInstance().setST_Customer(customerId, customer_state);
                    ListeningChanges.getInstance().send_information();
                    ListeningChanges.getInstance().send_message();

                }

            }
            if (whereTheCostumerIs == 1) {

                // System.out.println("COSTUMER "+customerId+" - EntranceHall->"+customerId+"----" +whereTheCostumerIs);
                whereTheCostumerIs++;
                entranceHall.in(customerId);
                customer_state = State.ENTRANCEHALL;
                synchronized (ListeningChanges.class) {
                    ListeningChanges.getInstance().setST_Customer(customerId, customer_state);
                    ListeningChanges.getInstance().send_information();
                    ListeningChanges.getInstance().send_message();

                }
            }
            if (whereTheCostumerIs == 2) {
                whereTheCostumerIs++;
                int lowerCorridorHall = 3;
                int idLowerCorridorHall = 0;

                for (int i = 0; i < 3; i++) {
                    if (lowerCorridorHall > corridorHalls[i].getNumberOfCostumers()) {
                        lowerCorridorHall = corridorHalls[i].getNumberOfCostumers();
                        idLowerCorridorHall = i;
                        idCorridor = i;
                    }
                }
                //System.out.println("COSTUMER "+customerId+" - CorridorHall->"+idLowerCorridorHall+"----"+whereTheCostumerIs+"----"+corridorHalls[idLowerCorridorHall].getNumberOfCostumers());
                corridorHalls[idLowerCorridorHall].in(customerId, corridors[idCorridor].getNumberOfCostumers(), corridors[idCorridor].firstSlotOpen());
                customer_state = State.CORRIDORHALL;
                synchronized (ListeningChanges.class) {
                    ListeningChanges.getInstance().setST_Customer(customerId, customer_state);
                    ListeningChanges.getInstance().send_information();
                    ListeningChanges.getInstance().send_message();

                }
            }
            if (whereTheCostumerIs == 3) {
                //   System.out.println("COSTUMER "+customerId+" - Corridor->"+idCorridor+"----"+whereTheCostumerIs+"----"+corridors[idCorridor].getNumberOfCostumers()+"-----"+corridorHalls[idCorridor].getNumberOfCostumers());

                corridors[idCorridor].inCorridor(customerId, nTimesWalked, corridorHalls[idCorridor].getFifo());
                customer_state = State.CORRIDOR;
                synchronized (ListeningChanges.class) {
                    ListeningChanges.getInstance().setST_Customer(customerId, customer_state);
                    ListeningChanges.getInstance().send_information();
                    ListeningChanges.getInstance().send_message();

                }
                try {
                    sleep(cto);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AECustomer.class.getName()).log(Level.SEVERE, null, ex);
                }
                nTimesWalked++;
                if (nTimesWalked == 10) {
                    whereTheCostumerIs++;
                }
            }
            if (whereTheCostumerIs == 4) {

                // System.out.println("COSTUMER "+customerId+" - PaymentHall->"+idCorridor+"----"+whereTheCostumerIs);
                whereTheCostumerIs++;

                paymentHalls[idCorridor].in(customerId);
                customer_state = State.PAYMENTHALL;
                synchronized (ListeningChanges.class) {
                    ListeningChanges.getInstance().setST_Customer(customerId, customer_state);
                    ListeningChanges.getInstance().send_information();
                    ListeningChanges.getInstance().send_message();

                }
            }
            if (whereTheCostumerIs == 5) {
                customer_state = State.PAYMENTBOX;
                synchronized (ListeningChanges.class) {
                    ListeningChanges.getInstance().setST_Customer(customerId, customer_state);
                    ListeningChanges.getInstance().send_information();
                    ListeningChanges.getInstance().send_message();

                }
                System.out.println("COSTUMER " + customerId + " - PaymentPoint->" + idCorridor + "----" + whereTheCostumerIs);
                nTimesWalked = 0;
                whereTheCostumerIs = -1;
            }
        }
    }
}
