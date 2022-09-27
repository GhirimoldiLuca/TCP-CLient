import java.net.InetAddress;
import java.net.ServerSocket;

public class App {
    public static void main(String[] args) throws Exception {
        Client cl = new Client();
        cl.connect();
        cl.communicate();
    }
}
