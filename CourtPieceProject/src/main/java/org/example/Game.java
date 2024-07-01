package org.example;

import java.util.ArrayList;

public class Game {
    String gameID = "1";
    ArrayList<String> players = new ArrayList<>();

    public boolean joinGame(String username, String creator) {
        if (players.size() < 4) {
            players.add(username);
            return true;
        } else {
            return false;
        }
    }
    public Game(Integer gameID, String creator) {
        this.gameID = gameID.toString();
        players.add(creator);
    }
}
