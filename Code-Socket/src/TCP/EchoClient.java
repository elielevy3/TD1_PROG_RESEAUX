/***
 * EchoClient
 * Example of a TCP client 
 * Date: 10/01/04
 * Authors:
 */
package TCP;

import java.io.*;
import java.net.*;

public class EchoClient {

  /**
  *  main method
  *  accepts a connection, receives a message from client then sends an echo to the client
  **/
    public static void main(String[] args) throws IOException {

        Socket echoSocket = null;
        PrintStream socOut = null;
        BufferedReader stdIn = null;
        BufferedReader socIn = null;

        if (args.length != 2) {
          System.out.println("Usage: java EchoClient <EchoServer host> <EchoServer port>");
          System.exit(1);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Veuillez entrer votre prénom: ");
        String firstName = in.readLine();
        System.out.println("Veuillez entrer votre nom de famille");
        String lastName = in.readLine();

        try {
      	    // creation socket ==> connexion
            System.out.println("Client Launched");
      	    echoSocket = new Socket(args[0],new Integer(args[1]).intValue());
            socIn = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            socOut= new PrintStream(echoSocket.getOutputStream());
            stdIn = new BufferedReader(new InputStreamReader(System.in));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + args[0]);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to:"+ args[0]);
            System.exit(1);
        }

        // recevoir un message
        ListenBroadcastThread thread = new ListenBroadcastThread(echoSocket);
        thread.start();

        // emettre un message
        String line;
        while (true) {
        	line=stdIn.readLine();
        	if (line.equals(".")) break;
        	socOut.println(firstName+" "+lastName+": "+line);
        }
      socOut.close();
      socIn.close();
      stdIn.close();
      echoSocket.close();
      thread.stop();
    }
}


