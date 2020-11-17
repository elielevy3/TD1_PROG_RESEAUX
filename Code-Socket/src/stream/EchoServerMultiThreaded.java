/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */

package stream;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EchoServerMultiThreaded  {


    /**
     * main method
     * @param args port
     *
     **/
    public static void main(String args[]){
        ServerSocket listenSocket;
        int clientId = 0;
        HashMap<Integer, Socket> hashMapSockets = new HashMap<Integer,Socket>();


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
                hashMapSockets.put(clientId, clientSocket);
                ClientThread ct = new ClientThread(clientSocket, clientId, hashMapSockets);
                ct.start();
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }
}


