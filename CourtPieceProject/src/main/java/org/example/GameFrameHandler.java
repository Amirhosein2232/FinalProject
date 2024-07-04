package org.example;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class GameFrameHandler extends Thread {
    String username;
    Socket socket;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    GameFrame gameFrame;
    public GameFrameHandler(String username, Socket socket, DataInputStream inputStream, DataOutputStream outputStream) {
        this.username = username;
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        gameFrame = new GameFrame(this.username);
    }
    @Override
    public void run() {
        try {
            String recieved;
            while (true) {
                recieved = inputStream.readUTF();
                System.out.println(recieved);
                gameFrame.operationType(recieved);
                //outputStream.writeUTF("hello");
                //System.out.println("s4");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
