package sample;


public class ScoreProfile {

    public String playerName;
    public int highscore;
    public String dateCreated;
    public String customMessage;
    public int credits;


    public ScoreProfile(String playerName, int highscore, String dateCreated, String customMessage, int credits){
        this.playerName = playerName;
        this.highscore = highscore;
        this.dateCreated = dateCreated;
        this.customMessage = customMessage;
        this.credits = credits;
    }
}
