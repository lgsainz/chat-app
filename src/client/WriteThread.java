package client;

import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This thread reads the user's input and sends it to the server. It will run in an infinite
 * loop until the user exits the chat room by typing "peace".
 */
public class WriteThread extends Thread {

    private Socket socket;
    private ChatClient client;
    private PrintWriter writer;

    public WriteThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException e) {
            System.out.println("Error getting output stream: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * This block of code will execute when we start() a thread.
     */
    @Override
    public void run() {
        // prompt client for username
        Console console = System.console();
        String userName;

        do {
            userName = console.readLine("Enter your username: ");
        } while (!client.getUnique());

        writer.println(userName);
        client.setUserName(userName);

        // read message from client and write to server until they end connection
        String text;
        do {
            text = console.readLine("[" + userName + "]: ");
            writer.println(text);
        } while (!text.equals("peace"));

        // close connection
        try {
            socket.close();
        }
        catch (IOException e) {
            System.out.println("Error writing to the server: " + e.getMessage());
        }

    }
}
