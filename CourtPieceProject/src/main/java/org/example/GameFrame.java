package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameFrame extends JFrame implements ActionListener {
    public HashMap<String,Icon> cardIcons = new HashMap<>();
    String currentUserName;
    JLabel player1;
    JLabel card1;
    JLabel turn1;
    JLabel player2;
    JLabel card2;
    JLabel turn2;
    JLabel player3;
    JLabel card3;
    JLabel turn3;
    JLabel player4;
    JLabel card4;
    JLabel turn4;
    JLabel serverMessage;
    JLabel possibleError;
    JButton insert;
    JTextField textField;
    JLabel[] players;
    JLabel[] cards;
    JLabel[] turns;

    public GameFrame(String currentUserName) {
        this.currentUserName = currentUserName;

        ImageIcon defaultCard = new ImageIcon("Default.PNG");
        ImageIcon arrow = new ImageIcon("Arrow.PNG");
        cardIcons.put("DFT",defaultCard);

        player1 = new JLabel("player1");
        player1.setBounds(50,0,100,50);
        card1 = new JLabel();
        card1.setBounds(50,50,150,230);
        //card1.setIcon(defaultCard);
        turn1 = new JLabel();
        turn1.setBounds(50,280,70,70);
        turn1.setIcon(arrow);
        turn1.setVisible(false);

        player2 = new JLabel("player2");
        player2.setBounds(250,0,100,50);
        card2 = new JLabel();
        card2.setBounds(250,50,150,230);
        //card2.setIcon(defaultCard);
        turn2 = new JLabel();
        turn2.setBounds(250,280,70,70);
        turn2.setIcon(arrow);
        turn2.setVisible(false);

        player3 = new JLabel("player3");
        player3.setBounds(450,0,100,50);
        card3 = new JLabel();
        card3.setBounds(450,50,150,230);
        //card3.setIcon(defaultCard);
        turn3 = new JLabel();
        turn3.setBounds(450,280,70,70);
        turn3.setIcon(arrow);
        turn3.setVisible(false);

        player4 = new JLabel("player4");
        player4.setBounds(650,0,100,50);
        card4 = new JLabel();
        card4.setBounds(650,50,150,230);
        //card4.setIcon(defaultCard);
        turn4 = new JLabel();
        turn4.setBounds(650,280,70,70);
        turn4.setIcon(arrow);
        turn4.setVisible(false);


        players = new JLabel[4];
        players[0] = player1;
        players[1] = player2;
        players[2] = player3;
        players[3] = player4;

        cards = new JLabel[4];
        cards[0] = card1;
        cards[1] = card2;
        cards[2] = card3;
        cards[3] = card4;

        turns = new JLabel[4];
        turns[0] = turn1;
        turns[1] = turn2;
        turns[2] = turn3;
        turns[3] = turn4;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(850,800);
        this.setVisible(true);
        this.setResizable(false);

        this.add(player1);
        this.add(card1);
        this.add(player2);
        this.add(card2);
        this.add(player3);
        this.add(card3);
        this.add(player4);
        this.add(card4);
        this.add(turn1);
        this.add(turn2);
        this.add(turn3);
        this.add(turn4);

    }
    public void refresh(String recieved) {
        recieved = recieved.substring(recieved.indexOf(" ")+1);
        String labelNames = recieved.substring(0,recieved.indexOf(" "));
        recieved = recieved.substring(recieved.indexOf(" ")+1);
        String cardNames = recieved.substring(0,recieved.indexOf(" "));
        recieved = recieved.substring(recieved.indexOf(" ")+1);
        String turnName = recieved.substring(0,recieved.indexOf(" "));
        recieved = recieved.substring(recieved.indexOf(" ")+1);
        String enabled = recieved.substring(0,recieved.length());
        for (int i=0 ; i<4 ; i++) {
            if (labelNames.contains("#")) {
                System.out.println(labelNames.substring(0, labelNames.indexOf("#")));
                players[i].setText(labelNames.substring(0, labelNames.indexOf("#")));
                labelNames = labelNames.substring(labelNames.indexOf("#")+1);
                System.out.println(labelNames);
            }
        }
        for (int i=0 ; i<4 ; i++) {
            if (cardNames.contains("#")) {
                System.out.println(cardNames.substring(0, cardNames.indexOf("#")));
                cards[i].setIcon(cardIcons.get(cardNames.substring(0, cardNames.indexOf("#"))));
                cardNames = cardNames.substring(cardNames.indexOf("#")+1);
                System.out.println(cardNames);
            }
        }
        /*for (Integer i=0 ; i<4 ; i++) {
            if (i.toString().equals(turnName)) {
                turns[i].setVisible(true);
            } else {
                turns[i].setVisible(false);
            }
        }*/
        /*if (enabled.equals("true")) {
            insert.setEnabled(true);
            textField.setEnabled(true);
        } else {
            insert.setEnabled(false);
            textField.setEnabled(false);
        }*/
    }
    public String operationType(String recieved) {
        Pattern refresh = Pattern.compile("refresh");
        Matcher matcherRefresh = refresh.matcher(recieved);
        if (matcherRefresh.find()) {
            refresh(recieved);
        }
        return "true";
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

}