package org.example;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private static final int PORT = 8888;
    private ServerSocket serverSocket;

    public Server() {
    }

    public void startListening() {
        System.out.println("Server listening on port " + PORT + "...");
        try {
            serverSocket = new ServerSocket(PORT);

            PlayerHandler Player1Handler = new PlayerHandler(serverSocket.accept());
            PlayerHandler Player2Handler = new PlayerHandler(serverSocket.accept());

            Thread Player1Thread = new Thread(Player1Handler);
            Thread Player2Thread = new Thread(Player2Handler);

            Player1Thread.start();
            Player2Thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null)
                    serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.startListening();
    }
}