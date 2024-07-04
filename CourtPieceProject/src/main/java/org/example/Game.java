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
    public String refresh(String enabled) {
        String labelNames = "";
        String cardNames = "";
        Integer i = players.size();
        String turnNames = i.toString();
        for (String player : players) {
            labelNames = labelNames + player + "#";
            cardNames = cardNames + "DFT#";
        }
        System.out.println("refresh " + labelNames + " " + cardNames + " " + turnNames + " " + enabled);
        return "refresh " + labelNames + " " + cardNames + " " + turnNames + " " + enabled;
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
            String recieved = "";
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
            for (int i=0 ; i<players.size() ; i++) {
                outputStreams.get(i).writeUTF(refresh("false"));
                //System.out.println(inputStreams.get(i).readUTF());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Game(Integer gameID) {
        this.gameID = gameID.toString();
    }
}
