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
        try {
            String recieved = "";
            while (!recieved.equalsIgnoreCase("exit")) {
                recieved = inputStream.readUTF();
                System.out.println(recieved);
                if (recieved.equalsIgnoreCase("signup amir 1234")) {
                    outputStream.writeBoolean(true);
                } else {
                    outputStream.writeBoolean(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            socket.close();
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
