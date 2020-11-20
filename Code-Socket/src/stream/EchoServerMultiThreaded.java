/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */

package stream;

import java.io.*;
import java.net.*;
import java.util.*;

public class EchoServerMultiThreaded  {

    public static String history;

    static{
        history = "";
    }

    /**
     * main method
     * @param args port
     *
     **/
    public static void main(String args[]){
        ServerSocket listenSocket;
        int clientId = 0;
        ArrayList<Socket> listSockets = new ArrayList<Socket>();

        if (args.length != 1) {
            System.out.println("Usage: java EchoServer <EchoServer port>");
            System.exit(1);
        }
        try {
            listenSocket = new ServerSocket(Integer.parseInt(args[0])); //port
            System.out.println("Server ready...");
            while (true) {
                Socket clientSocket = listenSocket.accept();
                System.out.println("Connexion from:" + clientSocket.getInetAddress());
                clientId++;
                listSockets.add(clientSocket);
                ClientThread ct = new ClientThread(clientSocket, clientId, listSockets, EchoServerMultiThreaded.history);
                ct.start();
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }
}


