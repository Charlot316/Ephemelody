package database.Entity;


import java.sql.Timestamp;
import java.util.Date;

public class Record {
    private String playerID;
    private Timestamp time;
    private int songID;
    private int songDifficulty;
    private int pureCount;
    private int farCount;
    private int lostCount;
    private int maxCombo;
    private double potential;
    private int score;

    public Record() {
    }

    public Record(String playerID, Timestamp time, int songID, int songDifficulty,
                  int pureCount, int farCount, int lostCount, int maxCombo,
                  double potential,int score) {
        this.playerID = playerID;
        this.time = time;
        this.songID = songID;
        this.songDifficulty = songDifficulty;
        this.pureCount = pureCount;
        this.farCount = farCount;
        this.lostCount = lostCount;
        this.maxCombo = maxCombo;
        this.potential = potential;
        this.score = score;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getSongID() {
        return songID;
    }

    public void setSongID(int songID) {
        this.songID = songID;
    }

    public int getSongDifficulty() {
        return songDifficulty;
    }

    public void setSongDifficulty(int songDifficulty) {
        this.songDifficulty = songDifficulty;
    }

    public int getPureCount() {
        return pureCount;
    }

    public void setPureCount(int pureCount) {
        this.pureCount = pureCount;
    }

    public int getFarCount() {
        return farCount;
    }

    public void setFarCount(int farCount) {
        this.farCount = farCount;
    }

    public int getLostCount() {
        return lostCount;
    }

    public void setLostCount(int lostCount) {
        this.lostCount = lostCount;
    }

    public int getMaxCombo() {
        return maxCombo;
    }

    public void setMaxCombo(int maxCombo) {
        this.maxCombo = maxCombo;
    }

    public double getPotential() {
        return potential;
    }

    public void setPotential(double potential) {
        this.potential = potential;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Record{" +
                "playerID='" + playerID + '\'' +
                ", time=" + time +
                ", songID=" + songID +
                ", songDifficulty=" + songDifficulty +
                ", pureCount=" + pureCount +
                ", farCount=" + farCount +
                ", lostCount=" + lostCount +
                ", maxCombo=" + maxCombo +
                ", potential=" + potential +
                ", score=" + score +
                '}';
    }
}
