package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class MainMenu extends JFrame implements ActionListener {
    String currentUserName;
    Socket socket;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    JPanel mainPanel;
    JButton exit;
    JButton newGame;
    JButton joinGame;
    JButton accounts;
    JLabel title;
    ////////////////////////
    JPanel accountsPanel;
    JButton signUp;
    JButton signIn;
    JButton exitAccount;
    JButton cancel;
    ////////////////////////
    JPanel signUpPanel;
    JLabel nameLabel1;
    JTextField nameTextField1;
    JLabel passwordLabel1;
    JTextField passwordTextField1;
    JLabel possibleError1;
    JButton insert1;
    JButton cancel1;
    ////////////////////////
    JPanel signInPanel;
    JLabel nameLabel2;
    JTextField nameTextField2;
    JLabel passwordLabel2;
    JTextField passwordTextField2;
    JLabel possibleError2;
    JButton insert2;
    JButton cancel2;
    public MainMenu(Socket socket, DataInputStream inputStream, DataOutputStream outputStream) {

        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;

        mainPanel = new JPanel();
        mainPanel.setSize(new Dimension(400,400));
        mainPanel.setLayout(null);
        mainPanel.setVisible(true);

        title = new JLabel("Court Piece");
        title.setBounds(50,0,200,50);

        newGame = new JButton("Create New Game");
        newGame.setBounds(10,100,100,50);
        newGame.addActionListener(this);
        newGame.setEnabled(false);

        joinGame = new JButton("Join Game");
        joinGame.setBounds(10,175,100,50);
        joinGame.addActionListener(this);
        joinGame.setEnabled(false);

        accounts = new JButton("Accounts");
        accounts.setBounds(10,250,100,50);
        accounts.addActionListener(this);

        exit = new JButton("Exit");
        exit.setBounds(10,325,100,50);
        exit.addActionListener(this);

        mainPanel.add(title);
        mainPanel.add(newGame);
        mainPanel.add(joinGame);
        mainPanel.add(accounts);
        mainPanel.add(exit);
        ////////////////////////////////////////////////////////////////////
        accountsPanel = new JPanel();
        accountsPanel.setSize(new Dimension(400,400));
        accountsPanel.setLayout(null);
        accountsPanel.setVisible(false);

        signUp = new JButton("Sign Up");
        signUp.setBounds(50,50,100,50);
        signUp.addActionListener(this);

        signIn = new JButton("Sign In");
        signIn.setBounds(50,120,100,50);
        signIn.addActionListener(this);

        exitAccount = new JButton("Exit Account");
        exitAccount.setBounds(50,190,100,50);
        exitAccount.addActionListener(this);
        exitAccount.setEnabled(false);

        cancel = new JButton("Return");
        cancel.setBounds(200,260,100,50);
        cancel.addActionListener(this);

        accountsPanel.add(signUp);
        accountsPanel.add(signIn);
        accountsPanel.add(exitAccount);
        accountsPanel.add(cancel);
        ////////////////////////////////////////////////////////////////////
        signUpPanel = new JPanel();
        signUpPanel.setSize(new Dimension(400,400));
        signUpPanel.setLayout(null);
        signUpPanel.setVisible(false);

        possibleError1 = new JLabel();
        possibleError1.setBounds(50,0,200,50);

        nameLabel1 = new JLabel("Username :");
        nameLabel1.setBounds(50,50,100,20);

        nameTextField1 = new JTextField();
        nameTextField1.setBounds(50,90,250,20);

        passwordLabel1 = new JLabel("Password :");
        passwordLabel1.setBounds(50,130,100,20);

        passwordTextField1 = new JTextField();
        passwordTextField1.setBounds(50,170,250,20);

        insert1 = new JButton("Insert");
        insert1.addActionListener(this);
        insert1.setBounds(50,210,100,20);

        cancel1 = new JButton("Return");
        cancel1.addActionListener(this);
        cancel1.setBounds(200,210,100,20);

        signUpPanel.add(nameLabel1);
        signUpPanel.add(nameTextField1);
        signUpPanel.add(passwordLabel1);
        signUpPanel.add(passwordTextField1);
        signUpPanel.add(insert1);
        signUpPanel.add(cancel1);
        signUpPanel.add(possibleError1);
        ////////////////////////////////////////////////////////////////////
        signInPanel = new JPanel();
        signInPanel.setSize(new Dimension(400,400));
        signInPanel.setLayout(null);
        signInPanel.setVisible(false);

        possibleError2 = new JLabel();
        possibleError2.setBounds(50,0,200,50);

        nameLabel2 = new JLabel("Username :");
        nameLabel2.setBounds(50,50,100,20);

        nameTextField2 = new JTextField();
        nameTextField2.setBounds(50,90,250,20);

        passwordLabel2 = new JLabel("Password :");
        passwordLabel2.setBounds(50,130,100,20);

        passwordTextField2 = new JTextField();
        passwordTextField2.setBounds(50,170,250,20);

        insert2 = new JButton("Insert");
        insert2.addActionListener(this);
        insert2.setBounds(50,210,100,20);

        cancel2 = new JButton("Return");
        cancel2.addActionListener(this);
        cancel2.setBounds(200,210,100,20);

        signInPanel.add(nameLabel2);
        signInPanel.add(nameTextField2);
        signInPanel.add(passwordLabel2);
        signInPanel.add(passwordTextField2);
        signInPanel.add(insert2);
        signInPanel.add(cancel2);
        signInPanel.add(possibleError2);
        ////////////////////////////////////////////////////////////////////
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(400,400);
        this.setVisible(true);
        this.setResizable(false);

        this.add(mainPanel);
        this.add(accountsPanel);
        this.add(signUpPanel);
        this.add(signInPanel);
    }
    public boolean sendInfo(String operation, String username, String password) {
        String message = "";
        boolean answear = true;
        try {
            outputStream.writeUTF(operation + " " + username + " " + password);
            answear = inputStream.readBoolean();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answear;
    }
    public boolean signUp() {
        String username = "";
        String password = "";
        boolean answear = true;
        try {
            username = nameTextField1.getText();
            password = passwordTextField1.getText();
            if (username.isEmpty() || username.contains(" ") || password.isEmpty() || password.contains(" ")) {
                possibleError1.setText("Error : unacceptable entry");
                answear = false;
            } else {
                if (sendInfo("signUp",username,password)) {
                    currentUserName = username;
                    nameTextField1.setText("");
                    passwordTextField1.setText("");
                    possibleError1.setText("");
                    signUpPanel.setVisible(false);
                    accountsPanel.setVisible(true);
                    signUp.setEnabled(false);
                    signIn.setEnabled(false);
                    exitAccount.setEnabled(true);
                    answear = true;
                } else {
                    possibleError1.setText("Error : username or password not acceptable");
                    answear = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answear;
    }
    public boolean signIn() {
        String username = "";
        String password = "";
        boolean answear = true;
        try {
            username = nameTextField2.getText();
            password = passwordTextField2.getText();
            if (username.isEmpty() || username.contains(" ") || password.isEmpty() || password.contains(" ")) {
                possibleError2.setText("Error : unacceptable entry");
                answear = false;
            } else {
                if (sendInfo("signIn",username,password)) {
                    currentUserName = username;
                    nameTextField2.setText("");
                    passwordTextField2.setText("");
                    possibleError2.setText("");
                    signInPanel.setVisible(false);
                    accountsPanel.setVisible(true);
                    signUpPanel.setVisible(false);
                    accountsPanel.setVisible(true);
                    signUp.setEnabled(false);
                    signIn.setEnabled(false);
                    exitAccount.setEnabled(true);
                    answear = true;
                } else {
                    possibleError2.setText("Error : incorrect username or password");
                    answear = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answear;
    }
    public boolean exit() {
        boolean answear = true;
        if (sendInfo("exitAccount","username","password")) {
            currentUserName = "";
            signUp.setEnabled(true);
            signIn.setEnabled(true);
            exitAccount.setEnabled(false);
            answear = true;
        } else {
            answear = false;
        }
        return answear;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGame) {
            try {
                outputStream.writeUTF("create " + currentUserName + " |");
                if (inputStream.readBoolean()) {
                    this.setVisible(false);
                    //GameFrame gameFrame = new GameFrame(currentUserName,socket,inputStream,outputStream);
                    //gameFrame.listen();
                    Thread gameFrameHandler = new GameFrameHandler(currentUserName,socket,inputStream,outputStream);
                    gameFrameHandler.start();
                }
            } catch (Exception t) {
                t.printStackTrace();
            }
        } else if (e.getSource() == joinGame) {
            this.setVisible(false);
            try {
                outputStream.writeUTF("join");
            } catch (Exception t) {
                t.printStackTrace();
            }
            GameLobby lobby = new GameLobby(currentUserName,socket,inputStream,outputStream,true);
        } else if (e.getSource() == accounts) {
            mainPanel.setVisible(false);
            accountsPanel.setVisible(true);
        } else if (e.getSource() == cancel) {
            accountsPanel.setVisible(false);
            mainPanel.setVisible(true);
        } else if (e.getSource() == signUp) {
            accountsPanel.setVisible(false);
            signUpPanel.setVisible(true);
        } else if (e.getSource() == signIn) {
            accountsPanel.setVisible(false);
            signInPanel.setVisible(true);
        } else if (e.getSource() == exitAccount) {
            if (exit()) {
                newGame.setEnabled(false);
                joinGame.setEnabled(false);
            }
        } else if (e.getSource() == insert1) {
            if (signUp()) {
                newGame.setEnabled(true);
                joinGame.setEnabled(true);
            }
        } else if (e.getSource()== cancel1) {
            nameTextField1.setText("");
            passwordTextField1.setText("");
            possibleError1.setText("");
            signUpPanel.setVisible(false);
            accountsPanel.setVisible(true);
        } else if (e.getSource() == insert2) {
            if (signIn()) {
                newGame.setEnabled(true);
                joinGame.setEnabled(true);
            }
        } else if (e.getSource() == cancel2) {
            nameTextField2.setText("");
            passwordTextField2.setText("");
            possibleError2.setText("");
            signInPanel.setVisible(false);
            accountsPanel.setVisible(true);
        }
    }
}