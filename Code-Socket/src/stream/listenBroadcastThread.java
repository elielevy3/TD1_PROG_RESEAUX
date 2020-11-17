package stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class listenBroadcastThread extends  Thread {

    private Socket clientSocket;

    public listenBroadcastThread(Socket s) {
        this.clientSocket = s;
    }

    public void run() {
        try {
            BufferedReader socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while(true){
                String line = socIn.readLine();
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}