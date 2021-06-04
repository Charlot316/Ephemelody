package team.seine.ephemelody.playinterface;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Date;

import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.scenes.*;
import team.seine.ephemelody.utils.Rect;

import javax.swing.*;

import static java.awt.BasicStroke.*;

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
    //public int frontNote=0;
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

        if(note.noteType==1&&note.length>0){
            int length=(int)(note.length*Data.HEIGHT);
            polygon1.addPoint(x,y-length-30);
            polygon1.addPoint(x+30,y-length);
            polygon1.addPoint(x+30,y);
            polygon1.addPoint(x,y+30);
            polygon1.addPoint(x-30,y);
            polygon1.addPoint(x-30,y-length);

            polygon2.addPoint(x,y-length-18);
            polygon2.addPoint(x+18,y-length);
            polygon2.addPoint(x,y-length+18);
            polygon2.addPoint(x-18,y-length);

            polygon3.addPoint(x,y-18);
            polygon3.addPoint(x+18,y);
            polygon3.addPoint(x,y+18);
            polygon3.addPoint(x-18,y);

            g_2d.setColor(new Color(22,22,14));
            g_2d.fillPolygon(polygon1);
            g_2d.draw(polygon1);

            g_2d.setColor(new Color(203,105,121));
            g_2d.fillPolygon(polygon2);
            g_2d.draw(polygon2);

            g_2d.setColor(new Color(203,105,121));
            g_2d.fillPolygon(polygon3);
            g_2d.draw(polygon3);

        }
        else{
            polygon1.addPoint(x,y-30);
            polygon1.addPoint(x+30,y);
            polygon1.addPoint(x,y+30);
            polygon1.addPoint(x-30,y);

            polygon3.addPoint(x,y-18);
            polygon3.addPoint(x+18,y);
            polygon3.addPoint(x,y+18);
            polygon3.addPoint(x-18,y);

            g_2d.setColor(new Color(22,22,14));
            g_2d.fillPolygon(polygon1);
            g_2d.draw(polygon1);

            g_2d.setColor(new Color(203,105,121));
            g_2d.fillPolygon(polygon3);
            g_2d.draw(polygon3);
        }


    }

    public void paint(Graphics g) {
        Graphics2D g_2d = (Graphics2D) g;
        int x=(int)(this.positionX*Data.WIDTH);
        int halfWidth=(int)(this.width*Data.WIDTH);
        int y=(int)(Data.HEIGHT*PlayInterface.finalY);

        g_2d.setStroke(new BasicStroke((float)(2*halfWidth),CAP_BUTT, JOIN_BEVEL));
        g_2d.setColor(new Color(this.R, this.G, this.B, 100));
        g_2d.drawLine(x,0,x,y);
        g_2d.setStroke(new BasicStroke(3.0f,CAP_BUTT, JOIN_BEVEL));
        g_2d.setColor(new Color(this.R, this.G, this.B, 100));
        g_2d.drawLine(x,0,x,y);
        g_2d.setColor(new Color(255,255,255, 100));
        g_2d.drawLine(x-halfWidth,0,x-halfWidth,y);
        g_2d.drawLine(x+halfWidth,0,x+halfWidth,y);

        Polygon polygon1 = new Polygon();
        polygon1.addPoint(x,y-10);
        polygon1.addPoint(x+10,y);
        polygon1.addPoint(x,y+10);
        polygon1.addPoint(x-10,y);
        g_2d.setStroke(new BasicStroke(0f));
        g_2d.setColor(new Color(0,0,0, 100));
        g_2d.fillPolygon(polygon1);
        g_2d.draw(polygon1);

        for(Note i:this.currentNotes){
            getImg(g,i);
        }

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
                this.R=(this.R>255||this.R<0)?this.R:this.R-(int)(((double)(this.R-currentColor.endR)/(double)(currentColor.endTime-this.trackCurrentTime))*(double)(this.trackCurrentTime-this.lastTime));
                this.G=(this.G>255||this.G<0)?this.G:this.G-(int)(((double)(this.G-currentColor.endG)/(double)(currentColor.endTime-this.trackCurrentTime))*(double)(this.trackCurrentTime-this.lastTime));
                this.B=(this.B>255||this.B<0)?this.B:this.B-(int)(((double)(this.B-currentColor.endB)/(double)(currentColor.endTime-this.trackCurrentTime))*(double)(this.trackCurrentTime-this.lastTime));
//                System.out.println((this.R-currentColor.endR)+" "+(currentColor.endTime-this.trackCurrentTime)+" "+(this.trackCurrentTime-this.lastTime));
//                System.out.println((int)(((double)(this.R-currentColor.endR)/(double)(currentColor.endTime-this.trackCurrentTime))*(double)(this.trackCurrentTime-this.lastTime))+","+(int)(((double)(this.G-currentColor.endG)/(double)(currentColor.endTime-this.trackCurrentTime))*(double)(this.trackCurrentTime-this.lastTime))+","+(int)(((double)(this.B-currentColor.endB)/(double)(currentColor.endTime-this.trackCurrentTime))*(double)(this.trackCurrentTime-this.lastTime)));

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

        this.trackCurrentTime=this.lastTime=System.currentTimeMillis()-PlayInterface.startTime;
        while(this.trackCurrentTime<this.endTiming&&this.startTiming<=this.trackCurrentTime){
            this.lastTime=this.trackCurrentTime;
            this.trackCurrentTime=System.currentTimeMillis()-PlayInterface.startTime;
            this.changeColor();
            this.changeWidth();
            this.moveTrack();
            if(!this.notes.isEmpty()){
                    while((rearNote+1<this.notes.size())&&(this.notes.get(rearNote+1).timing-PlayInterface.remainingTime)<this.trackCurrentTime){
                        rearNote++;
                        this.currentNotes.add(notes.get(rearNote));
                    }
                for(Note i:currentNotes){
                    i.moveNote(this.trackCurrentTime-this.lastTime);
                }
                if (!this.currentNotes.isEmpty()&&this.currentNotes.get(0).noteType==0&&this.trackCurrentTime>this.currentNotes.get(0).timing+150){
                    PlayInterface.combo.set(0);
                    PlayInterface.lostCount.getAndIncrement();
                    this.currentNotes.remove(0);
                }
                else if(!this.currentNotes.isEmpty()&&this.currentNotes.get(0).noteType==1&&this.trackCurrentTime>this.currentNotes.get(0).endTiming+150){
                    PlayInterface.combo.set(0);
                    PlayInterface.lostCount.getAndIncrement();
                    this.currentNotes.remove(0);
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

