package sample;

/**
 * Score profile class that stores score, playername, highscore, date created, and credits
 * by David Rogers
 */
public class ScoreProfile {
    public int score;
    public String playerName;
    public int highscore;
    public String dateCreated;
    public int credits;


    public ScoreProfile(String playerName, int highscore, String dateCreated, int credits){
        this.playerName = playerName;
        this.highscore = highscore;
        this.dateCreated = dateCreated;
        this.credits = credits;
    }
}
