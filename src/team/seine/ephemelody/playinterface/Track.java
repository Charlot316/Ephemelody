package team.seine.ephemelody.playinterface;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.scenes.*;
import team.seine.ephemelody.utils.Load;

import javax.swing.*;

import static java.awt.BasicStroke.*;

public class Track extends JPanel implements Runnable {// The track of the note.
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
    public int currentKey;//Equals to the key of the first note
    public boolean isHolding=false;//Check if is holding a note
    public boolean isStarted=false;
    public boolean isEnded=false;
    public boolean finalEnd=false;
    public int tempJudge=-1;
    public ArrayList<Note> notes = new ArrayList<>();//Notes that this track contains
    public ArrayList<PlayOperations> moveOperations = new ArrayList<>();
    public ArrayList<PlayOperations> changeWidthOperations = new ArrayList<>();
    public ArrayList<PlayOperations> changeColorOperations = new ArrayList<>();
    public PlayOperations currentMove = null;
    public PlayOperations currentWidth = null;
    public PlayOperations currentColor = null;
    public double tempRatio=0;
    public int frontNote=0;
    public int rearNote = -1;
    public int frontMove = 0;
    public int frontWidth = 0;
    public int frontColor = 0;
    public long lastTime;
    public long trackCurrentTime;
    public int[]positionY=new int[200];
    public int displayState=99;
    public Image []judgement=new Image[3];
    public Image currentJudgement;
    public int delay=40;
    public static AtomicInteger isStopped=new AtomicInteger();
    public static AtomicInteger isPaused=new AtomicInteger();

    /**
     * @param id          Uniquely defines a track
     * @param type        Denotes the type of the track. type=0 virtual track type=1 real track
     * @param key         The keyboard key corresponding to the track, it is only used to present the indicating key below the real track, and the key corresponding to the note shall prevail in the judgment
     * @param startTiming The start timing of the track
     * @param endTiming   The end timing of the track
     * @param positionX   The ratio of the horizontal axis of middle of the track to the length of the entire screen
     * @param width       The ratio of the half width from the middle of the track to the length of the entire screen
     * @param r           Control track's color
     * @param g           Control track's color
     * @param b           Control track's color
     */
    public Track(int id, int type, char key, long startTiming, long endTiming, double positionX, double width, int r, int g, int b) {
        isPaused.set(0);
        isStopped.set(0);
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
        positionY[0]=(int)(0.85*PlayInterface.finalY*(double) Data.HEIGHT);
        for(int i=1;i<=25;i++){
            positionY[i]=positionY[i-1]-1;
        }
        for(int i=26;i<100;i++){
            positionY[i]=positionY[i-1];
        }
        judgement[0]= Load.image("judgement/lost.png");
        judgement[1]= Load.image("judgement/far.png");
        judgement[2]= Load.image("judgement/pure.png");
        currentJudgement=judgement[2];
    }

    /**
     * Paint the notes
     *
     * @param g the brush
     */
    public void getImg(Graphics g, Note note) {
        Graphics2D g_2d = (Graphics2D) g;
        int x = (int) (note.positionX * Data.WIDTH);
        int y = (int) (note.positionY * Data.HEIGHT);
        Polygon polygon1 = new Polygon();
        Polygon polygon2 = new Polygon();
        Polygon polygon3 = new Polygon();

        if (note.noteType == 1 && note.length > 0) {
            int length = (int) (note.length * Data.HEIGHT);
            polygon1.addPoint(x, y - length - 30);
            polygon1.addPoint(x + 30, y - length);
            polygon1.addPoint(x + 30, y);
            polygon1.addPoint(x, y + 30);
            polygon1.addPoint(x - 30, y);
            polygon1.addPoint(x - 30, y - length);

            polygon2.addPoint(x, y - length - 18);
            polygon2.addPoint(x + 18, y - length);
            polygon2.addPoint(x, y - length + 18);
            polygon2.addPoint(x - 18, y - length);

            polygon3.addPoint(x, y - 18);
            polygon3.addPoint(x + 18, y);
            polygon3.addPoint(x, y + 18);
            polygon3.addPoint(x - 18, y);

            if(isHolding&&notes.get(frontNote)==note){
                g_2d.setColor(new Color(22, 22, 14));
            }
            else{
                g_2d.setColor(new Color(55, 55, 34));
            }
            g_2d.fillPolygon(polygon1);
            g_2d.draw(polygon1);

            g_2d.setColor(new Color(203, 105, 121));
            g_2d.fillPolygon(polygon2);
            g_2d.draw(polygon2);

        } else {
            polygon1.addPoint(x, y - 30);
            polygon1.addPoint(x + 30, y);
            polygon1.addPoint(x, y + 30);
            polygon1.addPoint(x - 30, y);

            polygon3.addPoint(x, y - 18);
            polygon3.addPoint(x + 18, y);
            polygon3.addPoint(x, y + 18);
            polygon3.addPoint(x - 18, y);

            g_2d.setColor(new Color(22, 22, 14));
            g_2d.fillPolygon(polygon1);
            g_2d.draw(polygon1);

        }
        g_2d.setColor(new Color(203, 105, 121));
        g_2d.fillPolygon(polygon3);
        g_2d.draw(polygon3);


    }

    /**
     * Paint the track
     * @param g the brush
     */
    public void paint(Graphics g) {
        Graphics2D g_2d = (Graphics2D) g;
        int x = (int) (this.positionX * Data.WIDTH);
        int halfWidth = (int) (this.width * Data.WIDTH);
        int y = (int) (Data.HEIGHT * PlayInterface.finalY);

        if(!isStarted&&!isEnded){

            if(tempRatio+0.05<=1)tempRatio+=0.05;
            if(tempRatio>=0.94) isStarted=true;
        }
        else if(!isEnded&&!finalEnd)
        {
            tempRatio=1;
        }
        else if(isStarted&&isEnded&&!finalEnd){
            if(tempRatio-0.05>=0) tempRatio-=0.05;
            if(tempRatio<=0.06) finalEnd=true;
        }
        g_2d.setStroke(new BasicStroke((float) (2 * halfWidth), CAP_BUTT, JOIN_BEVEL));


        g_2d.setColor(new Color(this.R, this.G, this.B, (int)(100*tempRatio)));
        g_2d.drawLine(x, y,x,(int)((double)y*(1-tempRatio)));
        g_2d.setStroke(new BasicStroke(3.0f, CAP_BUTT, JOIN_BEVEL));
        g_2d.setColor(new Color(255,255,255, (int)(100*tempRatio)));
        g_2d.drawLine( x,y,x,(int)((double)y*(1-tempRatio)));
        g_2d.drawLine(x - halfWidth, y,x - halfWidth, (int)((double)y*(1-tempRatio)));
        g_2d.drawLine(x + halfWidth, y,x + halfWidth,(int)((double)y*(1-tempRatio)));

        Polygon polygon1 = new Polygon();
        polygon1.addPoint(x, y - 10);
        polygon1.addPoint(x + 10, y);
        polygon1.addPoint(x, y + 10);
        polygon1.addPoint(x - 10, y);
        g_2d.setStroke(new BasicStroke(0f));

        g_2d.setColor(new Color(0, 0, 0, (int)(150*tempRatio)));
        g_2d.fillPolygon(polygon1);
        g_2d.draw(polygon1);
        if (this.type==1){
            g_2d.setStroke(new BasicStroke(3.0f, CAP_BUTT, JOIN_BEVEL));
            g_2d.setColor(new Color(36, 123, 160, 100));
            Rectangle2D rect = new Rectangle2D.Double(x-25, (int)((double)y/PlayInterface.finalY*0.85),50 , 50);
            g_2d.draw(rect);
            g_2d.fill(rect);
            Font f=new Font(null,Font.BOLD,40);
            g_2d.setFont(f);
            g_2d.setColor(new Color(255, 255, 255, 200));
            g_2d.drawString(String.valueOf(this.key),x-10,(int)((double)y/PlayInterface.finalY*0.85+35));
        }
        for(int i=frontNote;i<=rearNote;i++){
            this.notes.get(i).moveNote();
            getImg(g,this.notes.get(i));
        }
        if(displayState!=99){
            g_2d.drawImage(currentJudgement,(int)(this.positionX*Data.WIDTH)-55,positionY[displayState],null);
        }
    }

    /**
     * Judge the timing;
     */
    public void Judge() {
        if(this.frontNote<this.notes.size()) {

            this.currentKey = this.notes.get(frontNote).key;

            if(this.currentKey>='a'&&this.currentKey<='z') this.currentKey-=32;
            Note note=this.notes.get(frontNote);
            if(isHolding){
                if (Data.isReleased[this.currentKey].get()==1){
                    if(Math.abs(this.trackCurrentTime -Data.offset-this.delay-note.endTiming)>300){
                        this.displayState=0;
                        this.currentJudgement=judgement[0];
                        this.tempJudge=0;
                        Data.isReleased[this.currentKey].set(0);
                    }
                    else {
                        this.displayState=0;
                        this.currentJudgement=judgement[2];
                    }
                    isHolding=false;
                    Data.isReleased[this.currentKey].set(0);
                }
            }
            else if (Data.isPressed[this.currentKey].get()==1) {
                if (Math.abs(this.trackCurrentTime-Data.offset-this.delay-note.timing)>250){
                    this.tempJudge=-1;
                    Data.isPressed[this.currentKey].set(0);
                    Data.isReleased[this.currentKey].set(0);
                    return;
                }
                else if(Math.abs(this.trackCurrentTime -Data.offset-this.delay-note.timing)>225){
                    this.tempJudge=0;
                }
                else if(Math.abs(this.trackCurrentTime -Data.offset-this.delay-note.timing)>200){
                    this.tempJudge=1;
//                    if(this.notes.get(frontNote).noteType==1)System.out.println("tempFar");
                }
                else {
                    this.tempJudge=2;
//                    if(this.notes.get(frontNote).noteType==1)System.out.println("tempPure");
                }
                if(note.noteType==1&&this.tempJudge>0){
                    this.isHolding=true;
                }
                Data.isReleased[this.currentKey].set(0);
                Data.isPressed[this.currentKey].set(0);
                this.displayState=0;
                this.currentJudgement=judgement[this.tempJudge];
            }

            if(!isHolding&&tempJudge!=-1){
                int score=(PlayInterface.currentNoteCount.incrementAndGet()==PlayInterface.notesCount)?PlayInterface.scoreForLastNote:PlayInterface.scorePerNote;
                switch (tempJudge){
                    case 1:
                        PlayInterface.currentScore.getAndAdd(score/2);
                        PlayInterface.combo.getAndIncrement();
                        PlayInterface.maxCombo.set(Math.max(PlayInterface.combo.get(),PlayInterface.maxCombo.get()));
                        PlayInterface.farCount.getAndIncrement();
//                        if(this.notes.get(frontNote).noteType==1) {
//                            System.out.println("far"+this.notes.get(frontNote).timing+" "+(this.trackCurrentTime-Data.offset-this.delay));
//                        }
//                        else System.out.println("far"+this.notes.get(frontNote).timing+" "+(this.trackCurrentTime-Data.offset-this.delay));
                        break;
                    case 2:
                        PlayInterface.currentScore.getAndAdd(score);
                        PlayInterface.combo.getAndIncrement();
                        PlayInterface.maxCombo.set(Math.max(PlayInterface.combo.get(),PlayInterface.maxCombo.get()));
                        PlayInterface.pureCount.getAndIncrement();
//                        if(this.notes.get(frontNote).noteType==1) {
//                            System.out.println("pure"+this.notes.get(frontNote).timing+" "+(this.trackCurrentTime-Data.offset-this.delay));
//                        }
//                        else System.out.println("pure"+this.notes.get(frontNote).timing+" "+(this.trackCurrentTime-Data.offset-this.delay));
                        break;
                    case 0:
                        PlayInterface.combo.set(0);
                        PlayInterface.lostCount.getAndIncrement();
//                        if(this.notes.get(frontNote).noteType==1) {
//                            System.out.println("lost"+this.notes.get(frontNote).timing+" "+(this.trackCurrentTime-Data.offset-this.delay));
//                        }
//                        else System.out.println("lost"+this.notes.get(frontNote).timing+" "+(this.trackCurrentTime-Data.offset-this.delay));
                        break;
                    default:
                        tempJudge=-1;
                        break;
                }
                tempJudge=-1;
                this.frontNote++;
            }
        }
    }


    /**
     * Responsible for calculating the positionX at each moment, starting from positionX at currentTime, and endX at endTime
     */
    public void moveTrack() {
        double temp1=0,temp2=0,temp3=0;
        if (moveOperations.isEmpty()) {
            return;
        }
        currentMove = moveOperations.get(frontMove);
        if (currentMove.startTime < this.trackCurrentTime) {
            if (currentMove.startTime == currentMove.endTime) this.positionX = currentMove.endX;
            else if (currentMove.endTime - this.trackCurrentTime > 0)
            {
                this.positionX = this.positionX - ((this.positionX - currentMove.endX) / (double) (currentMove.endTime - this.trackCurrentTime)) * (double) (this.trackCurrentTime - this.lastTime);
            }
        }
        if (currentMove.endTime < this.trackCurrentTime && (frontMove + 1) < this.moveOperations.size()) {
            frontMove++;
            this.positionX=currentMove.endX;
        }
    }

    /**
     * Responsible for calculating the width at each moment, starting from width at currentTime, and endWidth at endTime
     */
    public void changeWidth() {
        if (changeWidthOperations.isEmpty()) {
            return;
        }

        currentWidth = changeWidthOperations.get(frontWidth);
        if (currentWidth.startTime < this.trackCurrentTime) {
            if (currentWidth.startTime == currentWidth.endTime) this.width = currentWidth.endWidth;
            else if (currentWidth.endTime - this.trackCurrentTime > 0){
                this.width = this.width - ((this.width - currentWidth.endWidth) / (double) (currentWidth.endTime - this.trackCurrentTime)) * (double) (this.trackCurrentTime - this.lastTime);
            }
        }
        if (currentWidth.endTime < this.trackCurrentTime && (frontWidth + 1) < this.changeWidthOperations.size()) {
            frontWidth++;
            this.width=currentWidth.endWidth;
        }
        if(this.width<0.01) this.width=0.01;
    }

    /**
     * Responsible for calculating the current color at each moment, starting from width at currentTime, and endWidth at endTime
     */
    public void changeColor() {
        if (changeColorOperations.isEmpty()) {
            return;
        }
        currentColor = changeColorOperations.get(frontColor);
        if (currentColor.startTime < this.trackCurrentTime ) {
            if (currentColor.startTime == currentColor.endTime) {
                this.R = currentColor.endR;
                this.G = currentColor.endG;
                this.B = currentColor.endB;
            } else if (currentColor.endTime - this.trackCurrentTime > 0) {
                int r = this.R - (int) (((double) (this.R - currentColor.endR) / (double) (currentColor.endTime - this.trackCurrentTime)) * (double) (this.trackCurrentTime - this.lastTime));
                int g = this.G - (int) (((double) (this.G - currentColor.endG) / (double) (currentColor.endTime - this.trackCurrentTime)) * (double) (this.trackCurrentTime - this.lastTime));
                int b = this.B - (int) (((double) (this.B - currentColor.endB) / (double) (currentColor.endTime - this.trackCurrentTime)) * (double) (this.trackCurrentTime - this.lastTime));
                this.R = (r > 255 || r < 0) ? 160 : r;
                this.G = (g > 255 || g < 0) ? 160 : g;
                this.B = (b > 255 || b < 0) ? 160 : b;
            }
        }
        if (currentColor.endTime < this.trackCurrentTime && (frontColor + 1) < this.changeColorOperations.size())
            frontColor++;
    }

    /**
     * Displays the track and notes on the screen according to the positionX and width and the currentNotes list of the current frame
     * Note: display must be after the move and change operations are finished
     */
    public void run() {
        System.out.println(Thread.activeCount());
        System.out.println("Track"+Thread.currentThread());
        setBounds(0, 0, Data.WIDTH, Data.HEIGHT);
        setLayout(null);
        setOpaque(false);
        this.setVisible(true);
        this.trackCurrentTime = System.currentTimeMillis() - PlayInterface.startTime;
            if(this.notes.isEmpty()){//这个判断不放在while里，这样可以省下判断这条if的时间
                while ((Track.isStopped.get()!=1)&&this.trackCurrentTime < this.endTiming && this.startTiming <= this.trackCurrentTime) {
                    while(isPaused.get()==1&&(Track.isStopped.get()!=1));
                    this.lastTime = this.trackCurrentTime;
                    this.trackCurrentTime = System.currentTimeMillis() - PlayInterface.startTime;
                    this.changeColor();
                    this.changeWidth();
                    this.moveTrack();
                    this.repaint();
                    try{
                        Thread.sleep(8);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
            else{
                while ((Track.isStopped.get()!=1)&&this.trackCurrentTime < this.endTiming && this.startTiming <= this.trackCurrentTime) {
                    while(isPaused.get()==1&&(Track.isStopped.get()!=1));
                    if (displayState<99) displayState++;
                    this.lastTime = this.trackCurrentTime;
                    this.trackCurrentTime = System.currentTimeMillis() - PlayInterface.startTime;
                    Note note;
                    if(frontNote<this.notes.size()&&this.trackCurrentTime+250>(note=this.notes.get(frontNote)).timing){
                        this.Judge();
                    }
                    this.changeColor();
                    this.changeWidth();
                    this.moveTrack();
                    while ((rearNote + 1 < this.notes.size()) && (this.notes.get(rearNote + 1).timing - PlayInterface.remainingTime) < this.trackCurrentTime) {
                        rearNote++;
                    }
                    if(frontNote<this.notes.size()){
                        if (this.trackCurrentTime > (note=this.notes.get(frontNote)).timing + 250+Data.offset+delay&&(note.noteType==0||note.noteType==1&&!this.isHolding)) {
                            PlayInterface.combo.set(0);
                            PlayInterface.lostCount.getAndIncrement();
                            PlayInterface.currentNoteCount.getAndIncrement();
                            this.tempJudge=-1;
//                        System.out.println("type2 lost"+this.notes.get(frontNote).timing+" "+(this.trackCurrentTime-Data.offset-this.delay));
                            frontNote++;
                            this.displayState=0;
                            this.currentJudgement=judgement[0];
                        }
                        else if((note=this.notes.get(frontNote)).noteType==1&&this.isHolding&&this.trackCurrentTime>note.endTiming){
                            int score=(PlayInterface.currentNoteCount.incrementAndGet()==PlayInterface.notesCount)?PlayInterface.scoreForLastNote:PlayInterface.scorePerNote;
                            switch (this.tempJudge){
                                case 2:
                                    PlayInterface.pureCount.getAndIncrement();
                                    PlayInterface.currentScore.getAndAdd(score);
                                    break;
                                case 1:
                                    PlayInterface.farCount.getAndIncrement();
                                    PlayInterface.currentScore.getAndAdd(score/2);
                                    break;
                                default: break;
                            }
                            if(note.key>='a'&&note.key<='z')Data.isReleased[note.key-32].set(0);
                            else Data.isReleased[note.key].set(0);
                            PlayInterface.combo.getAndIncrement();
                            PlayInterface.maxCombo.set(Math.max(PlayInterface.combo.get(),PlayInterface.maxCombo.get()));
                            this.currentJudgement=judgement[tempJudge];
                            this.tempJudge=-1;
                            this.isHolding=false;
                            frontNote++;
                            this.displayState=0;

                        }
                        //if(frontNote<this.notes.size()&&this.notes.get(frontNote).noteType==1) System.out.println(this.isHolding+" "+this.trackCurrentTime+" "+note.endTiming);
                    }
                    this.repaint();
                    try{
                        Thread.sleep(15);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
            this.isEnded=true;
            while(!finalEnd&&(isStopped.get()==0)){
                this.repaint();
            }
            Data.canvas.remove(this);
            System.out.println("Track ended");
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

