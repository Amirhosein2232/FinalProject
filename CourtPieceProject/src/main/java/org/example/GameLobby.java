package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class GameLobby extends JFrame implements ActionListener {
    Socket socket;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    public GameLobby(Socket socket, DataInputStream inputStream, DataOutputStream outputStream, Boolean canJoin) {

        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(400,400);
        this.setVisible(true);
        this.setResizable(false);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
