package socketprogramming;

import java.util.Scanner;

/**
 *
 * @author Aaron Guillen
 */
public class SocketProgramming {
    
    private static Scanner input = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int t;
        int port = 8080;
        String host = "localhost";
        
        System.out.println("Welcome to my simple server program!");
        System.out.println("The client must send \"Exit\" to the server to");
        System.out.println("properly end the session.");
        System.out.println("\nHost and port can be specified via command line");
        
        //Allow command line input to specify host and port when starting a client
        if (args.length == 2) {
            host = args[0];
            port = Integer.getInteger(args[1]).intValue();
        }
        
        //Allow command line input to specify port when starting a server
        if (args.length == 1) {
            port = Integer.getInteger(args[1]).intValue();
        }
        
        do {
            System.out.print("Enter\n1:\tTo be Server\n2:\tTo be Client\n3:\tExit\n::->  ");
            t = input.nextInt();
            System.out.println();
            switch(t) {
                case 1:
                    new Server(port).startServer();
                    break;
                case 2:
                    new Client(host, port).startClient();
                    break;
                case 3:
                    System.exit(0);
                default:
            }
        } while ((t != 1) && (t != 2));
    }
    
}
