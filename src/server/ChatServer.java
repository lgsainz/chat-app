package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * Handles the Server side of the Chat application.
 */
public class ChatServer {
    private int port;
    private Vector<String> userNames = new Vector<>();
    private Vector<UserThread> userThreads = new Vector<>();

    public ChatServer(int port) {
        this.port = port;
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

    /**
     * Starts new chat server and listen for users.
     */
    public void execute() {
        try (ServerSocket ss = new ServerSocket(port)) {

            System.out.println("Chat server is listening on port " + port);
            while (true) {
                Socket socket = ss.accept();
                System.out.println("New user connected");

                UserThread newUser = new UserThread(socket, this);
                userThreads.add(newUser);
                newUser.start();
            }
        }
        catch (IOException ex) {
            System.out.println("Server Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Delivers a message from one user to all other users.
     */
    public void broadcast(String message, UserThread excludeUser) {
        for (UserThread userT : userThreads) {
            if (userT != excludeUser) {
                userT.sendMessage(message);
            }
        }
    }

    /**
     * Adds new user to userNames.
     */
    public boolean addUserName(String userName) {
        if (!userNames.contains(userName)) {
            userNames.add(userName);
        }
        else {
            System.out.println("Username taken.");
            return false;
        }
        System.out.println("users: " + userNames);
        return true;
//        userNames.add(userName);
    }

    /**
     * Disconnects a user.
     */
    public void removeUser(String userName, UserThread aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            userThreads.remove(aUser);
            System.out.println("The user " + userName + " left the chat room.");
        }
    }

    /**
     * Return list of userNames.
     */
    public Vector<String> getUserNames() {
        return this.userNames;
    }

    /**
     * Returns true if there are other users connected (not count the currently connected user)
     */
    public boolean hasUsers() {
        return !this.userNames.isEmpty();
    }
}
