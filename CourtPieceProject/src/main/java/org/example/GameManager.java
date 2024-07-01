package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameManager {
    Socket socket;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    TemporaryFileStorage storage = new TemporaryFileStorage();
    public static Integer gameID = 1000;
    public static ArrayList<Game> games = new ArrayList<>();
    public void gameGenerator(String creator) {
        Game game = new Game(gameID,creator);
        games.add(game);
        gameID++;
        try {
            outputStream.writeBoolean(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getUserName(String recieved) {
        int index1 = recieved.indexOf(" ");
        index1++;
        String userName = recieved.substring(index1, recieved.lastIndexOf(" "));
        return userName;
    }
    public String getGameID(String recieved) {
        int index1 = recieved.lastIndexOf(" ");
        index1++;
        String gameID = recieved.substring(index1,recieved.length());
        return gameID;
    }
    public void selectGame(String username, String gameId) {
        boolean answear = false;
        for (Game game : games) {
            if (game.gameID.equals(gameId) && game.players.size() < 4) {
                game.players.add(username);
                answear = true;
                break;
            }
        }
        try {
            outputStream.writeBoolean(answear);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void refreshList() {
        storage.writeFileLobbyList(games);
        try {
            outputStream.writeUTF(storage.readFileLobbyList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String operationTyp(String recieved) {
        Pattern create = Pattern.compile("create\\s(.){1,}\\s(.){1,}");
        Pattern select = Pattern.compile("select\\s(.){1,}\\s[0-9]{4}");
        Pattern refresh = Pattern.compile("refresh");
        Matcher matcherCreate = create.matcher(recieved);
        Matcher matcherSelect = select.matcher(recieved);
        Matcher matcherRefresh = refresh.matcher(recieved);
        if (matcherCreate.matches()) {
            return "create";
        } else if (matcherSelect.matches()) {
            return "select";
        } else if (matcherRefresh.matches()) {
            return "refresh";
        } else {
            return "noMatch";
        }
    }
    public void executeOperation(String recieved) {
        String operation = operationTyp(recieved);
        switch (operation) {
            case "create" :
                gameGenerator(getUserName(recieved));
                break;
            case "select" :
                selectGame(getUserName(recieved),getGameID(recieved));
                break;
            case "refresh" :
                refreshList();
                break;
            default:
                break;
        }
    }
    public GameManager(String message, Socket socket, DataInputStream inputStream, DataOutputStream outputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        try {
            executeOperation(message);
            String recieved = "";
            while (true) {
                recieved = inputStream.readUTF();
                executeOperation(recieved);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
