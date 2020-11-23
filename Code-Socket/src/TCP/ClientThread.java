/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package TCP;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientThread extends Thread {

    private ArrayList<Socket> listSockets;
    private Socket clientSocket;
    private Integer id;

    ClientThread(Socket s, Integer ID, ArrayList<Socket> l) {
        this.clientSocket = s;
        this.listSockets = l;
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
            String history = readFromFile("history.txt");
            socOut.println(history);
            while (true) {
                // on recupere ce qui est entré sur le terminal
                String line = socIn.readLine();
                // ici on le broadcast partout y compris pour ce client ci
                if (line != null){
                    for (Socket s : this.listSockets) {
                        PrintStream out = new PrintStream(s.getOutputStream());
                        out.println(line);
                    }
                    EchoServerMultiThreaded.history +=line + "\n";
                    writeIntoFile(EchoServerMultiThreaded.history, "history.txt");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeIntoFile(String text, String fileName){
        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.append(text);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public String readFromFile(String fileName){
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
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return content;
    }
}


