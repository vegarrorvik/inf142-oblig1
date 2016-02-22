/**
 * TCP server for å håndtere heiltalsvariabel V.
 * Delar av koden er henta frå forelesningsnotat
 * Created by vegar on 2/15/16.
 */
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class T {
    public static void main(String[]args) throws IOException {
        ArrayList<UpdateInformation> history = new ArrayList<>();
        String clientCommand;
        String [] clientCommandToSplitToArray;
        int inputNumber = 0;
        int V = 0;

        ServerSocket welcomeSocket = new ServerSocket(6789);

        while(true){

            Socket connectionSocket = welcomeSocket.accept();
            System.out.println("Connection received from: " + connectionSocket.getInetAddress().getHostName());

            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            clientCommand = inFromClient.readLine();
            System.out.println("Data received: " + clientCommand);

            if(clientCommand.contains("inc") || clientCommand.contains("dec")){
                clientCommandToSplitToArray = clientCommand.split(" ");
                if(clientCommandToSplitToArray[0].equals("inc")) {
                    inputNumber = Integer.parseInt(clientCommandToSplitToArray[1]);
                    V+= inputNumber;
                    history.add(new UpdateInformation("Increase: " + inputNumber, new Date(),connectionSocket.getRemoteSocketAddress()));
                    outToClient.writeBytes("Increase of value with " + inputNumber + " succeded. Value is now " + V + '\n');
                }
                else if(clientCommandToSplitToArray[0].equals("dec")){
                    inputNumber = Integer.parseInt(clientCommandToSplitToArray[1]);
                    V-=inputNumber;
                    history.add(new UpdateInformation("Decrease: " + inputNumber, new Date(),connectionSocket.getRemoteSocketAddress()));
                    outToClient.writeBytes("Decrease of value with " + inputNumber + " succeded. Value is now " + V + '\n');
                }
                else if(clientCommandToSplitToArray.length<2)
                    outToClient.writeBytes("Invalid input. Remeber space between inc/dec and number \n");

            }
            else if(clientCommand.equals("val")) {
                outToClient.writeBytes("Value is " + V + '\n');
                history.add(new UpdateInformation("Get value", new Date(),connectionSocket.getRemoteSocketAddress()));
            }

            else if (clientCommand.equals("his")){
                String historyString = "";
                for(UpdateInformation u: history){
                    historyString += u + "NEXT";
                }
                history.add(new UpdateInformation("Get history", new Date(), connectionSocket.getRemoteSocketAddress()));
                outToClient.writeBytes(historyString + '\n');

            } else
                outToClient.writeBytes("Input not valid, try again. Remember space between inc/dec and *number*. \n");

        }
    }
}
