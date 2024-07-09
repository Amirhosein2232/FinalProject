package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client {

    public Client(String ip, int port) {
        Socket socket = null;
        DataInputStream inputStream = null;
        DataOutputStream outputStream = null;
        System.out.println("Connecting to server...");
        try {
            socket = new Socket(ip,port);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println("Error : failed to connect to server.");
        }
        System.out.println("Connected to server.");
        MainMenu menu = new MainMenu(socket,inputStream,outputStream);
    }
    public static void main(String[] args) {
        Client client = new Client("localhost", 9090);
        Client client1 = new Client("localhost", 9090);
        Client client2 = new Client("localhost", 9090);
        Client client3 = new Client("localhost", 9090);
    }
}