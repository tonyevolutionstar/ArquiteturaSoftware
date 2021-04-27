/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

import ActiveEntity.AECashier;
import ActiveEntity.AECustomer;
import ActiveEntity.AEManager;
import Main.OIS;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tonya
 */
public class ListeningChanges {

    private static ListeningChanges changes = null;
    public AECashier.State ST_Cashier;
    public AEManager.State ST_Manager;
    public AECustomer.State[] ST_Customer;
    public static StringBuilder builder;
    CServer server;

    public ListeningChanges() {

        ST_Customer = new AECustomer.State[OIS.MAX_CUSTOMERS];
        
    }

    public void send_information() {
        builder = new StringBuilder();
        builder.append(ST_Manager.toString()).append(";");
        System.out.println("Live Information: Manager " + ST_Manager);
        for (int i = 0; i < OIS.MAX_CUSTOMERS; i++) {
            System.out.println(" Customer: " + i + " state: " + ST_Customer[i]);
            String estado = ST_Customer[i].toString();
            if (!estado.isEmpty()) {
                builder.append(i).append(",").append(estado).append(" | ");
            }
        }

    }

    public void open_server(int port) throws IOException {
        server = new CServer(port);
    }

    public static ListeningChanges getInstance() {
        if (changes == null) {
            changes = new ListeningChanges();
        }
        return changes;
    }
    
    public void send_message(){
        try {
            server.sendMessage(builder.toString());
        } catch (IOException ex) {
            Logger.getLogger(ListeningChanges.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setST_Cashier(AECashier.State ST_Cashier) {
        this.ST_Cashier = ST_Cashier;
    }

    public void setST_Manager(AEManager.State ST_Manager) {
        this.ST_Manager = ST_Manager;
    }

    public void setST_Customer(int ID, AECustomer.State ST_Customer) {
        this.ST_Customer[ID] = ST_Customer;
    }
}
