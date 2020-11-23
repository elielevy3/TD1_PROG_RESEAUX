package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;

public class ListenClientSocketInputThread extends Thread {

    private MulticastSocket socket;

    public ListenClientSocketInputThread(MulticastSocket s){
        this.socket = s;
    }

    @Override
    public void run(){
        byte[] buf;
        while(true){
            try {
                buf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                byte[] data = packet.getData();
                String s = new String(data, StandardCharsets.UTF_8);
                System.out.println(s);
                packet.setLength(buf.length);
            } catch (IOException e) {e.printStackTrace();}
        }
    }
}
