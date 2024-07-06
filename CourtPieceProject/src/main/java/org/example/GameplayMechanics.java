package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameplayMechanics {
    CardsCollection collection = new CardsCollection();
    ArrayList<String> players = new ArrayList<>();
    ArrayList<Socket> sockets = new ArrayList<>();
    ArrayList<DataOutputStream> outputStreams = new ArrayList<>();
    ArrayList<DataInputStream> inputStreams = new ArrayList<>();
    ArrayList<ArrayList<String>> playersCardsGroup = new ArrayList<>();
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
            playersCardsGroup.get(players.indexOf(player)).add(random_single_card());
        }
        String playerCards = "";
        for (String card : playersCardsGroup.get(players.indexOf(player))) {
            playerCards = playerCards + card + "#";
        }
        try {
            outputStreams.get(players.indexOf(player)).writeUTF("playercards " + playerCards);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void send_error(String player, String error) {
        try {
            outputStreams.get(players.indexOf(player)).writeUTF("error : " + error);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String get_card(String player) {
        String card = "";
        try {
            card = inputStreams.get(players.indexOf(player)).readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return card;
    }
    //This method finds out what type of card this is (dl,kt,pk or gz ?) (two last indexes of the card , use substring)
    public String findType(String card) {
        return card.substring(2);
    }

    public boolean checkCard(String card, String tableCard, String ruleCard, ArrayList playersCards) {
        boolean acceptable = true;
        //first of all , does player have a card the same type as the table card?
        //if yes he can only insert that kind of type
        //if not he can insert either the ruling card or any other cards
        //if ruling card , then the value changes to max in the hashmap
        //if player chooses another card , the value of the card changes to 1 in the hashmap above
        //lastly if the card is acceptable return yes , otherwise return no
        return acceptable;
    }
    //This method allows the starting player to choose the "table card" and then let others put their cards in order
    public String turn(String ruleCard) {
        String tableCard = "";
        return  tableCard;
    }
    public boolean checkIfContainsCard(String player, String card) {
        return playersCardsGroup.get(players.indexOf(player)).contains(card);
    }
    public String setRuler() {
        Random random = new Random();
        int i = random.nextInt(4);
        try {
            for (String player : players) {
                outputStreams.get(players.indexOf(player)).writeUTF("ruler " + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return players.get(i);
    }
    public void setPlayerCard(String player) {
        String cards = "";
        for (String card : playersCardsGroup.get(players.indexOf(player))) {
            cards = cards + card + " , ";
        }
        try {
            System.out.println("Your cards : " + cards);
                outputStreams.get(players.indexOf(player)).writeUTF("Your cards : " + cards);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //First the ruler must choose the ruling card and then the game begins
    public String game() {
        String ruleCard = "";
        String ruler = setRuler();
        random_card_pack(ruler,5);
        setPlayerCard(ruler);
        do {
            send_error(ruler, "Please choose the rule card");
            ruleCard = get_card(ruler);
            System.out.println(checkIfContainsCard(ruler,ruleCard));
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
            setPlayerCard(player);
        }
        return  ruleCard;
    }
    public GameplayMechanics(ArrayList<String> players, ArrayList<Socket> sockets, ArrayList<DataInputStream> inputStreams, ArrayList<DataOutputStream> outputStreams) {
        this.players = players;
        this.sockets = sockets;
        this.inputStreams = inputStreams;
        this.outputStreams = outputStreams;
        collection.prepareAllCards();
        collection.prepareCardsValue();
        for (int i=0 ; i<4 ; i++) {
            ArrayList<String> playerCards = new ArrayList<>();
            playersCardsGroup.add(playerCards);
        }
        game();
    }
}