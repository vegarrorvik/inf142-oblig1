/**
 * TCP klient, for å kommunisere med TCP serveren
 * Delar av koden henta frå forelesningsnotat
 * Created by vegar on 2/15/16.
 */
import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[]args) throws IOException {
        String userCommand;
        String responsFromServer;
        String [] responseFromServerInArray;

        BufferedReader inFromUser= new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket = new Socket("127.0.0.1", 6789);

        DataOutputStream outToServer= new DataOutputStream(clientSocket.getOutputStream());

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        System.out.println("Welcome! You can do following:\ninc *number* \t increases Value with *number*\n" +
                "dec *number* \t decreases Value with *number*\n" +
                "val \t returns Value\n" +
                "his \t returns log");

        userCommand = inFromUser.readLine();

        outToServer.writeBytes(userCommand + '\n');

        responsFromServer = inFromServer.readLine();

        responseFromServerInArray = responsFromServer.split("NEXT");

        System.out.println("FROM SERVER: ");
        for(String s : responseFromServerInArray)
            System.out.println(s);

        clientSocket.close();
    }
}
