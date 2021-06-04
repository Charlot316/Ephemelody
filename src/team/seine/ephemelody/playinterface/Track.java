package team.seine.ephemelody.playinterface;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Date;

import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.scenes.*;
import team.seine.ephemelody.utils.Rect;

import javax.swing.*;

public class Track extends JPanel implements Runnable, Scenes {// The track of the note.
    public int id;
    public int type;
    public char key;
    public long startTiming;
    public long endTiming;
    public double positionX;
    public double width;
    public int R;
    public int G;
    public int B;
    public char currentKey;//Equals to the key of the first note
    public boolean isHolding;//Check if is holding a note
    public boolean lastStatus;//last status of currentKey
    public boolean currentStatus;//current status of currentKey
    public int tempJudge;
    public ArrayList<Note> notes = new ArrayList<>();//Notes that this track contains
    public ArrayList<PlayOperations> moveOperations = new ArrayList<>();
    public ArrayList<PlayOperations> changeWidthOperations = new ArrayList<>();
    public ArrayList<PlayOperations> changeColorOperations = new ArrayList<>();
    public ArrayList<Note> currentNotes = new ArrayList<>();
    public PlayOperations currentMove=null;
    public PlayOperations currentWidth=null;
    public PlayOperations currentColor=null;
    public int frontNote=0;
    public int rearNote=-1;
    public int frontMove=0;
    public int frontWidth=0;
    public int frontColor=0;
    public Graphics2D Instance;
    public long lastTime;
    public long trackCurrentTime;

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
        setBounds(0, 0, Data.WIDTH, Data.HEIGHT);
        this.setVisible(true);
        setLayout(null);
        setOpaque(false);
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
     * Paint the notes and track.
     * @param g the brush
     */
    public void getImg(Graphics g,Note note) {
        Graphics2D g_2d = (Graphics2D) g;
        int x=(int)(note.positionX*Data.WIDTH);
        int y=(int)(note.positionY*Data.HEIGHT);
        Polygon polygon1 = new Polygon();
        Polygon polygon2 = new Polygon();
        Polygon polygon3 = new Polygon();
        if(note.noteType==0){
            polygon1.addPoint(x,y-15);
            polygon1.addPoint(x+15,y);
            polygon1.addPoint(x,y+15);
            polygon1.addPoint(x-15,y);

            polygon2.addPoint(x,y-10);
            polygon2.addPoint(x+10,y);
            polygon2.addPoint(x,y+10);
            polygon2.addPoint(x-10,y);

            polygon3.addPoint(x,y-5);
            polygon3.addPoint(x+5,y);
            polygon3.addPoint(x,y+5);
            polygon3.addPoint(x-5,y);
        }
        else{
            polygon1.addPoint(x,y-15);
            polygon1.addPoint(x+15,y);
            polygon1.addPoint(x,y+15);
            polygon1.addPoint(x-15,y);

        }

        g_2d.setColor(Color.WHITE);
        g_2d.fillPolygon(polygon1);
        g_2d.draw(polygon1);

        g_2d.setColor(Color.YELLOW);
        g_2d.fillPolygon(polygon2);
        g_2d.draw(polygon2);

        g_2d.setColor(Color.RED);
        g_2d.fillPolygon(polygon3);
        g_2d.draw(polygon3);
    }

    public void paint(Graphics g) {
        Graphics2D g_2d = (Graphics2D) g;
        Rectangle2D rect = new Rectangle2D.Double((int)((this.positionX*Data.WIDTH)-(this.width*Data.WIDTH)), 0, (this.width*2*Data.WIDTH), (int)(Data.HEIGHT*PlayInterface.finalY));
        g_2d.setColor(new Color(this.R, this.G, this.B, 100));
        g_2d.fill(rect);


        //记得画指示键！



    }

    /**
     * Judge the timing;
     */
    public void Judge(){

    }
    /**
     * Responsible for calculating the positionX at each moment, starting from positionX at currentTime, and endX at endTime
     */
    public void moveTrack() {
        if(moveOperations.isEmpty()){
            return;
        }
        currentMove=moveOperations.get(frontMove);
        if(currentMove.startTime<this.trackCurrentTime){
            if(currentMove.startTime==currentMove.endTime) this.positionX=currentMove.endX;
            else if(currentMove.endTime-this.trackCurrentTime>0) this.positionX=this.positionX-((this.positionX-currentMove.endX)/(double)(currentMove.endTime-this.trackCurrentTime))*(double)(this.trackCurrentTime-this.lastTime);
        }
        if(currentMove.endTime<this.trackCurrentTime&&(frontMove+1)<this.moveOperations.size()) frontMove++;
    }

    /**
     * Responsible for calculating the width at each moment, starting from width at currentTime, and endWidth at endTime
     */
    public void changeWidth(){
        if(changeWidthOperations.isEmpty()){
            return;
        }
        currentWidth=changeWidthOperations.get(frontWidth);
        if(currentWidth.startTime<this.trackCurrentTime){
            if(currentWidth.startTime==currentWidth.endTime) this.width=currentWidth.endWidth;
            else if(currentWidth.endTime-this.trackCurrentTime>0) this.width=this.width-((this.width-currentWidth.endWidth)/(double)(currentWidth.endTime-this.trackCurrentTime))*(double)(this.trackCurrentTime-this.lastTime);

        }
        if(currentWidth.endTime<this.trackCurrentTime&&(frontWidth+1)<this.changeWidthOperations.size()) frontWidth++;
    }

    /**
     * Responsible for calculating the current color at each moment, starting from width at currentTime, and endWidth at endTime
     */
    public void changeColor(){
        if(changeColorOperations.isEmpty()){
            return;
        }
        currentColor=changeColorOperations.get(frontColor);
        if(currentColor.startTime<this.trackCurrentTime){
            if(currentColor.startTime==currentColor.endTime){
                this.R=currentColor.endR;
                this.G=currentColor.endG;
                this.B=currentColor.endB;
            }
            else if(currentColor.endTime-this.trackCurrentTime>0){
                this.R=this.R-(int)(((double)(this.R-currentColor.endR)/(double)(currentColor.endTime-this.trackCurrentTime))*(double)(this.trackCurrentTime-this.lastTime));
                this.G=this.G-(int)(((double)(this.G-currentColor.endG)/(double)(currentColor.endTime-this.trackCurrentTime))*(double)(this.trackCurrentTime-this.lastTime));
                this.B=this.B-(int)(((double)(this.B-currentColor.endB)/(double)(currentColor.endTime-this.trackCurrentTime))*(double)(this.trackCurrentTime-this.lastTime));
                System.out.println((this.R-currentColor.endR)+" "+(currentColor.endTime-this.trackCurrentTime)+" "+(this.trackCurrentTime-this.lastTime));
                System.out.println((int)(((double)(this.R-currentColor.endR)/(double)(currentColor.endTime-this.trackCurrentTime))*(double)(this.trackCurrentTime-this.lastTime))+","+(int)(((double)(this.G-currentColor.endG)/(double)(currentColor.endTime-this.trackCurrentTime))*(double)(this.trackCurrentTime-this.lastTime))+","+(int)(((double)(this.B-currentColor.endB)/(double)(currentColor.endTime-this.trackCurrentTime))*(double)(this.trackCurrentTime-this.lastTime)));
            }
            //System.out.println(this.R+","+this.G+","+this.B);
        }
        if(currentColor.endTime<this.trackCurrentTime&&(frontColor+1)<this.changeColorOperations.size()) frontColor++;
    }
    /**
     * Displays the track and notes on the screen according to the positionX and width and the currentNotes list of the current frame
     * Note: display must be after the move and change operations are finished
     */
    public void run(){
    System.out.println(this.changeWidthOperations);
    System.out.println(this.changeColorOperations);
        this.trackCurrentTime=this.lastTime=System.currentTimeMillis()-PlayInterface.startTime;
        while(this.trackCurrentTime<this.endTiming&&this.startTiming<=this.trackCurrentTime){
            this.lastTime=this.trackCurrentTime;
            this.trackCurrentTime=System.currentTimeMillis()-PlayInterface.startTime;
            this.changeColor();
            this.changeWidth();
            this.moveTrack();
            if(!this.notes.isEmpty()){
                if(rearNote+1<this.notes.size())
                    while(this.notes.get(rearNote+1).timing+PlayInterface.remainingTime<this.trackCurrentTime){
                        rearNote++;
                        this.currentNotes.add(notes.get(rearNote));
                    }
                if (this.notes.get(frontNote).timing<this.trackCurrentTime+150){
                    frontNote++;
                    PlayInterface.combo.set(0);
                    PlayInterface.lostCount.getAndIncrement();
                    this.currentNotes.remove(0);
                }
                for(Note i:currentNotes){
                    i.moveNote();
                }
            }
            this.repaint();
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
    @Override
    public void onKeyDown(int keyCode) {

    }

    @Override
    public void onKeyUp(int keyCode) {

    }

    public void onMouse(int x, int y, int struts) {
//        System.out.println(x + " " + y);

    }
}

