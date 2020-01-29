package client;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * This thread reads the user's input and sends it to the server. It will run in an infinite
 * loop until the user exits the chat room by typing "peace".
 */
public class WriteThread extends Thread {

    private Socket socket;
    private ChatClient client;
    private PrintWriter writer;
    private Set<String> userNames = new HashSet<>();

    public WriteThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;

        
    }

    /**
     * This block of code will execute when we start() a thread.
     */
    @Override
    public void run() {

        Console console = System.console();
        String userName = console.readLine("Enter your username: ");

        if (!userNames.contains(userName)) {
            client.setUserName(userName);
            writer.println(userName);

            String text;
            do {
                text = console.readLine("[" + userName + "]: ");
                writer.println(text);
            } while (!text.equals("peace"));

            try {
                socket.close();
            }
            catch (IOException e) {
                System.out.println("Error writing to the server: " + e.getMessage());
            }

        }
        else {
            System.out.println("That username is taken, please enter a new username.");
        }
    }
}
