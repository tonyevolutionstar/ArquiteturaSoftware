/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Criar cliente para enviar comandos para o OCC.
 *
 * @author omp
 */
public class CClient {

    // RECEIVE INFORMATION FROM OCC, MEANS SERVER -> CLIENT, SO WILL RECEIVE DATA FROM OCC
    String message;

    public CClient(int port) throws IOException {
        System.out.println("Connecting to 127.0.0.1 " + " on port " + port);
        try (Socket client = new Socket("127.0.0.1", port)) {
            System.out.println("Just conne" + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF("Hello from " + client.getLocalSocketAddress() + " port: " + +port);
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            message = in.readUTF();
        }
    }

    public String getMessage() {
        return this.message;
    }

}
