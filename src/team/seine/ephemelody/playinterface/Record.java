package team.seine.ephemelody.playinterface;

public class Record {
//    score purecount farcount lostcount combo
    public int score;
    public int pureCount;
    public int farCount;
    public int lostCount;
    public int combo;
    public int way; // 展示方法
    public Record(int way) {
        this.way = way;
    }
    public Record(int score, int pureCount, int farCount, int lostCount, int combo) {
        this.score = score;
        this.pureCount = pureCount;
        this.farCount = farCount;
        this.lostCount = lostCount;
        this.combo = combo;
    }
    public int getWay() {
        return way;
    }
}
