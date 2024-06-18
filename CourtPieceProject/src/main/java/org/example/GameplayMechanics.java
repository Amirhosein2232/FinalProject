package org.example;

import java.util.ArrayList;
import java.util.HashMap;

//This class is for gameplay methods we are going to use later
public class GameplayMechanics {
    //add all 52 cards to this arraylist ( (king khesht ) Kkt , (2 del ) 2dl , (5 pik ) 5pk , (ace geshniz) Agz , ....)
    ArrayList<String> allCards = new ArrayList<>();
    //add all 52 cards and their scores (for comparing cards) (you should create methods for changing value of cards accordingly)
    HashMap<String,Integer> allCards_Scores = new HashMap<>();
    //This method chooses one card form allCards randomly and removes that card from the arraylist
    public String random_single_card() {
        String chosenCard = "";
        //your code here
        return chosenCard;
    }
    //This method adds a number of chosen cards to players collection
    public void random_card_pack(ArrayList<String> playersCards, int number) {
        for (int i=0 ; i<number ; i++) {
            playersCards.add(random_single_card());
        }
    }
    //This method finds out what type of card this is (dl,kt,pk or gz ?) (two last indexes of the card , use substring)
    public String findType(String card) {
        String type = "";
        //your code here
        return type;
    }

    public boolean checkCard(String card, String tableCard, String ruleCard, ArrayList playersCards) {
        boolean acceptable = true;
        //first of all , does player have a card the same type as the table card?
        //if yes he can only insert that kind of type
        //if not he can insert either the ruling card or any other cards
        //if ruling card , then the value changes to max in the hashmap
        //if player chooses an other card , the value of the card changes to 1 in the hashmap above
        //lastly if the card is acceptable return yes , otherwise return no
        return acceptable;
    }
    //This method allows the starting player to choose the "table card" and then let others put their cards in order
    public String turn(String ruleCard) {
        String tableCard = "";
    }
    //First the ruler must choose the ruling card and then the game begins
    public String game() {
        String ruleCard = "";
        for(int i=0 ; i<7 ; i++) {
            turn(ruleCard);
        }
    }
}