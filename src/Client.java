import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;


public class Client {

    private Socket socket;
    private int serverPort = 6666;
    private String serverName = "localhost";
    private BufferedReader dataIn;
    private DataOutputStream dataOut;
    private String dataInput;
    private String dataOutput;
    private static Scanner sc = new Scanner(System.in);

    protected Socket connect() throws IOException{
        this.socket = new Socket(serverName,serverPort);
        this.dataOut = new DataOutputStream(socket.getOutputStream());
        this.dataIn = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    
        return this.socket;
    }

    protected void communicate(){
        try{
            System.out.print("Enter the message to send: ");
            this.dataInput = sc.nextLine();

            System.out.println("Messagge sent to the server!");
            this.dataOut.writeBytes(this.dataInput);

            this.dataOutput = this.dataIn.readLine();
            System.out.println("Server response: "+"\n"+this.dataOutput);
            System.out.println("Connection closed!");
            this.socket.close();

        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Error during communication with server");
            System.exit(1);
        }
    }

    
}
