package team.seine.ephemelody.playinterface;

import team.seine.ephemelody.scenes.PlayInterface;

public class Note extends Thread {//Note, main article of the play interface
    public Track basedTrack;//Track that this note belongs to
    public double positionX;//The ratio of the horizontal axis of the note to the the length of the entire screen, equals to the x of the track which it belongs to
    public double positionY;//The ratio of the vertical axis of the note to the the width of the entire screen
    public int noteType;//Denotes the type of the note. type=0 "hit" type=1 "hold"
    public char key;//The keyboard keys corresponding to that note
    public long timing;//The standard timing of a note
    public long endTiming;//
    public double length;//The length of a hold calculated from startTiming and endTiming
    public long lastTime;
    public long currentTime;
    public boolean printed=false;

    public Note(Track basedTrack, int noteType, char key, long timing) {
        this.basedTrack = basedTrack;
        this.noteType = noteType;
        this.key = key;
        this.timing = timing;
        this.positionY=0;
    }

    public Note(Track basedTrack, int noteType, char key, long startTiming, long endTiming) {
        this.basedTrack = basedTrack;
        this.noteType = noteType;
        this.key = key;
        this.timing = startTiming;
        this.endTiming = endTiming;
        this.length=((double)(this.endTiming-this.timing)/(double) PlayInterface.remainingTime)*PlayInterface.finalY;
        this.positionY=0;
    }

    /**
     * Responsible for calculating the positionY of the note at each moment, and synchronizing the positionX with the track of the note.
     * Refer to the formula: current position = last time position - ((last time position - final position)/(retention time - (standard time of the note - current time))* (1/ screen refresh rate)
     */

    public void moveNote(){
        this.positionX=this.basedTrack.positionX;
        this.lastTime=this.currentTime;
        this.currentTime=System.currentTimeMillis()-PlayInterface.startTime;
        if(Math.abs(this.lastTime-this.currentTime)>30) {
            this.lastTime=this.currentTime;

        }
        if(this.timing>this.currentTime){
            this.positionY=this.positionY-((this.positionY-PlayInterface.finalY)/(double)(this.timing-this.currentTime))*(double)(this.currentTime-this.lastTime);
            //if(this.positionY>0.8) System.out.println(this.currentTime+" "+this.lastTime);
        }
        else if(this.noteType==1){
            this.positionY=PlayInterface.finalY;
            this.length=((double)(this.endTiming-this.currentTime)/(double)PlayInterface.remainingTime)*PlayInterface.finalY;
        }
        else{
            this.positionY+=0.0001;
        }
    }

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
                ", printed=" + printed +
                '}';
    }
}
