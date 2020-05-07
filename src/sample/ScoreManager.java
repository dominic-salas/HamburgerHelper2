package sample;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScoreManager {
    private LinkedList<ScoreProfile> profiles = new LinkedList<>();
    private ScoreProfile activeProfile;
    private int score;
    Scanner scanner = new Scanner(System.in);

    FileReader fr;
    BufferedReader br;
    FileWriter fw;
    BufferedWriter bw;
    File scoreFile = new File("Resources/highscores.txt");

    public ScoreManager(){
        try {
            fr = new FileReader(scoreFile);
            br = new BufferedReader(fr);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void createNewAccount(){
        System.out.println("please enter a name");
        String name = scanner.nextLine();
        System.out.println("please enter custom message");
        String custom =scanner.nextLine();

        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.now();
        String date = dateformatter.format(dateTime);
        System.out.println("Account: '"+name+"' created at: "+date);

        profiles.add(new ScoreProfile(name,0,date,custom,0));
    }
    public void createNewAccount(String name){ //method overload for if name is already created
        System.out.println("please enter custom message");
        String custom =scanner.nextLine();

        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.now();
        String date = dateformatter.format(dateTime);
        System.out.println("Account: '"+name+"' created at: "+date);

        profiles.add(new ScoreProfile(name,0,date,custom,0));
    }

    public void selectAccount(){
        System.out.println("please enter account name");
        String accountCheckName= scanner.nextLine();
        AtomicBoolean found = new AtomicBoolean(false);
        profiles.forEach(scoreProfile -> {
            if(scoreProfile.playerName.equals(accountCheckName)){
                found.set(true);
                activeProfile =scoreProfile; //find a way to break
            }
        });
        if(found.get()){
            System.out.println("Account Found!\nHere are your stats:\n    Account Name: "+activeProfile.playerName+"\n    Highscore: "+activeProfile.highscore+"\n    Credits: "+activeProfile.credits+"\n    Custom Message: "+activeProfile.customMessage+"\n    Created on: "+activeProfile.dateCreated);
        }else{
            System.out.println("no account found, we've created you new one!");
            createNewAccount(accountCheckName);
        }
    }

    private void sortScores() {
    }

    public void storeProfiles() {
            try {
                fw = new FileWriter(scoreFile, false);
                bw = new BufferedWriter(fw);
                profiles.forEach(scoreProfile ->{
                    try{
                        bw.write(scoreProfile.playerName);
                        bw.write("\n"+scoreProfile.highscore);
                        bw.write("\n"+scoreProfile.dateCreated);
                        bw.write("\n"+scoreProfile.customMessage);
                        bw.write("\n"+scoreProfile.credits);
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

    private void readScores() {
    }

    private void printScores() {
    }
}
