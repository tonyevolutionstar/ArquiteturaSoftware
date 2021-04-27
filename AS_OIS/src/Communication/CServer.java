/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Criar Server para receber comandos do OCC.
 *
 * @author omp
 */
public class CServer {

    public ServerSocket serverSocket;
    public Socket server;

    public CServer(int Port) {
        try {
            //time of the waiting
            serverSocket = new ServerSocket(Port);
            serverSocket.setSoTimeout(1000);
            System.out.println("Waiting for client occ on port " + serverSocket.getLocalPort() + "...");

            server = serverSocket.accept();
            System.out.println("Just connected to occ" + server.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(server.getInputStream());

            System.out.println(in.readUTF());
        } catch (IOException ex) {
            Logger.getLogger(CServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void sendMessage(String message) throws IOException {

        DataOutputStream out = new DataOutputStream(server.getOutputStream());
        out.writeUTF(message);
    }

}
