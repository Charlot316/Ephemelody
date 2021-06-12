package team.seine.ephemelody.playinterface;

import team.seine.ephemelody.scenes.PlayInterface;

public class Note extends Thread {
    public Track basedTrack;//指明所属的轨道
    public double positionX;//音符的横坐标，恒等于其所属轨道
    public double positionY;//音符的纵坐标
    public int noteType;//指明音符类型。 noteType=0 "单点" noteType=1 "长按"
    public char key;//与音符对应的键盘按钮
    public long timing;//音符的标准时机
    public long endTiming;//长键的结束时机
    public double length;//长键的长度
    public long lastTime;//上一时刻
    public long currentTime;//这一时刻

    public Track getBasedTrack() {
        return basedTrack;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public int getNoteType() {
        return noteType;
    }

    public char getKey() {
        return key;
    }

    public long getTiming() {
        return timing;
    }

    public long getEndTiming() {
        return endTiming;
    }

    public double getLength() {
        return length;
    }

    public long getLastTime() {
        return lastTime;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setBasedTrack(Track basedTrack) {
        this.basedTrack = basedTrack;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public void setNoteType(int noteType) {
        this.noteType = noteType;
    }

    public void setKey(char key) {
        this.key = key;
    }

    public void setTiming(long timing) {
        this.timing = timing;
    }

    public void setEndTiming(long endTiming) {
        this.endTiming = endTiming;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public Note(Track basedTrack, int noteType, char key, long timing) {
        this.basedTrack = basedTrack;
        this.noteType = noteType;
        this.key = key;
        this.timing = timing;
        this.positionY = 0;
    }

    public Note(Track basedTrack, int noteType, char key, long startTiming, long endTiming) {
        this.basedTrack = basedTrack;
        this.noteType = noteType;
        this.key = key;
        this.timing = startTiming;
        this.endTiming = endTiming;
        this.length = ((double) (this.endTiming - this.timing) / (double) PlayInterface.remainingTime) * PlayInterface.finalY;
        this.positionY = 0;
    }

    /**
     * 实时更新音符的坐标
     * 相关公式： 当前位置 = 上一位置 - ((上一位置 - 终点位置)/(标准时间 - 当前时间)* (当前时间 - 上一时刻时间)
     */

    public void moveNote() {
        this.positionX = this.basedTrack.positionX;
        this.lastTime = this.basedTrack.lastTime;
        this.currentTime = this.basedTrack.trackCurrentTime;
        if (this.timing > this.currentTime) {
            this.positionY = this.positionY - ((this.positionY - PlayInterface.finalY) / (double) (this.timing - this.currentTime)) * (double) (this.currentTime - this.lastTime);
        } else if (this.noteType == 1) {
            this.positionY = PlayInterface.finalY;
            this.length = ((double) (this.endTiming - this.currentTime) / (double) PlayInterface.remainingTime) * PlayInterface.finalY;
        } else {
            this.positionY += 0.0001;
        }
    }

    /**
     * 测试用的音符toString
     *
     * @return 返回音符信息
     */
    @Override
    public String toString() {
        return "Note{" +
                "basedTrack=" + basedTrack +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                ", noteType=" + noteType +
                ", key=" + key +
                ", timing=" + timing +
                ", endTiming=" + endTiming +
                ", length=" + length +
                ", lastTime=" + lastTime +
                ", currentTime=" + currentTime +
                '}';
    }


}
