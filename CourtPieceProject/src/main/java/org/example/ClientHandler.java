package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    Socket socket;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    public ClientHandler(Socket socket, DataInputStream inputStream, DataOutputStream outputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }
    @Override
    public void run() {
        while (true) {
            String receivedMessage;
            try {
                receivedMessage = inputStream.readUTF();
                System.out.println(receivedMessage);
                if (receivedMessage.equals("signUp amir 1234")) {
                    outputStream.writeBoolean(true);
                    outputStream.flush();
                } else {
                    outputStream.writeBoolean(false);
                    outputStream.flush();
                }
            } catch (Exception e) {
                System.out.println("Error : failed to get message.");
            }
            try {
                inputStream.close();
                outputStream.close();
                socket.close();
            } catch (Exception e) {
                System.out.println("Error : failed to close connection.");
            }
        }
    }
}
