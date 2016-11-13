package socketprogramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Aaron
 */
public class Client {
    
    private int port;
    private String ip;
    
    /**
     * 
     * @param ip The IP address of the host you are attempting to reach
     * @param port The port at which you are attempting to connect
     */
    public Client(String ip, int port) {
        if ((port < 0) || (port > 0xFFFF)) {
            port = 8080;
        }
        this.port = port;
        this.ip = ip;
    }
    
    public void startClient() {
        
        //Let's instantiate a socket and attempt to connect to a server
        Socket socket = null;
        
        try {
            socket = new Socket(ip, port);
        }
        catch (UnknownHostException e) {
            System.err.println("Unable to connect to " + ip + ":" + port);
            e.printStackTrace();
            System.exit(-1);
        }
        catch (IOException e) {
            System.err.println("IOExcpetion");
            e.printStackTrace();
            System.exit(-1);
        }
        
        System.out.println("Connection established to " + ip + ":" + port);
        
        //Establish a way to write to and read from the server
        BufferedReader inFromServer = null;
        PrintWriter outToServer = null;
        try {
            inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outToServer = new PrintWriter(socket.getOutputStream(), true);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("IOException in BufferedReader or PrintWriter");
            System.exit(-1);
        }
        
        System.out.println("BufferedReader and PrintWriter established.");
        
        //So that we can get user input
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        
        //Now, our communication loop
        String message = null;
        do {
            System.out.print("Enter something to send to the Server : ");
            try {
                message = inFromUser.readLine();
                outToServer.println(message);
            }
            catch (IOException e) {
                System.err.println("Unable to read user input");
                e.printStackTrace();
                System.exit(-1);
            }

            System.out.print("Server message : ");
            System.out.println(readFromServer(inFromServer));
        } while (!message.equals("Exit"));
        
        //Of course, clean everything up properly
        try {
            socket.close();
        }
        catch (IOException e) {
            System.err.println("Problem closing socket");
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
    private String readFromServer(BufferedReader inFromServer) {
        
        String serverInput = null;
        
        try {
            serverInput = inFromServer.readLine();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading from client");
            System.exit(-1);
        }
        
        return serverInput;        
    }
}
