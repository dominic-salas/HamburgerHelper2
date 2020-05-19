package sample;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Manages the score accounts
 * by David Rogers
 */
public class ScoreManager {
    public LinkedList<ScoreProfile> profiles = new LinkedList<>();
    public static ScoreProfile activeProfile = null;
    Scanner scanner = new Scanner(System.in);

    FileReader fr;
    BufferedReader br;
    FileWriter fw;
    BufferedWriter bw;
    File scoreFile = new File("Resources/highscores.txt");

    /**
     * creates file reader and buffered reader
     */
    public ScoreManager() {
        try {
            fr = new FileReader(scoreFile);
            br = new BufferedReader(fr);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * called by other classes to add score to the current active account
     *
     * @param score score to be added
     */
    public static void addScore(int score) {
        activeProfile.score += score;
        activeProfile.credits += score;
        if (activeProfile.score > activeProfile.highscore) { //update highscore if score is greater than it
            activeProfile.highscore = activeProfile.score;
        }
    }

    /**
     * creates a new account if an account is not already found
     */
    public void createNewAccount() {
        System.out.println("please enter a name");
        String name = scanner.nextLine();

        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.now();
        String date = dateformatter.format(dateTime);
        System.out.println("Account: '" + name + "' created at: " + date);

        profiles.add(new ScoreProfile(name, 0, date, 0));
    }

    /**
     * overloads createNewAccount() if the name is already created
     *
     * @param name
     */
    public void createNewAccount(String name) { //method overload for if name is already created TODO i have no idea what this means david u gotta fix
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.now();
        String date = dateformatter.format(dateTime);
        System.out.println("Account: '" + name + "' created at: " + date);
        profiles.add(new ScoreProfile(name, 0, date, 0));
        activeProfile = profiles.getLast();
        storeProfiles();
    }

    /**
     * Finds an account if the string entered matches the name of an account
     *
     * @param accountCheckName to be compared with all accounts
     */
    public void selectAccount(String accountCheckName) {
        AtomicBoolean found = new AtomicBoolean(false);
        profiles.forEach(scoreProfile -> {
            if (scoreProfile.playerName.equals(accountCheckName)) {
                found.set(true);
                activeProfile = scoreProfile; //find a way to break
            }
        });
        if (found.get()) {
            System.out.println("Account Found!\nHere are your stats:\n    Account Name: " + activeProfile.playerName + "\n    Highscore: " + activeProfile.highscore + "\n    Credits: " + activeProfile.credits + "\n    Created on: " + activeProfile.dateCreated);
        } else {
            System.out.println("no account found, we've created you new one!");
            createNewAccount(accountCheckName);
        }
    }

    private void sortScores() {
    }

    /**
     * saves all profiles if they were updated
     */
    public void storeProfiles() {
        try {
            fw = new FileWriter(scoreFile, false);
            bw = new BufferedWriter(fw);
            profiles.forEach(scoreProfile -> {
                try {
                    bw.write(scoreProfile.playerName);
                    bw.write("\n" + scoreProfile.highscore);
                    bw.write("\n" + scoreProfile.dateCreated);
                    bw.write("\n" + scoreProfile.credits + "\n");
                }catch (Exception e){
                    System.out.println(e);
                }
            });
            bw.flush();
            bw.close();
        } catch (Exception e) {
            System.out.println("wuh woh, wooks wike youw scowe couwdn't be saved." + e);
        }

    }

    /**
     * grabs all profiles from highscores.txt
     */
    public void loadProfiles() {
        try {
            long lineTotal = Files.lines(scoreFile.toPath()).count();
            for (int lineNumber = 0; lineNumber < lineTotal; lineNumber++) {
                if (lineNumber % 4 == 0) {
                    String name = br.readLine();
                    int highscore = Integer.parseInt(br.readLine());
                    String createdOn = br.readLine();
                    int credits = Integer.parseInt(br.readLine());
                    profiles.add(new ScoreProfile(name, highscore,createdOn,credits));
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }


    }

    private void printScores() {
    }
}
