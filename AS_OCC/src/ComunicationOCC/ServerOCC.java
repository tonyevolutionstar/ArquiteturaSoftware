/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ComunicationOCC;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tonya
 */
public class ServerOCC {
    // variables store information
    private int Ncostumers;
    private int CTO;
    private String SOM; //supervisor operating move
    private int STO;
    
    
    public ServerOCC(int Port, int ncost, int tmc, String som, int sto) throws IOException{
        ncost = this.Ncostumers;
        tmc  = this.CTO;
        som = this.SOM;
        sto = this.STO;
        
        //struct message
        StringBuilder message = new StringBuilder();
        message.append(ncost).append(";").append(tmc).append(";").append(som).append(";").append(sto); // send all parameters to OIS
        
        ServerSocket serverSocket = new ServerSocket(Port);
        serverSocket.setSoTimeout(1000); //time of the waiting
        
        System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
        try (Socket server = serverSocket.accept()) {
            
            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(server.getInputStream());
                
            System.out.println(in.readUTF());
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            //out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
            out.writeUTF(message.toString());
        } catch (IOException ex) {
            Logger.getLogger(ServerOCC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
  
    
    
    // ---------- getters 
    
    public int getNcostumers(){ return this.Ncostumers; }
    
    public int getCTO(){ return this.CTO; } 
    
    public String getSOM() { return this.SOM; }
    
    public int getSTO() { return this.STO; }
    
}
