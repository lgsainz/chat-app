package client;

import java.io.*;
import java.net.Socket;

/**
 * This thread reads the server's input and prints it. It will run in an infinite loop
 * until the client disconnects from the server.
 */
public class ReadThread extends Thread {

    private Socket socket;
    private ChatClient client;

    /**
     * Read input from server.
     * @param socket
     * @param client
     */
    public ReadThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;

        try {
            InputStreamReader in = new InputStreamReader(socket.getInputStream());
            BufferedReader reader = new BufferedReader(in);
        }
        catch (IOException e) {
            System.out.println("Error getting input stream: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * This block of code will execute when we start() a thread.
     */
    @Override
    public void run() {
    }
}
