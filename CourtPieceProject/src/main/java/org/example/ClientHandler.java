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
    Pattern create = Pattern.compile("^create");
    Pattern join = Pattern.compile("^join");
    public ClientHandler(Socket socket, DataInputStream inputStream, DataOutputStream outputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }
    @Override
    public void run() {
        try {

            String recieved = "";
            while (recieved != "exit") {
                recieved = inputStream.readUTF();
                Matcher matcherCreate = create.matcher(recieved);
                Matcher matcherJoin = join.matcher(recieved);
                if (matcherCreate.find() || matcherJoin.find()) {
                    GameManager manager = new GameManager(recieved, socket, inputStream, outputStream);
                }else {
                    outputStream.writeBoolean(methods.executeOperation(recieved));
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
