package sample;


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
    public String getPlayerName(){return playerName;}
    public int getHighscore(){return highscore;}
    public String getDateCreated(){return dateCreated;}
    public int getCredits(){return credits;}
}
