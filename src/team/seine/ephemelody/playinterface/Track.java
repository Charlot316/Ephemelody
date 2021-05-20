package team.seine.ephemelody.playinterface;

import java.util.ArrayList;

public abstract class Track extends Thread {// The track of the note.
    int id;//Uniquely defines a track
    String type;//Denotes the type of the track. type=0 virtual track type=1 real track
    double positionX;//The ratio of the horizontal axis of middle of the track to the length of the entire screen
    double width;//The ratio of the half width from the middle of the track to the length of the entire screen
    char key;//The keyboard key corresponding to the track, it is only used to present the indicating key below the real track, and the key corresponding to the note shall prevail in the judgment
    long startTiming;//The start timing of the track
    long endTiming;//The end timing of the track
    int R;
    int G;
    int B;//Control track's color
    char currentKey;//Equals to the key of the first note
    boolean isHolding;//Check if is holding a note
    boolean lastStatus;//last status of currentKey
    boolean currentStatus;//current status of currentKey
    int tempJudge;
    ArrayList<Note> notes = new ArrayList<>();//Notes that this track contains
    int frontNote;
    int rearNote;

    public abstract void Judge();

    /**
     * Displays the track and notes on the screen according to the positionX and width and the currentNotes list of the current frame
     * Note: display must be after the move and change operations are finished
     */
    public abstract void run();
}

