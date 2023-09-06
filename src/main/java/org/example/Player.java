package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player {
    private static final String HOST = "localhost";
    private static final int PORT = 8888;

    private String name;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private int messageCounter;

    public Player(String name) {
        this.name = name;
        messageCounter = 1;
    }

    public void connect() throws IOException {
        socket = new Socket(HOST, PORT);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);
    }

    public void sendAndReceiveMessages() throws IOException {
        while (messageCounter <= 10) {
            sendMessage();
            receiveMessage();
        }
    }

    private void sendMessage() {
        String message = "Message " + messageCounter;
        output.println(message);
        System.out.println(name + " sent: " + message);
        messageCounter++;
    }

    private void receiveMessage() throws IOException {
        String receivedMessage = input.readLine();
        System.out.println(name + " received: " + receivedMessage);
    }

    public void disconnect() {
        try {
            if (input != null)
                input.close();
            if (output != null)
                output.close();
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Player initiator = new Player("Initiator");
        Player receiver = new Player("Receiver");

        try {
            initiator.connect();
            receiver.connect();

            initiator.sendAndReceiveMessages();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            initiator.disconnect();
            receiver.disconnect();
        }
    }
}
