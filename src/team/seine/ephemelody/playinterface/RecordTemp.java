package team.seine.ephemelody.playinterface;

import java.util.concurrent.atomic.AtomicInteger;

public class RecordTemp {
    public int score;
    public AtomicInteger pureCount;
    public AtomicInteger farCount;
    public AtomicInteger lostCount;
    public AtomicInteger combo;
    public int way; // 展示方法
    public double changePotential;
    public double nowPotential;
    public RecordTemp(int way) {
        this.way = way;
    }

    public int getScore() {
        return score;
    }

    public AtomicInteger getPureCount() {
        return pureCount;
    }

    public AtomicInteger getFarCount() {
        return farCount;
    }

    public AtomicInteger getLostCount() {
        return lostCount;
    }

    public AtomicInteger getCombo() {
        return combo;
    }

    public int getWay() {
        return way;
    }

    public double getChangePotential() {
        return changePotential;
    }

    public double getNowPotential() {
        return nowPotential;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setPureCount(AtomicInteger pureCount) {
        this.pureCount = pureCount;
    }

    public void setFarCount(AtomicInteger farCount) {
        this.farCount = farCount;
    }

    public void setLostCount(AtomicInteger lostCount) {
        this.lostCount = lostCount;
    }

    public void setCombo(AtomicInteger combo) {
        this.combo = combo;
    }

    public void setWay(int way) {
        this.way = way;
    }

    public void setChangePotential(double changePotential) {
        this.changePotential = changePotential;
    }

    public void setNowPotential(double nowPotential) {
        this.nowPotential = nowPotential;
    }

    public RecordTemp(int score, AtomicInteger pureCount, AtomicInteger farCount, AtomicInteger
            lostCount, AtomicInteger combo, int way, double changePotential, double nowPotential) {
        this.score = score;
        this.pureCount = pureCount;
        this.farCount = farCount;
        this.lostCount = lostCount;
        this.combo = combo;
        this.way = way;
        this.changePotential = changePotential;
        this.nowPotential = nowPotential;
    }

    @Override
    public String toString() {
        return "RecordTemp{" +
                "score=" + score +
                ", pureCount=" + pureCount +
                ", farCount=" + farCount +
                ", lostCount=" + lostCount +
                ", combo=" + combo +
                ", way=" + way +
                ", changePotential=" + changePotential +
                ", nowPotential=" + nowPotential +
                '}';
    }
}
