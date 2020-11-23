package UDP;
import java.io.*;
import java.net.*;
public class ClientUDP {

    private String firstName;
    private String lastName;

    public ClientUDP(String f, String l){
        this.firstName = f;
        this.lastName = l;
    }

    public String getFirstName(){ return firstName; }
    public String getLastName(){ return lastName; }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Veuillez entrer votre prénom: ");
        String firstName = in.readLine();
        System.out.println("Veuillez entrer votre nom de famille");
        String lastName = in.readLine();
        if(args.length != 2){
            System.out.println("Usage: java EchoClient <EchoServer host> <EchoServer port>");
            System.exit(1);
        }

        String ipGroup = args[0];
        int serverPort = Integer.parseInt(args[1]);

        System.out.println("port:"+serverPort+" ip:"+ipGroup);
        MulticastSocket socket = new MulticastSocket(serverPort);
        InetAddress serverAddress = InetAddress.getByName(ipGroup);
        socket.joinGroup(serverAddress);

        ListenClientInputThread send = new ListenClientInputThread(firstName, lastName, serverAddress, serverPort, socket);
        send.start();

        Thread receive = new ListenClientSocketInputThread(socket);
        receive.start();
    }
}