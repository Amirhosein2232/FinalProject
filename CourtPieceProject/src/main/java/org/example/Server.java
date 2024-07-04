package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    TemporaryFileStorage storage = new TemporaryFileStorage();
    ServerSocket serverSocket = null;
    public Server(int port) {
        storage.readeFilePlayer();
        System.out.println("Creating server...");
        try {
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            System.out.println("Error : failed to create server.");
        }
        System.out.println("Server created.");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("A new client has established connection : " + socket);

                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                System.out.println("Assigning new thread for new client.");
                Thread t = new ClientHandler(socket,inputStream,outputStream);
                t.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /*try {
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
    public static void main(String[] args) {
        Server server = new Server(9090);
    }
}
