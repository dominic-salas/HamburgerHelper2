package sample;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;

public class ScoreManager {
    private File scoresFile = new File("highscores.txt");
    private LinkedList<ScoreProfile> profiles;
    public int currentScore;
    //not too sure what these next two values are supposed to be; they were in the uml with no type
    public boolean checkAccount;
    public Date createAccount;

    private void sortScores() {
    }

    private void storeScores() {
    }

    private void readScores() {
    }

    private void printScores() {
    }
}
