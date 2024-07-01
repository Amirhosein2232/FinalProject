package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameLobby extends JFrame implements ActionListener {
    String currentUserName;
    Socket socket;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    JLabel game1;
    JLabel game2;
    JLabel game3;
    JLabel game4;
    JLabel[] games;
    JLabel possibleError;
    JTextField textField;
    JButton select;
    JButton refresh;
    JPanel panel;
    public GameLobby(String currentUserName, Socket socket, DataInputStream inputStream, DataOutputStream outputStream, Boolean canJoin) {

        this.currentUserName = currentUserName;
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;

        panel = new JPanel();
        panel.setBounds(0,0,400,400);
        panel.setLayout(null);

        game1 = new JLabel("////////////////////////////////////");
        game1.setBounds(50,0,300,50);

        game2 = new JLabel("////////////////////////////////////");
        game2.setBounds(50,60,300,50);

        game3 = new JLabel("////////////////////////////////////");
        game3.setBounds(50,120,300,50);

        game4 = new JLabel("////////////////////////////////////");
        game4.setBounds(50,180,300,50);

        games = new JLabel[4];
        games[0] = game1;
        games[1] = game2;
        games[2] = game3;
        games[3] = game4;

        possibleError = new JLabel("////////////////////////////////////");
        possibleError.setBounds(50,240,200,50);

        textField = new JTextField();
        textField.setBounds(50,300,200,50);
        textField.setEnabled(canJoin);

        select = new JButton("Join");
        select.setBounds(250,300,100,50);
        select.setEnabled(canJoin);
        select.addActionListener(this);

        refresh = new JButton("refresh");
        refresh.setBounds(250,240,100,50);
        refresh.addActionListener(this);

        panel.add(game1);
        panel.add(game2);
        panel.add(game3);
        panel.add(game4);
        panel.add(possibleError);
        panel.add(textField);
        panel.add(select);
        panel.add(refresh);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(400,400);
        this.setVisible(true);
        this.setResizable(false);

        this.add(panel);
    }
    public void readLabel(String recieved) {
        for (int i=0 ; i<4 ; i++) {
            if (!recieved.isEmpty() && recieved.contains("|")) {
                games[i].setText(recieved.substring(0, recieved.indexOf("|")));
                recieved = recieved.substring(recieved.indexOf("|")+1);
            }
        }
    }
    public void refreshMessage() {
        String recieved = "";
        try {
            outputStream.writeUTF("refresh");
            recieved = inputStream.readUTF();
            readLabel(recieved);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean checkEntry(String gameID) {
        Pattern pattern = Pattern.compile("[0-9]{4}");
        Matcher matcher = pattern.matcher(gameID);
        return matcher.matches();
    }
    public void selectGame() {
        try {
            String gameID = textField.getText();
            if (checkEntry(gameID)) {
                outputStream.writeUTF("select " + currentUserName + " " + gameID);
                if (inputStream.readBoolean()) {
                    possibleError.setText("Joined game successfully : " + gameID);
                    select.setEnabled(false);
                    textField.setEnabled(false);
                } else {
                    possibleError.setText("Error : Unable to join game");
                }
            } else {
                possibleError.setText("Error : unacceptable entry");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == refresh) {
            refreshMessage();
        } else if (e.getSource() == select) {
            selectGame();
        }
    }
}
