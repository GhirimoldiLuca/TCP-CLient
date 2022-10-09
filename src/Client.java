import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Client process for exchange of Strings through the {@link Socket} with the Server until the number of vowels is half that of consonants
 * 
 * @author Ghirimoldi Luca
 * 
 * @version 1.1.3
 * @since 2022-09-24
 */
public class Client implements Interface {
    int attempts = 0;
    Socket client;
    InetAddress ip;
    BufferedReader keyboard;
    DataOutputStream out;
    BufferedReader in;
    String letters = "";
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
            keyboard = new BufferedReader(new InputStreamReader(System.in));
            ip = InetAddress.getLocalHost();
            while (attempts < 3 && client == null) {
                client = new Socket(ip, PORT);
                attempts++;
            }
            if (client == null) {
                throw new IOException("Can't estabilish connection with server!");
            } else {
                System.out.println("Connected to the server!");
            }

            out = new DataOutputStream(client.getOutputStream());
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Unknown local host address");
            System.exit(2);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(3);
        }

        return client;
    }

    /**
     * Send a String to the Server,
     * Receive a String containing the count of vowels and consonants of the
     * messagges sent
     */
    protected void communicate() {
        try {
            do {
                System.out.print("- Enter text to send to the server: ");
                String text = keyboard.readLine();
                out.writeBytes(text + "\n");
                out.flush();
                String received = in.readLine();
                System.out.println(received);
                letters=received.replaceAll("\\D", "");
            }while ((!((Integer.parseInt(Character.toString(letters.charAt(1)))) == (2*Integer.parseInt(Character.toString(letters.charAt(0))))))); // ugly condition
            client.close();
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