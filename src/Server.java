import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server process for exchange Strings through the {@link Socket} with the Client
 * 
 * @author Ghirimoldi Luca
 * 
 * @version 1.1.3
 * @since 2022-09-25
 */
public class Server implements Interface {
    ServerSocket server = null;
    private static int vowels = 0, consonants = 0;
    private int attempts = 0;
    Socket client = null;

    private BufferedReader input = null;
    private DataOutputStream output = null;

    /**
     * Launch the {@link ServerSocket} with
     * @param PORT 
     * Wait for a client {@link Socket} request
     * Saving the {@link InputStream} {@link OutputStream}
     * @throws IOException
     */
    protected Socket launch() {
        try {
            server = new ServerSocket(PORT);
            this.client = server.accept();
            server.close();

            this.input = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            this.output = new DataOutputStream(this.client.getOutputStream());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(3);
        }
        return this.client;
    }

    /**
     * Receive a String from the client
     * Invocation count() function, by passing the lowercase received String
     * Send a String to the Client and close the Client {@link Socket} 
     * 
     * Handling the {@link IOException}
     *  
     * @return  void    [return description]
     */
    protected void communicate() {
        try {

            String received = this.input.readLine();
            count(received.toLowerCase());
            this.output.writeBytes("Vowels: " + Server.vowels + "\t" + "Consonants: " + Server.consonants + "\n");
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Count the number of the vowels and 
     *
     * @return  void    [return description]
     */
    private void count(String s) {
        int start = s.length();
        int vowelsCount = (start - s.replaceAll("[aeiou]", "").length());
        vowels += vowelsCount;
        consonants += start - vowelsCount;
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.launch();
        server.communicate();
    }
}
