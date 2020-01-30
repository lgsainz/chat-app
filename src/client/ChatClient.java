package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This class handles the Client side of the Chat application.
 */
public class ChatClient {

    private String hostname;
    private int port;
    private String userName;
    private boolean isUnique = true;

    /**
     * ChatClient constructor.
     * @param hostname
     * @param port
     */
    public ChatClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    /**
     * Connect client to server.
     */
    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);
            System.out.println("Now connected to the chat server!");

            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();
        }
        catch (UnknownHostException e) {
            System.out.println("Error: server not found " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        }
    }

    /**
     * Set new username.
     */
    public void setUserName(String userName) {
        if (userName == null) {
            isUnique = false;
        }
        else if (this.getUnique()) {
            this.userName = userName;
        }
    }


    /**
     * Get current user.
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Set boolean flag to identify if incoming username is unique.
     */
    public void setUnique(boolean bool) {
        isUnique = bool;
    }

    /**
     * Get value of "unique flag" for incoming username.
     */
    public boolean getUnique() {
        return isUnique;
    }


    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please use the following command: 'java ChatClient <hostname> <port>'");
        }

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);

        ChatClient client = new ChatClient(hostname, port);
        client.execute();
    }
}
