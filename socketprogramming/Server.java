package socketprogramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Aaron
 */
public class Server {
    
    private int port;
    
    /**
     * 
     * @param port The port to listen on
     */
    public Server(int port) {
        if ((port < 0) || (port > 0xFFFF)) {
            port = 8080;
        }
        this.port = port;
    }
    
    
    public void startServer () {
        //Alright, so first we instantiate a ServerSocket
        ServerSocket serverSocket = null;
        
        try {
            serverSocket = new ServerSocket(port);
        }
        catch (IOException e) {
            System.err.println("Could not listen on port " + port);
            System.exit(-1);
        }
        
        //We allow connection
        Socket connectedSocket = null;
        
        try {
            System.out.println("Awaiting connection ... ");
            //Program waits at this line until a connection is made.
            connectedSocket = serverSocket.accept();
        }
        catch (IOException e) {
            System.err.println("Could not accept connection");
            System.exit(-1);
        }
        System.out.println("Connection accepted on port " + port);
        
        //Now, we establish a way to read from and write to the client
        BufferedReader inFromClient = null;
        PrintWriter outToClient = null;
        
        try {
            inFromClient = new BufferedReader(new InputStreamReader(connectedSocket.getInputStream()));
            outToClient = new PrintWriter(connectedSocket.getOutputStream(), true);
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
        String clientMessage = null;
        do {
            System.out.print("Client message : ");
            clientMessage = readFromClient(inFromClient);
            System.out.println(clientMessage);

            if (!clientMessage.equals("Exit")) {
                System.out.print("Enter something to send to the client : ");
                try {
                    outToClient.println(inFromUser.readLine());
                }
                catch (IOException e) {
                    System.err.println("Unable to read user input");
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
            else {
                outToClient.println("Goodbye");
            }
        } while (!clientMessage.equals("Exit"));
        
        //And of course, close everything properly
        try {
            serverSocket.close();
            connectedSocket.close();
        }
        catch (IOException e) {
            System.err.println("Error cleaning up");
            e.printStackTrace();
        }
    }
    
    private String readFromClient(BufferedReader inFromClient) {
        
        String clientInput = null;
        
        try {
            clientInput = inFromClient.readLine();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading from client");
            System.exit(-1);
        }
        
        return clientInput;        
    }    
}
