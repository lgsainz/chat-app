package server;

import java.io.IOException;
import java.net.ServerSocket;

public class ChatServer {
    private int port;

    public ChatServer(int port) {
        this.port = port;
    }

    /**
     * Start new chat server and listen for users.
     */
    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Chat server is listening on port " + port);

        }
        catch (IOException ex) {
            System.out.println("Server Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please use the following syntax: java ChatServer <port-number>");
            System.exit(0);
        }

        int port = Integer.parseInt(args[0]);

        ChatServer server = new ChatServer(port);
        server.execute();
    }
}
