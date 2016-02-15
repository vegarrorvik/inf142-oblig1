/**
 * Created by vegar on 2/15/16.
 */
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class TCPServer {
    public static void main(String[]args) throws IOException {
        ArrayList<UpdateInformation> history = new ArrayList<>();
        String clientSentence;
        String [] clientCommand;
        int inputNumber = 0;
        int V = 0;

        ServerSocket welcomeSocket = new ServerSocket(6789);

        while(true){

            Socket connectionSocket = welcomeSocket.accept();
            System.out.println("Connection received from: " + connectionSocket.getInetAddress().getHostName());

            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            clientSentence = inFromClient.readLine();
            System.out.println("Data received: " + clientSentence);

            if(clientSentence.contains("inc") || clientSentence.contains("dec")){
                clientCommand = clientSentence.split(" ");
                if(clientCommand[0].equals("inc")) {
                    inputNumber = Integer.parseInt(clientCommand[1]);
                    V+= inputNumber;
                    history.add(new UpdateInformation("Increase: " + inputNumber, new Date(),connectionSocket.getRemoteSocketAddress()));
                    outToClient.writeBytes("Increase of value with " + inputNumber + " succeded. Value is now " + V + '\n');
                }
                else if(clientCommand[0].equals("dec")){
                    inputNumber = Integer.parseInt(clientCommand[1]);
                    V-=inputNumber;
                    history.add(new UpdateInformation("Decrease: " + inputNumber, new Date(),connectionSocket.getRemoteSocketAddress()));
                    outToClient.writeBytes("Decrease of value with " + inputNumber + " succeded. Value is now " + V + '\n');
                }

            } if(clientSentence.equals("val")) {
                outToClient.writeBytes("Value is " + V + '\n');
                history.add(new UpdateInformation("Get value", new Date(),connectionSocket.getRemoteSocketAddress()));
            }

            if (clientSentence.equals("his")){
                String historyString = "";
                for(UpdateInformation u: history){
                    historyString += u;
                }
                outToClient.writeBytes(historyString + '\n');

            }

        }
    }
}
