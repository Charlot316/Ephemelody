package team.seine.ephemelody.scenes;

import team.seine.ephemelody.playinterface.*;
public class PlayOperations {//Denotes the operations the game should implement
    public int trackId;
    public int type;
    public long startTime;
    public long endTime;
    public double endX;
    public double endWidth;
    public int endR;
    public int endG;
    public int endB;
    public String background;

    /**
     * Constructor of the operation
     * @param trackId Indicates the track it related to
     * @param type Define which type of operation it is. 1:move 2:change width 3: change color 4:change background
     * @param startTime Define the start timing of the operation
     * @param endTime Define the end timing of the operation
     * @param endX Define the final state of track's X
     * @param endWidth Define the final state of track's width
     * @param endR Define the final color of the track
     * @param endG Define the final color of the track
     * @param endB Define the final color of the track
     * @param background Define the path of the background
     */
    public PlayOperations(int trackId, int type, long startTime, long endTime, double endX,double endWidth,int endR, int endG, int endB,String background) {
        this.trackId = trackId;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.endX = endX;
        this.endWidth=endWidth;
        this.endR = endR;
        this.endG = endG;
        this.endB = endB;
        this.background = background;
    }

    @Override
    public String toString() {
        return "PlayOperations{" +
                "trackId=" + trackId +
                ", type=" + type +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", endX=" + endX +
                ", endWidth=" + endWidth +
                ", endR=" + endR +
                ", endG=" + endG +
                ", endB=" + endB +
                ", background='" + background + '\'' +
                '}';
    }
}
