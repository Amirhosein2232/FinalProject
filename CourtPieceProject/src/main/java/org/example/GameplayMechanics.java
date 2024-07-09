package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;

public class GameplayMechanics {
    CardsCollection collection = new CardsCollection();
    ArrayList<String> players;
    ArrayList<Socket> sockets;
    ArrayList<DataOutputStream> outputStreams;
    ArrayList<DataInputStream> inputStreams;
    ArrayList<String> p1cards;
    ArrayList<String> p2cards;
    ArrayList<String> p3cards;
    ArrayList<String> p4cards;
    ArrayList<ArrayList<String>> playersCardsGroup = new ArrayList<>();
    ArrayList<String> playedCards = new ArrayList<>();
    ArrayList<Integer> scores = new ArrayList<>();
    int[] teamScores = new int[2];
    public void sendCardPack(int player) {
        String playerCards = "";
        for (String card : playersCardsGroup.get(player)) {
            playerCards = playerCards + card + ",";
        }
        try {
            outputStreams.get(player).writeUTF("Your cards : " + playerCards);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void resetPlayedCards() {
        for (int i=0 ; i<4 ; i++) {
            playedCards.set(i,"DFT");
        }
    }
    public void resetScores() {
        for (int i=0 ; i<4 ; i++) {
            scores.set(i,0);
        }
    }
    public String random_single_card() {
        String chosenCard = "";
        Random random = new Random();
        int index = random.nextInt(collection.allCards.size());
        chosenCard = collection.allCards.get(index);
        collection.allCards.remove(index);
        return chosenCard;
    }
    //This method adds a number of chosen cards to players collection
    public void random_card_pack(String player, int number) {
        for (int i=0 ; i<number ; i++) {
            (playersCardsGroup.get(players.indexOf(player))).add(random_single_card());
        }
        String playerCards = "";
        for (String card : playersCardsGroup.get(players.indexOf(player))) {
            playerCards = playerCards + card + ",";
        }
        try {
            outputStreams.get(players.indexOf(player)).writeUTF("Your cards : " + playerCards);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String send_error(String player, String error) {
        String card = "";
        try {
            outputStreams.get(players.indexOf(player)).writeUTF("error : " + error);
            card = inputStreams.get(players.indexOf(player)).readUTF();
            System.out.println(card);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return card;
    }
    /*public String get_card(String player) {

        while (card.isEmpty()) {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(card);
        return card;
    }
    public void recieve_card(int player) {
        String card = "";
        try {
            card = inputStreams.get(player).readUTF();
            get_card(card);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    //This method finds out what type of card this is (dl,kt,pk or gz ?) (two last indexes of the card , use substring)
    public String findType(String card) {
        return card.substring(2);
    }
    public boolean tableCardExists(int player, String tableCard) {
        boolean answear = false;
        for (String card : playersCardsGroup.get(player)) {
            if (findType(card).equals(tableCard)) {
                answear = true;
                break;
            }
        }
        System.out.println("exist : " + answear);
        return answear;
    }
    public boolean checkCard(int player, String card, String tableCard, String ruleCard) {
        if (tableCardExists(player,tableCard)) {
            if (findType(card).equals(tableCard)) {
                return true;
            } else if (findType(card).equals(ruleCard)) {
                int value = collection.cardsValue.get(card) + 13;
                collection.cardsValue.replace(card,value);
                return true;
            } else {
                return false;
            }
        } else {
            if (findType(card).equals(ruleCard)) {
                int value = collection.cardsValue.get(card) + 13;
                collection.cardsValue.replace(card,value);
                return true;
            } else {
                collection.cardsValue.replace(card,1);
                return true;
            }
        }
    }
    public void playerChooseCard(int player, String rulingCard ,String tableCard) {
        String card = "";
        do {
            card = send_error(players.get(player), "Please choose a card");
            System.out.println(!checkIfContainsCard(players.get(player), card) || !checkCard(player, card, tableCard, rulingCard));
        } while (!checkIfContainsCard(players.get(player), card) || !checkCard(player, card, tableCard, rulingCard));
        playersCardsGroup.get(player).remove(card);
        System.out.println("card removed");
        playedCards.set(player, card);
        System.out.println("played cards set");
        scores.set(player, collection.cardsValue.get(card));
        System.out.println("score set");
    }
    public String playerChooseTableCard(int player){
        String tableCard = "";
        do {
            tableCard = send_error(players.get(player), "Please choose a card");
            System.out.println("tabelcard : " + !checkIfContainsCard(players.get(player), tableCard));
        } while (!checkIfContainsCard(players.get(player), tableCard));
        playersCardsGroup.get(player).remove(tableCard);
        playedCards.set(player, tableCard);
        scores.set(player, collection.cardsValue.get(tableCard));
        return tableCard;
    }
    public void setTeamScores(int winner) {
        if (winner == 0 || winner == 2) {
            teamScores[0]++;
        } else {
            teamScores[1]++;
        }
        try {
            for (int i=0 ; i<4 ; i++) {
                outputStreams.get(i).writeUTF("team 1 score : " + teamScores[0]);
                outputStreams.get(i).writeUTF("team 2 score : " + teamScores[1]);
            }
        } catch (Exception e) {

        }
    }
    public void refresh(Integer turn) {
        String labelNames = "";
        String cardNames = "";
        int turnNames = turn;
        System.out.println(turnNames);
        for (String player : players) {
            labelNames = labelNames + player + "#";
            cardNames = cardNames + playedCards.get(players.indexOf(player)) + "#";
        }
        for (int i=0 ; i<4 ; i++) {
            try {
                outputStreams.get(i).writeUTF("refresh " + labelNames + " " + cardNames + " " + turnNames + " |");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //This method allows the starting player to choose the "table card" and then let others put their cards in order
    public int turn(String ruleCard, int starter) {
        resetPlayedCards();
        resetScores();
        refresh(starter);
        String tableCard = findType(playerChooseTableCard(starter));
        int player = starter + 1;
        for (int i=0 ; i<4 ; i++) {
            player = player % 4;
            refresh(player);
            playerChooseCard(player,ruleCard,tableCard);
            player = player + 1;
        }
        int maxValue = 0;
        int winner = 0;
        for (int score : scores) {
            if (score > maxValue) {
                maxValue = score;
                winner = scores.indexOf(score);
            }
        }
        setTeamScores(winner);
        for (int i=0 ; i<3 ; i++) {
            sendCardPack(i);
        }
        return winner;
    }
    public boolean checkIfContainsCard(String player, String card) {
        System.out.println(playersCardsGroup.get(players.indexOf(player)));
        return (playersCardsGroup.get(players.indexOf(player))).contains(card);
    }
    public String setRuler() {
        Random random = new Random();
        int i = random.nextInt(4);
        for (String player : players) {
            try {
                outputStreams.get(players.indexOf(player)).writeUTF("ruler " + i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return players.get(i);
    }
    //First the ruler must choose the ruling card and then the game begins
    public void game() {
        for (int i=0 ; i<4 ; i++) {
            playedCards.add(i,"DFT");
        }
        String ruleCard = "";
        String ruler = setRuler();
        System.out.println("ruler determined");
        refresh(players.indexOf(ruler));
        random_card_pack(ruler,5);
        do {
            ruleCard = send_error(ruler, "Please choose the rule card");
            System.out.println(!checkIfContainsCard(ruler,ruleCard));

        } while (!checkIfContainsCard(ruler,ruleCard));
        ruleCard = findType(ruleCard);
        for (String player : players) {
            try {
                outputStreams.get(players.indexOf(player)).writeUTF("rule card : " + ruleCard);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (String player : players) {
            if (player.equals(ruler)) {
                random_card_pack(player,8);
            } else {
                random_card_pack(player,13);
            }
        }
        for (int i=0 ; i<4 ; i++) {
            scores.add(0);
        }
        int starter = players.indexOf(ruler);
        /*for (int i=0 ; i<7 ; i++) {
            starter = turn(ruleCard,starter);
            System.out.println(starter);
        }*/
        starter = turn(ruleCard,starter);
        starter = turn(ruleCard,starter);
        starter = turn(ruleCard,starter);
        starter = turn(ruleCard,starter);
        starter = turn(ruleCard,starter);
        starter = turn(ruleCard,starter);
        starter = turn(ruleCard,starter);
    }
    public GameplayMechanics(ArrayList<String> players, ArrayList<Socket> sockets, ArrayList<DataInputStream> inputStreams, ArrayList<DataOutputStream> outputStreams) {
        this.players = (ArrayList<String>)players.clone();
        this.sockets = (ArrayList<Socket>) sockets.clone();
        this.inputStreams = (ArrayList<DataInputStream>)inputStreams.clone();
        this.outputStreams = (ArrayList<DataOutputStream>)outputStreams.clone();
        collection.prepareAllCards();
        collection.prepareCardsValue();
        p1cards = new ArrayList<>();
        p2cards = new ArrayList<>();
        p3cards = new ArrayList<>();
        p4cards = new ArrayList<>();
        playersCardsGroup.add(p1cards);
        playersCardsGroup.add(p2cards);
        playersCardsGroup.add(p3cards);
        playersCardsGroup.add(p4cards);
        game();
    }
}