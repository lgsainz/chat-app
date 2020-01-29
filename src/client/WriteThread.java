package client;

import java.net.Socket;

/**
 * This thread reads the user's input and sends it to the server. It will run in an infinite
 * loop until the user exits the chat room by typing "peace".
 */
public class WriteThread extends Thread {

    private Socket socket;
    private ChatClient client;

    public WriteThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;
    }

    /**
     * This block of code will execute when we start() a thread.
     */
    @Override
    public void run() {
        
    }

}
