/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package stream;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientThread
        extends Thread {

    private HashMap<Integer, Socket> hashMapSockets;
    private Socket clientSocket;
    private Integer id;

    ClientThread(Socket s, Integer ID, HashMap<Integer, Socket> l) {
        this.clientSocket = s;
        this.hashMapSockets = l;
        this.id = ID;
    }

    /**
     * receives a request from client then sends an echo to the client
     **/
    public void run() {
        try {
            BufferedReader socIn = null;
            socIn = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
            while (true) {
                String line = socIn.readLine();
                // ici on broadcast
                Iterator it = hashMapSockets.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    if (pair.getKey() != this.id){
                        Socket s = (Socket) pair.getValue();
                        PrintStream out = new PrintStream(s.getOutputStream());
                        out.println(line);
                    }
                }
                socOut.println(line);
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }

}


