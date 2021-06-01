package team.seine.ephemelody.playinterface;

import java.awt.*;
import java.util.ArrayList;

public class Track extends Thread {// The track of the note.
    int id;
    int type;
    char key;
    long startTiming;
    long endTiming;
    double positionX;
    double width;
    int R;
    int G;
    int B;
    char currentKey;//Equals to the key of the first note
    boolean isHolding;//Check if is holding a note
    boolean lastStatus;//last status of currentKey
    boolean currentStatus;//current status of currentKey
    int tempJudge;
    ArrayList<Note> notes = new ArrayList<>();//Notes that this track contains
    ArrayList<PlayOperations> moveOperations = new ArrayList<>();
    ArrayList<PlayOperations> changeWidthOperations = new ArrayList<>();
    ArrayList<PlayOperations> changeColorOperations = new ArrayList<>();
    int frontNote=0;
    int rearNote=-1;
    int frontMove=0;
    int rearMove=-1;
    int frontWidth=0;
    int rearWidth=-1;
    int frontColor=0;
    int rearColor=-1;
    Graphics2D Instance;
    long lastTime;
    long trackCurrentTime;

    /**
     *
     * @param id Uniquely defines a track
     * @param type Denotes the type of the track. type=0 virtual track type=1 real track
     * @param key The keyboard key corresponding to the track, it is only used to present the indicating key below the real track, and the key corresponding to the note shall prevail in the judgment
     * @param startTiming The start timing of the track
     * @param endTiming The end timing of the track
     * @param positionX The ratio of the horizontal axis of middle of the track to the length of the entire screen
     * @param width The ratio of the half width from the middle of the track to the length of the entire screen
     * @param r Control track's color
     * @param g Control track's color
     * @param b Control track's color
     */
    public Track(int id, int type, char key, long startTiming, long endTiming, double positionX, double width, int r, int g, int b) {
        this.id = id;
        this.type = type;
        this.key = key;
        this.startTiming = startTiming;
        this.endTiming = endTiming;
        this.positionX = positionX;
        this.width = width;
        this.R = r;
        this.G = g;
        this.B = b;
    }

    /**
     * Judge the timing;
     */
    public void Judge(){

    }
    /**
     * Responsible for calculating the positionX at each moment, starting from positionX at currentTime, and endX at endTime
     *
     * @param id      track's id
     * @param endX    the destination of the track
     * @param endTime time when the move should be finished
     */
    public void moveTrack(int id, double endX, long endTime) {

    }

    /**
     * Responsible for calculating the width at each moment, starting from width at currentTime, and endWidth at endTime
     *
     * @param id       track's id
     * @param endWidth the final width of the track
     * @param endTime  time when the distortion should be finished
     */
    public void changeWidth(int id, double endWidth, long endTime){
    }
    /**
     * Displays the track and notes on the screen according to the positionX and width and the currentNotes list of the current frame
     * Note: display must be after the move and change operations are finished
     */
    public void run(){
        this.trackCurrentTime=this.lastTime=System.currentTimeMillis()-PlayInterface.startTime;
        while(this.trackCurrentTime<this.endTiming&&this.startTiming<=this.trackCurrentTime){
            this.lastTime=this.trackCurrentTime;
            this.trackCurrentTime=System.currentTimeMillis()-PlayInterface.startTime;
            if(rearNote+1<this.notes.size())
                while(this.notes.get(rearNote+1).timing<this.trackCurrentTime){
                    rearNote++;
                }
            if (this.notes.get(frontNote).timing>this.trackCurrentTime+150){
                frontNote++;
            }
            /*for(int i=frontNote;i<=rearNote;i++){
                notes.get(i).moveNote();
                *//*

                please paint the note here.

                 *//*
            }*/
            // paintnotes
            // painttracks
            //...
        }
    }

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", type=" + type +
                ", key=" + key +
                ", startTiming=" + startTiming +
                ", endTiming=" + endTiming +
                ", positionX=" + positionX +
                ", width=" + width +
                ", R=" + R +
                ", G=" + G +
                ", B=" + B +
                '}';
    }
}

