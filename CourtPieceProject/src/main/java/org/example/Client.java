package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client {
    public Client(String ip, int port) {
        Socket socket = null;
        try {
            socket = new Socket("localHost", 9090);
        } catch (Exception e) {
            System.out.println("Error : failed to connect to server.");
        }
        MainMenu menu = new MainMenu(socket);
    }
    public static void main(String[] args) {
        Client client = new Client("192.168.1.2",9090);
    }
}