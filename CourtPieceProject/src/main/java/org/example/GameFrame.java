package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameFrame extends JFrame implements ActionListener {
    HashMap<String,ImageIcon> cardIcons = new HashMap<>();
    CardsCollection collection = new CardsCollection();
    String currentUserName;
    Socket socket = null;
    DataInputStream inputStream = null;
    DataOutputStream outputStream = null;
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
    JLabel ruler1;
    JLabel ruler2;
    JLabel ruler3;
    JLabel ruler4;
    JLabel serverMessage;
    JLabel myCards;
    JLabel possibleError;
    JLabel rulerCard;
    JButton insert;
    JLabel team1;
    JLabel team2;
    JTextField textField;
    JLabel[] players;
    JLabel[] cards;
    JLabel[] turns;

    public GameFrame(String currentUserName, Socket socket, DataInputStream inputStream, DataOutputStream outputStream) {
        this.currentUserName = currentUserName;
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        ImageIcon arrow = new ImageIcon("Arrow.PNG");
        ImageIcon rulerIcon = new ImageIcon("Ruler.PNG");

        collection.prepareAllCards();
        collection.setCardIcon();
        cardIcons = collection.cardIcon;

        player1 = new JLabel("player1");
        player1.setBounds(50,0,100,50);
        card1 = new JLabel();
        card1.setBounds(50,50,150,230);
        //card1.setIcon(defaultCard);
        turn1 = new JLabel();
        turn1.setBounds(50,280,70,70);
        turn1.setIcon(arrow);
        turn1.setVisible(false);
        ruler1 = new JLabel();
        ruler1.setBounds(120,280,70,70);
        ruler1.setIcon(rulerIcon);
        ruler1.setVisible(false);

        player2 = new JLabel("player2");
        player2.setBounds(250,0,100,50);
        card2 = new JLabel();
        card2.setBounds(250,50,150,230);
        //card2.setIcon(defaultCard);
        turn2 = new JLabel();
        turn2.setBounds(250,280,70,70);
        turn2.setIcon(arrow);
        turn2.setVisible(false);
        ruler2 = new JLabel();
        ruler2.setBounds(320,280,70,70);
        ruler2.setIcon(rulerIcon);
        ruler2.setVisible(false);

        player3 = new JLabel("player3");
        player3.setBounds(450,0,100,50);
        card3 = new JLabel();
        card3.setBounds(450,50,150,230);
        //card3.setIcon(defaultCard);
        turn3 = new JLabel();
        turn3.setBounds(450,280,70,70);
        turn3.setIcon(arrow);
        turn3.setVisible(false);
        ruler3 = new JLabel();
        ruler3.setBounds(520,280,70,70);
        ruler3.setIcon(rulerIcon);
        ruler3.setVisible(false);

        player4 = new JLabel("player4");
        player4.setBounds(650,0,100,50);
        card4 = new JLabel();
        card4.setBounds(650,50,150,230);
        //card4.setIcon(defaultCard);
        turn4 = new JLabel();
        turn4.setBounds(650,280,70,70);
        turn4.setIcon(arrow);
        turn4.setVisible(false);
        ruler4 = new JLabel();
        ruler4.setBounds(720,280,70,70);
        ruler4.setIcon(rulerIcon);
        ruler4.setVisible(false);

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

        serverMessage = new JLabel();
        serverMessage.setBounds(50,350,300,50);
        myCards = new JLabel();
        myCards.setBounds(50,400,500,50);
        possibleError = new JLabel();
        possibleError.setBounds(50,450,200,50);
        possibleError.setText("///////////////////////////////");
        textField = new JTextField();
        textField.setBounds(50,500,200,50);
        textField.setEnabled(false);
        insert = new JButton("Insert");
        insert.setBounds(250,500,100,50);
        insert.addActionListener(this);
        insert.setEnabled(false);
        rulerCard = new JLabel();
        rulerCard.setBounds(600,500,150,50);

        team1 = new JLabel();
        team1.setBounds(650,500,150,50);
        team2 = new JLabel();
        team2.setBounds(650,550,150,50);

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
        this.add(ruler1);
        this.add(ruler2);
        this.add(ruler3);
        this.add(ruler4);
        this.add(serverMessage);
        this.add(myCards);
        this.add(possibleError);
        this.add(textField);
        this.add(insert);
        this.add(rulerCard);
        this.add(team1);
        this.add(team2);
    }
    public void refresh(String recieved) {
        recieved = recieved.substring(recieved.indexOf(" ")+1);
        String labelNames = recieved.substring(0,recieved.indexOf(" "));
        recieved = recieved.substring(recieved.indexOf(" ")+1);
        String cardNames = recieved.substring(0,recieved.indexOf(" "));
        recieved = recieved.substring(recieved.indexOf(" ")+1);
        String turnName = recieved.substring(0,recieved.indexOf(" "));
        for (int i=0 ; i<4 ; i++) {
            if (labelNames.contains("#")) {
                players[i].setText(labelNames.substring(0, labelNames.indexOf("#")));
                labelNames = labelNames.substring(labelNames.indexOf("#")+1);
            }
        }
        for (int i=0 ; i<4 ; i++) {
            if (cardNames.contains("#")) {
                cards[i].setIcon(cardIcons.get(cardNames.substring(0, cardNames.indexOf("#"))));
                cardNames = cardNames.substring(cardNames.indexOf("#")+1);
            }
        }
        for (Integer i=0 ; i<4 ; i++) {
            if (i.toString().equals(turnName)) {
                turns[i].setVisible(true);
            } else {
                turns[i].setVisible(false);
            }
        }
    }
    public void showRuler(String recievded) {
        char a = recievded.charAt(6);
        switch (a) {
            case '0':
                ruler1.setVisible(true);
                break;
            case '1':
                ruler2.setVisible(true);
                break;
            case '2':
                ruler3.setVisible(true);
                break;
            case '3':
                ruler4.setVisible(true);
                break;
        }
    }
    public void showError(String recieved) {
        possibleError.setText(recieved);
        textField.setEnabled(true);
        insert.setEnabled(true);
    }
    public void setMyCards(String recieved) {
        myCards.setText(recieved);
    }
    public String  operationType(String recieved) {
        Pattern refresh = Pattern.compile("^refresh");
        Matcher matcherRefresh = refresh.matcher(recieved);
        Pattern ruler = Pattern.compile("ruler [0-3]");
        Matcher matcherRuler = ruler.matcher(recieved);
        Pattern error = Pattern.compile("^error");
        Matcher matcherError = error.matcher(recieved);
        Pattern yourCards = Pattern.compile("^Your cards");
        Matcher matcherYourCards = yourCards.matcher(recieved);
        Pattern ruleCard = Pattern.compile("^rule card");
        Matcher matcherRuleCard = ruleCard.matcher(recieved);
        Pattern team1Score = Pattern.compile("^team 1 score");
        Pattern team2Score = Pattern.compile("^team 2 score");
        Matcher matcher1 = team1Score.matcher(recieved);
        Matcher matcher2 = team2Score.matcher(recieved);
        if (matcherRefresh.find()) {
            System.out.println(currentUserName + ":" + recieved);
            return "refresh";
        } else if (matcherRuler.matches()) {
            System.out.println(currentUserName + ":" + recieved);
            return "ruler";
        } else if (matcherError.find()) {
            System.out.println(currentUserName + ":" + recieved);
            return "error";
        } else if (matcherYourCards.find()) {
            System.out.println(currentUserName + ":" + recieved);
            return "yourCards";
        } else if (matcherRuleCard.find()) {
            System.out.println(currentUserName + ":" + recieved);
            return "ruleCard";
        } else if (matcher1.find()) {
            return "team1";
        } else if (matcher2.find()) {
            return "team2";
        } else {
            return "noMatch";
        }
    }
    public void executeOperation(String recieved) {
        String operation = operationType(recieved);
        switch (operation) {
            case "refresh":
                refresh(recieved);
                break;
            case "ruler":
                showRuler(recieved);
                break;
            case "error":
                showError(recieved);
                break;
            case "yourCards":
                setMyCards(recieved);
                break;
            case "ruleCard":
                rulerCard.setText(recieved);
                break;
            case "team1":
                team1.setText(recieved);
                break;
            case "team2":
                team2.setText(recieved);
                break;
            default:
                break;
        }
    }
    public void getCommand() {
        try {
            String recieved = "";
                recieved = inputStream.readUTF();
                executeOperation(recieved);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insert) {
            Pattern pattern = Pattern.compile("([0-1][0-9][A-Z]{2}|[A-Z]{4})");
            Matcher matcher;
            String card = textField.getText();
            matcher = pattern.matcher(card);
            if (matcher.matches()) {
                try {
                    outputStream.writeUTF(card);
                } catch (Exception t) {
                    t.printStackTrace();
                }
                //textField.setEnabled(false);
                //insert.setEnabled(false);
                textField.setText("");
                } else {
                showError("error : unacceptable entry");
            }
        }
    }

}