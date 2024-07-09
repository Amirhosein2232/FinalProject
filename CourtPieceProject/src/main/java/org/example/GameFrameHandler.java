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
        gameFrame = new GameFrame(username,socket,inputStream,outputStream);
    }
    @Override
    public void run() {
        while (true) {
            gameFrame.getCommand();
        }
    }
}
