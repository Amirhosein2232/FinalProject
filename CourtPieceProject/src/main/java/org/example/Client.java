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
            socket = new Socket("localHost", 9090);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println("Error : failed to connect to server.");
        }
        System.out.println("Connected to server.");
        MainMenu menu = new MainMenu(socket,inputStream,outputStream);
    }
    public static void main(String[] args) {
        Client client = new Client("192.168.1.2",9090);
    }
}