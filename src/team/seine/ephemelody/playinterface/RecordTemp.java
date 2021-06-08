package team.seine.ephemelody.playinterface;

import java.util.concurrent.atomic.AtomicInteger;

public class RecordTemp {
    public int score;
    public AtomicInteger pureCount;
    public AtomicInteger farCount;
    public AtomicInteger lostCount;
    public AtomicInteger combo;
    public int way; // 展示方法
    public RecordTemp(int way) {
        this.way = way;
    }
    public RecordTemp(int score, AtomicInteger pureCount, AtomicInteger farCount, AtomicInteger lostCount, AtomicInteger combo, int way) {
        this.score = score;
        this.pureCount = pureCount;
        this.farCount = farCount;
        this.lostCount = lostCount;
        this.combo = combo;
        this.way = way;
    }
}
