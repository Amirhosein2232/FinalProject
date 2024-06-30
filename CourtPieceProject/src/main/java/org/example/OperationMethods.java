package org.example;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperationMethods {
    TemporaryFileStorage storage = new TemporaryFileStorage();
    public String getUserName(String recieved) {
        int index1 = recieved.indexOf(" ");
        index1++;

        String userName = recieved.substring(index1,recieved.lastIndexOf(" "));
        return userName;
    }
    public String getPassword(String recieved) {
        int index1 = recieved.lastIndexOf(" ");
        index1++;
        String password = recieved.substring(index1,recieved.length());
        return password;
    }
    public boolean signUpOperation(String username, String password, String currentUsername) {
        if (!storage.userName_password.containsKey(username)) {
            storage.fileEntry(username,storage.passwordEncoder(password));
            currentUsername = username;
            return true;
        } else {
            return false;
        }
    }
    public boolean signInOperation(String username, String password, String currentUsername) {
        if (storage.userName_password.containsKey(username) && storage.passwordEncoder(password).equals(storage.userName_password.get(username))) {
            currentUsername = username;
            return true;
        } else {
            return false;
        }
    }
    public boolean exitOperation(String currentUsername) {
        currentUsername = "";
        return true;
    }
    public String operationType(String recieved) {
        Pattern signUp = Pattern.compile("signUp\\s(.){1,}\\s(.){1,}");
        Pattern signIn = Pattern.compile("signIn\\s(.){1,}\\s(.){1,}");
        Pattern exitAccount = Pattern.compile("exitAccount\\s(.){1,}\\s(.){1,}");
        Matcher matcherUp = signUp.matcher(recieved);
        Matcher matcherIn = signIn.matcher(recieved);
        Matcher matcherExit = exitAccount.matcher(recieved);
        if (matcherUp.matches()) {
            return "signUp";
        } else if (matcherIn.matches()) {
            return "signIn";
        } else if (matcherExit.matches()) {
            return "exit";
        } else {
            return "noMatch";
        }
    }
    public boolean executeOperation(String recieved, String currentUsername) {
        boolean answear = true;
        String operation = operationType(recieved);
        switch (operation) {
            case "signUp":
                answear = signUpOperation(getUserName(recieved), getPassword(recieved), currentUsername);
                break;
            case "signIn":
                answear = signInOperation(getUserName(recieved), getPassword(recieved), currentUsername);
                break;
            case "exit":
                answear = exitOperation(currentUsername);
                break;
            default:
                answear = false;
                break;
        }
        return answear;
    }
}
