package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame implements ActionListener {
    JFrame accountOps;
    JFrame signUpOps;
    JFrame signInOps;
    JFrame deleteOps;
    public MainMenu() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(800,800);
        this.setVisible(true);
        this.setResizable(false);
    }
    public void accountsWindow() {
        accountOps = new JFrame("Account Operations");
        accountOps.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        accountOps.setLayout(null);
        accountOps.setSize(800,800);
        accountOps.setVisible(true);
        accountOps.setResizable(false);
    }
    public void signUpWindow() {
        signUpOps = new JFrame("Sign Up Operation");
        signUpOps.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        signUpOps.setLayout(null);
        signUpOps.setSize(800,800);
        signUpOps.setVisible(true);
        signUpOps.setResizable(false);
    }
    public void signInWindow() {
        signInOps = new JFrame("Sign In Operation");
        signInOps.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        signInOps.setLayout(null);
        signInOps.setSize(800,800);
        signInOps.setVisible(true);
        signInOps.setResizable(false);
    }
    public void deleteAccountWindow() {
        deleteOps = new JFrame("Delete Account Operation");
        deleteOps.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        deleteOps.setLayout(null);
        deleteOps.setSize(800,800);
        deleteOps.setVisible(true);
        deleteOps.setResizable(false);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
