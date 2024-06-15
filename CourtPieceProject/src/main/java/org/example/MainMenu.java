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
    JButton newGame;
    JButton accounts;
    JLabel title;
    JLabel playability;
    ////////////////////////
    JPanel accountsPanel;
    JButton signUp;
    JButton signIn;
    JButton deleteAccount;
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
    public MainMenu(Socket socket) {
        this.socket = socket;
        mainPanel = new JPanel();
        mainPanel.setSize(new Dimension(400,400));
        mainPanel.setLayout(null);
        mainPanel.setVisible(true);

        title = new JLabel("Court Piece");
        title.setBounds(10,50,100,50);

        playability = new JLabel("You must sign in before playing the game.");
        playability.setBounds(120,100,300,50);

        newGame = new JButton("New Game");
        newGame.setBounds(10,100,100,50);
        newGame.addActionListener(this);
        newGame.setEnabled(false);

        accounts = new JButton("Accounts");
        accounts.setBounds(10,175,100,50);
        accounts.addActionListener(this);

        mainPanel.add(title);
        mainPanel.add(playability);
        mainPanel.add(newGame);
        mainPanel.add(accounts);
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

        deleteAccount = new JButton("Delete Account");
        deleteAccount.setBounds(50,190,100,50);
        deleteAccount.addActionListener(this);
        deleteAccount.setEnabled(false);

        cancel = new JButton("Return");
        cancel.setBounds(200,260,100,50);
        cancel.addActionListener(this);

        accountsPanel.add(signUp);
        accountsPanel.add(signIn);
        accountsPanel.add(deleteAccount);
        accountsPanel.add(cancel);
        ////////////////////////////////////////////////////////////////////
        signUpPanel = new JPanel();
        signUpPanel.setSize(new Dimension(400,400));
        signUpPanel.setLayout(null);
        signUpPanel.setVisible(false);

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
        ////////////////////////////////////////////////////////////////////
        signInPanel = new JPanel();
        signInPanel.setSize(new Dimension(400,400));
        signInPanel.setLayout(null);
        signInPanel.setVisible(false);

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
    public boolean send_recieve(String operation, String userName, String password) {
        boolean response = true;
        try {
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());

            outputStream.writeUTF(operation + " " + userName + " " + password);
            outputStream.flush();

            response = inputStream.readBoolean();
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            System.out.println("Error : failed to connect.");
        }
        return response;
    }
    public void sendInfo(String operation) {
        String userName = "";
        String password = "";
        boolean response = true;
        try {
            userName = nameTextField1.getText();
            password = passwordTextField1.getText();
        } catch (Exception e) {
            System.out.println("error : could not get entries.");
        }
        if (send_recieve(operation, userName, password)) {
            currentUserName = userName;
            newGame.setEnabled(true);
            signIn.setEnabled(false);
            signUp.setEnabled(false);
            deleteAccount.setEnabled(true);
        }
    }
    public void getEntries() {

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGame) {

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
        } else if (e.getSource() == deleteAccount) {
            System.out.println("deleted");
        } else if (e.getSource() == insert1) {
            sendInfo("signUp");
        } else if (e.getSource()== cancel1) {
            nameTextField1.setText("");
            passwordTextField1.setText("");
            signUpPanel.setVisible(false);
            accountsPanel.setVisible(true);
        } else if (e.getSource() == insert2) {

        } else if (e.getSource() == cancel2) {
            nameTextField2.setText("");
            passwordTextField2.setText("");
            signInPanel.setVisible(false);
            accountsPanel.setVisible(true);
        }
    }
}