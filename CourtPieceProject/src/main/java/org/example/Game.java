package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Game {
    String gameID = "1";
    ArrayList<String> players = new ArrayList<>();
    ArrayList<Socket> sockets = new ArrayList<>();
    ArrayList<DataOutputStream> outputStreams = new ArrayList<>();
    ArrayList<DataInputStream> inputStreams = new ArrayList<>();
    public String refresh() {
        String labelNames = "";
        String cardNames = "";
        Integer i = players.size() - 1;
        String turnNames = i.toString();
        for (String player : players) {
            labelNames = labelNames + player + "#";
            cardNames = cardNames + "DFT#";
        }
        return "refresh " + labelNames + " " + cardNames + " " + turnNames + " |";
    }
    public boolean joinGame(String username, Socket socket, DataInputStream inputStream, DataOutputStream outputStream) {
        if (players.size() < 4) {
            players.add(username);
            sockets.add(socket);
            inputStreams.add(inputStream);
            outputStreams.add(outputStream);
            return true;
        } else {
            return false;
        }
    }
    public void startGame() {
        try {
            /*while (true) {
                recieved = inputStreams.get(players.indexOf(username)).readUTF();
                if (recieved.equals("refresh")) {
                    outputStreams.get(players.indexOf(username)).writeUTF(refresh(recieved));
                }
            }*/
            /*for (String player : players) {
                System.out.println("s1");
                outputStreams.get(players.indexOf(player)).writeUTF(refresh("false"));
                System.out.println("s2");
                System.out.println(inputStreams.get(players.indexOf(player)).readUTF());
                System.out.println("s3");
            }*/
            for (String player : players) {
                outputStreams.get(players.indexOf(player)).writeUTF(refresh());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (players.size() == 4) {
            GameplayMechanics gameplayMechanics = new GameplayMechanics(players,sockets,inputStreams,outputStreams);
        }
    }
    public Game(Integer gameID) {
        this.gameID = gameID.toString();
    }
}
