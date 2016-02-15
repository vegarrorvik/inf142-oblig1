/**
 * Created by vegar on 2/15/16.
 */
import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[]args) throws IOException {
        String clientSentence;
        String capitalizedSentence;

        ServerSocket welcomeSocket = new ServerSocket(6789);

        while(true){

            Socket connectionSocket = welcomeSocket.accept();
            System.out.println("Connection received from: " + connectionSocket.getInetAddress().getHostName());

            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            clientSentence = inFromClient.readLine();
            System.out.println("Data received: " + clientSentence);

            capitalizedSentence = clientSentence.toUpperCase() + '\n';

            outToClient.writeBytes(capitalizedSentence);
        }
    }
}
