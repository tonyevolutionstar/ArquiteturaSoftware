/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ComunicationOCC;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author tonya
 */
public class ClientOCC {

    String message;

    public ClientOCC(int port) throws IOException {
        System.out.println("Connecting to 127.0.0.1 " + " on port " + port);
        try (Socket client = new Socket("127.0.0.1", port)) {
            System.out.println("Just connected to ois " + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF("Hello from ois" + client.getLocalSocketAddress() + " port: " + +port);
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            while(client.isConnected()){
                String line = in.readUTF();  
               System.out.println("server ois says: " + line);
            }
        }
    }

    public String getMessage() { return this.message; }
}
