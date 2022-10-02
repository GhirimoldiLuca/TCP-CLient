import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client implements Interface {
    private int attempts = 0;
    private Socket client = null;
    private InetAddress ip;
    private static Scanner sc = new Scanner(System.in);
    private DataOutputStream out;
    private BufferedReader in;
    
    protected void connect() {
        try {
            this.ip = InetAddress.getLocalHost();
            while (this.attempts < 3 || this.client == null) {
                this.client = new Socket(ip, PORT);
                this.attempts++;
            }
            if(this.attempts == 3 || this.client == null){
                throw new IOException("Can't estabilish connection with server!");
            }else {
                System.out.println("Connected to the server!");
            }   

            this.out = new DataOutputStream(this.client.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Unknown local host address");
            System.exit(2);
        }
        catch(IOException e){
            System.err.println(e.getMessage());
            System.exit(3);
        }
        System.out.print("Enter text to send to the server:");
            
        
    }
    protected void communicate(){
        try{
            this.out.writeBytes(sc.nextLine());
            String received = this.in.readLine();
            System.out.println(received);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
