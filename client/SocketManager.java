package client;

import java.io.*;
import java.net.*;
import game.listener.MessageListener;

public class SocketManager {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private MessageListener listener;

    public boolean connectToServer(String host, int port) {
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void startReceiver() {
        new Thread(() -> {
            String msg;
            try {
                while ((msg = in.readLine()) != null) {
                    if (listener != null) {
                        listener.onMessageReceived(msg);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
            System.out.println("[SEND] " + message);
        }
    }

    public void setMessageListener(MessageListener listener) {
        this.listener = listener;
    }
}
