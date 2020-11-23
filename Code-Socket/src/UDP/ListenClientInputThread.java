package UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ListenClientInputThread extends  Thread {
    private InetAddress serverAddr;
    private int serverPort;
    private String clientFirstName ;
    private String clientLastName ;
    private MulticastSocket socket;

    public ListenClientInputThread(String firstName, String lastName, InetAddress address, int port, MulticastSocket s){
        this.serverAddr = address;
        this.serverPort = port;
        this.clientFirstName = firstName;
        this.clientLastName = lastName;
        this.socket = s;
    }

    @Override
    public void run(){
        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                line = in.readLine();
                String message = this.clientFirstName+" "+this.clientLastName+" : "+line;
                byte[] data = message.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, serverAddr, serverPort);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
