package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerHandler implements Runnable {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private int messageCounter;

    public PlayerHandler(Socket socket) {
        this.socket = socket;
        messageCounter = 1;
    }

    @Override
    public void run() {
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            while (messageCounter <= 10) {
                String receivedMessage = input.readLine();
                if (receivedMessage == null) {
                    break; // Stop listening if null message received
                }
                System.out.println("Received: " + receivedMessage);
                String response = receivedMessage + " - " + messageCounter;
                output.println(response);
                messageCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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
    }
}