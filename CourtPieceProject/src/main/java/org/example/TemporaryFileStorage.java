package org.example;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;
//This class is about storing and recovering data
public class TemporaryFileStorage {
    File file = new File("Temporary_Storage.txt");
    public static HashMap<String,String> userName_password = new HashMap<>();
    public void writeFile(String userName, String password) {
        try {
            FileWriter writer = new FileWriter(file,true);
            writer.write( userName + "\n" + password + "\n" );
            writer.close();
        }catch (Exception e) {
            System.out.println("error : failed to write the file");
        }
    }
    public void readeFile() {
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
    public void fileEntry(String userName, String password) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        }catch (Exception e) {
            System.out.println("error : failed to create new file");
        }
        writeFile(userName, password);
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