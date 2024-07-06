package org.example;

import java.util.ArrayList;
import java.util.HashMap;

public class CardsCollection {
    ArrayList<String> allCards = new ArrayList<>();
    HashMap<String,Integer> cardsValue = new HashMap<>();
    public void prepareAllCards() {
        String[] roles = {"GZ","DL","KT","PK"};
        String[] level = {"02","03","04","05","06","07","08","09","10","JK","QN","KG","AC"};
        for (int i=0 ; i<4 ; i++) {
            for (int j=0 ; j<13 ; j++) {
                allCards.add(level[j]+roles[i]);
            }
        }
        System.out.println(allCards);
    }
    public void prepareCardsValue() {
        Integer i = 2;
        for (String card : allCards) {
            if (i>14) {
                i=2;
            }
            cardsValue.put(card,i);
            i++;
        }
        System.out.println(cardsValue);
    }
}
