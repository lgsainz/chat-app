package server;

import java.io.*;
import java.net.Socket;

/**
 * Handles connections from each connected user (client); allows for multiple
 * connections.
 * Exit Chat room by writing "peace".
 */
public class UserThread extends Thread {
    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;

    public UserThread(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    /**
     * This block of code will execute when we start() a thread.
     */
    @Override
    public void run() {

        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            printUsers();
            String userName;

            do {

                userName = reader.readLine();
//                server.addUserName(userName);

                if (server.addUserName(userName) != true) {
                    writer.println("Username is taken. Please use choose a new user.");
                    continue;
                }

                String serverMessage = "New user connected: " + userName;
                server.broadcast(serverMessage, this);

                String clientMessage;

                do {
                    clientMessage = reader.readLine();
                    serverMessage = "[" + userName + "]: " + clientMessage;
                    server.broadcast(serverMessage, this);

                } while (!clientMessage.equals("peace"));

                server.removeUser(userName, this);
                socket.close();

                serverMessage = userName + " has left the chat room.";
                server.broadcast(serverMessage, this);
                break;

            } while (true);


        }
        catch (IOException e) {
            System.out.println("Error in UserThread: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Sends a list of connected users to new user.
     */
    void printUsers() {
        if (server.hasUsers()) {
            writer.println("Connected users: " + server.getUserNames());
        } else {
            writer.println("No other users connected");
        }
    }

    /**
     * Sends a message to user.
     */
    void sendMessage(String message) {
        writer.println(message);
    }
}