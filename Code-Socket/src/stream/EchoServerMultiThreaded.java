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
    public static String historyFileName;

    static{
        history = "";
        historyFileName = "history.txt";
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
            history = readFromFile(historyFileName);
            while (true) {
                Socket clientSocket = listenSocket.accept();
                System.out.println("Connexion from:" + clientSocket.getInetAddress());
                clientId++;
                listSockets.add(clientSocket);
                ClientThread ct = new ClientThread(clientSocket, clientId, listSockets);
                ct.start();
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }

    public static String readFromFile(String fileName){
        String content = "";
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                content += line+"\n";
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File "+fileName+" not found. We created an empty one.");
            File file = new File(fileName);
            try {
                file.createNewFile();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return content;
    }
}


