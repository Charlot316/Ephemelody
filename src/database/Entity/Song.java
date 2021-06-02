package database.Entity;

public class Song {
    private int songID;
    private int songDifficulty;
    private double chartConstant;
    private int noteCount;

    public Song() {
    }

    public Song(int songID, int songDifficulty, double chartConstant, int noteCount) {
        this.songID = songID;
        this.songDifficulty = songDifficulty;
        this.chartConstant = chartConstant;
        this.noteCount = noteCount;
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

    public double getChartConstant() {
        return chartConstant;
    }

    public void setChartConstant(double chartConstant) {
        this.chartConstant = chartConstant;
    }

    public int getNoteCount() {
        return noteCount;
    }

    public void setNoteCount(int noteCount) {
        this.noteCount = noteCount;
    }
}
