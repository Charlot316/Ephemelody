package team.seine.ephemelody.playinterface;


public abstract class Note extends Thread {//Note, main article of the play interface
    Track basedTrack;//Track that this note belongs to
    double positionX;//The ratio of the horizontal axis of the note to the the length of the entire screen, equals to the x of the track which it belongs to
    double positionY;//The ratio of the vertical axis of the note to the the width of the entire screen
    int noteType;//Denotes the type of the note. type=0 "hit" type=1 "hold"
    char key;//The keyboard keys corresponding to that note
    long timing;//The standard timing of a note
    long endTiming;//
    double length;//The length of a hold calculated from startTiming and endTiming
    long lastTime;
    long noteCurrentTime;

    public Note(Track basedTrack, int noteType, char key, long timing) {
        this.basedTrack = basedTrack;
        this.noteType = noteType;
        this.key = key;
        this.timing = timing;
    }

    public Note(Track basedTrack, int noteType, char key, long startTiming, long endTiming) {
        this.basedTrack = basedTrack;
        this.noteType = noteType;
        this.key = key;
        this.timing = startTiming;
        this.endTiming = endTiming;
        this.length=((double)(this.endTiming-this.timing)/(double)PlayInterface.remainingTime)*PlayInterface.finalY;
    }

    /**
     * Responsible for calculating the positionY of the note at each moment, and synchronizing the positionX with the track of the note.
     * Refer to the formula: current position = last time position - ((last time position - final position)/(retention time - (standard time of the note - current time))* (1/ screen refresh rate)
     */

    public void moveNote(){
        this.lastTime=this.noteCurrentTime;
        this.noteCurrentTime=PlayInterface.currentTime;

        this.positionX=this.basedTrack.positionX;
        if(this.noteType==0||this.noteType==1&&this.timing<this.noteCurrentTime){
            this.positionY=this.positionY+((PlayInterface.finalY-this.positionY)/(double)(PlayInterface.remainingTime-(this.timing-this.noteCurrentTime)))*(double)(this.noteCurrentTime-this.lastTime);
        }
        else {
            this.positionY=PlayInterface.finalY;
            this.length=((double)(this.endTiming-this.noteCurrentTime)/(double)PlayInterface.remainingTime)*PlayInterface.finalY;
        }
    }


    /**
     * Displays tracks on the screen according to the current frame's positionX and positionY
     * Note: display must be the last function to be called. It waits for the move operation to finish
     */
    public abstract void run();
}
