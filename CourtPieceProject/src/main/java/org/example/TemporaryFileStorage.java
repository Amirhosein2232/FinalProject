package org.example;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
//This class is about storing and recovering data
public class TemporaryFileStorage {
    File file = new File("playerInfo.txt");
    File lobbyList = new File("lobbyGameList.txt");
    public static HashMap<String,String> userName_password = new HashMap<>();
    public void writeFilePlayer(String userName, String password) {
        try {
            FileWriter writer = new FileWriter(file,true);
            writer.write( userName + "\n" + password + "\n" );
            writer.close();
        }catch (Exception e) {
            System.out.println("error : failed to write the file");
        }
    }
    public void writeFileLobbyList(ArrayList<Game> games) {
        try {
            if (!lobbyList.exists()) {
                lobbyList.createNewFile();
            }
            FileWriter writer = new FileWriter(lobbyList);
            for (int i=0 ; i< games.size() ; i++) {
                writer.write(games.get(i).gameID + " created by : " + games.get(i).players.get(0) + " " + games.get(i).players.size() + "/" + 4 + "\n");
            }
            writer.close();
        }catch (Exception e) {
            System.out.println("error : failed to write the file");
        }
    }
    public String readFileLobbyList() {
        String gameList = "";
        try {
            if (!lobbyList.exists()) {
                lobbyList.createNewFile();
            }
            FileReader reader = new FileReader(lobbyList);
            Scanner sc = new Scanner(lobbyList);
            while (sc.hasNextLine()) {
                String str = sc.nextLine();
                gameList = gameList + str + "|";
            }
            reader.close();
        }catch (Exception e) {
            System.out.println("error : failed to read the file");
        }
        return gameList;
    }
    public void readeFilePlayer() {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileReader reader = new FileReader(file);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String userName = sc.nextLine();
                String password = sc.nextLine();
                userName_password.put(userName,password);
            }
            reader.close();
        }catch (Exception e) {
            System.out.println("error : failed to read the file");
        }
    }
    public void fileEntryPlayer(String userName, String password) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        }catch (Exception e) {
            System.out.println("error : failed to create new file");
        }
        writeFilePlayer(userName, password);
        userName_password.put(userName,password);
    }
    public String passwordEncoder(String password) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        String encoded = "";
        for (byte i : encodedhash) {
            encoded = encoded + i;
        }
        return encoded;
    }

}