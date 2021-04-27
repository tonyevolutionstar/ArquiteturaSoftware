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
    public ServerOCC(int Port, String message) throws IOException{
        ServerSocket serverSocket = new ServerSocket(Port);
        serverSocket.setSoTimeout(100000); //time of the waiting
        
        System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
        try (Socket server = serverSocket.accept()) {
            
            System.out.println("Just connected to ois" + server.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(server.getInputStream());
                
            System.out.println(in.readUTF());
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF(message);
        } catch (IOException ex) {
            Logger.getLogger(ServerOCC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
  
    
}
