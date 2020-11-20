/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package stream;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.*;

public class ClientThread extends Thread {

    private ArrayList<Socket> listSockets;
    private Socket clientSocket;
    private Integer id;
    private String history;

    ClientThread(Socket s, Integer ID, ArrayList<Socket> l, String h) {
        this.clientSocket = s;
        this.listSockets = l;
        this.id = ID;
        this.history = h;
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
            socOut.println(this.history);
            while (true) {
                // on recupere ce qui est entré sur le terminal
                String line = socIn.readLine();
                // ici on le broadcast partout y compris pour ce client ci
                if (line != null){
                    for (Socket s : this.listSockets) {
                        PrintStream out = new PrintStream(s.getOutputStream());
                        out.println("Client n°" + id + ": " + line);
                    }
                    EchoServerMultiThreaded.history += "Client n°" + id + ": " + line + "\n";
                }
            }
        }
        catch(Exception e){

        }
    }
}


