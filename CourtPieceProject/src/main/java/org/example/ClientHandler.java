package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientHandler extends Thread {
    OperationMethods methods = new OperationMethods();
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
        String currentUsername = "";
        try {
            String recieved = "";
            while (true) {
                recieved = inputStream.readUTF();
                outputStream.writeBoolean(methods.executeOperation(recieved,currentUsername));
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
