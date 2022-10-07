import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Client process for exchange of Strings through the {@link Socket} with the Server
 * 
 * @author Ghirimoldi Luca
 * 
 * @version 1.1.3
 * @since 2022-09-24
 */
public class Client implements Interface {
    private int attempts = 0;
    Socket client;
    private InetAddress ip;
    private BufferedReader keyboard;
    private DataOutputStream out;
    private BufferedReader in;

    /**
     * Create the {@link Socket} @param {@link InetAddress}, @param PORT} 
     * which try to estabilish the connection with the {@link ServerSocket} 3 times.
     * 
     * Handling the {@link UnUnknownHostException} , {@link IOException} 
     * 
     * @return {@link Socket} 
     * 
     */
    protected Socket connect() {
        try {
            this.keyboard = new BufferedReader(new InputStreamReader(System.in));
            this.ip = InetAddress.getLocalHost();
            while (this.attempts < 3 || this.client == null) {
                this.client = new Socket(ip, PORT);
                this.attempts++;
            }
            if (this.client == null) {
                throw new IOException("Can't estabilish connection with server!");
            } else {
                System.out.println("Connected to the server!");
            }

            this.out = new DataOutputStream(this.client.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Unknown local host address");
            System.exit(2);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(3);
        }

        return this.client;
    }

    /**
     * Send a String to the Server, 
     * Receive a String containing the count of vowels and consonants of the messages sent
     */
    protected void communicate() {
        try {
            System.out.print("Enter text to send to the server:");
            String text = keyboard.readLine();
            this.out.writeBytes(text + "\n");
            this.out.flush();
            String received = this.in.readLine();
            System.out.println(received);
            this.client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Start the client process
     */
    public static void main(String[] args) {
        Client client = new Client();
        client.connect();
        client.communicate();
    }

}
