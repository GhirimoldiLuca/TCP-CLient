import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.SourceDataLine;

public class Server  implements Interface {

    private static int vowels = 0,consonants = 0;
    private int attempts = 0;
    private Socket client = null;
    private InetAddress ip;

    private BufferedReader input;
    private DataOutputStream output;

    protected void launch() {
        try {
            ServerSocket server = new ServerSocket(PORT);
            this.client = server.accept(); 
            server.close();


            this.input = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            this.output = new DataOutputStream(this.client.getOutputStream());
        }
        catch(IOException e){
            System.err.println(e.getMessage());
            System.exit(3);
        }
    }
    protected void communicate(){
        try {

            String received = this.input.readLine();
            count(received.toLowerCase());
            this.output.writeBytes("Vowels: "+Server.vowels+"\t"+"Consonants: "+Server.consonants);
            System.out.println(this.input.readLine());
            client.close();
           
        } catch (IOException e) {
            System.out.println(e);
        }
        
        
    }
    private void count(String s){ 
        int start = s.length();
        int vowelsCount = (start - s.replaceAll("[aeiou]", "").length());
        vowels+=vowelsCount;
        consonants+= start-vowelsCount;
    }
}
