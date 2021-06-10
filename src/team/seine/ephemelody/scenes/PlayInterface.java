package team.seine.ephemelody.scenes;

import database.Entity.Record;
import database.RecordController;
import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.main.Canvas;
import team.seine.ephemelody.playinterface.*;
import team.seine.ephemelody.utils.Load;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.awt.BasicStroke.CAP_BUTT;
import static java.awt.BasicStroke.JOIN_BEVEL;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.lang.Thread.sleep;

public class PlayInterface extends JPanel implements Scenes, Runnable, KeyListener {//Set up the play interface
    public static int songID;
    public static int difficulty;
    public static int trackCount;
    public static int notesCount;
    public static int operationsCount;
    public static int scorePerNote;
    public static int scoreForLastNote;
    public static AtomicInteger pureCount = new AtomicInteger();
    public static AtomicInteger farCount = new AtomicInteger();
    public static AtomicInteger lostCount = new AtomicInteger();
    public static AtomicInteger combo = new AtomicInteger();
    public static AtomicInteger currentNoteCount = new AtomicInteger();
    public static AtomicInteger maxCombo = new AtomicInteger();
    public static AtomicInteger currentScore = new AtomicInteger();
    public static long startTime;
    public static long currentTime;//Used to tell the current time
    public static double finalY=0.8;
    public static long remainingTime;
    public static long finalEndTime;
    public static long pauseTime;
    public static long resumeTime;

    static int clipTime;
    public static int score=0;
    public static ArrayList<Track> allTracks = new ArrayList<>();
    public static ArrayList<PlayOperations> backgroundOperations = new ArrayList<>();
    public static ArrayList<Image> backgroundImg = new ArrayList<>();
    public static int frontTrack = 0;
    public static int frontOperation = 0;
    public static int frontBackground = 0;
    public static String Path;
    public static ScoreAndComboDisplay displayer = new ScoreAndComboDisplay();
    public static Clip song;
    public static double prevPotential;
    public static double nowPotential;
    public static boolean isPaused=false;
    public static boolean isStop=false;
    private static PlayInterface playinterface=new PlayInterface();
    /**
     * read in information of the display
     */
    public void paint(Graphics g) {
        Graphics2D g_2d = (Graphics2D) g;
        g.drawImage(backgroundImg.get(frontBackground), 0, 0, Data.WIDTH,Data.HEIGHT,null);
        g_2d.setStroke(new BasicStroke(3.0f,CAP_BUTT, JOIN_BEVEL));
        g_2d.setColor(new Color(255,255,255));
        g_2d.drawLine(0,(int)(PlayInterface.finalY* Data.HEIGHT),Data.WIDTH,(int)(PlayInterface.finalY*Data.HEIGHT));
    }
    static Comparator<Note> comparatorNote = Comparator.comparingLong(o -> o.timing);
    static Comparator<Track> comparatorTrack= Comparator.comparingLong(o -> o.startTiming);
    static Comparator<PlayOperations> comparatorOperation= (o1, o2) -> Long.compare(o1.startTime,o2.endTime);
    public void loadData() {
        for(int i=0;i<200;i++){
            Data.isPressed[i]=new AtomicInteger();
            Data.isReleased[i]=new AtomicInteger();
            Data.isUsing[i]=new AtomicInteger();
            Data.isPressed[i].set(0);
            Data.isReleased[i].set(0);
            Data.isUsing[i].set(0);
        }
        PlayInterface.Path = PlayInterface.songID + "/";
        String displayPath = PlayInterface.Path + PlayInterface.difficulty + ".txt";
        BufferedReader bufferedReader = Load.File(displayPath);
        try {
            String command = bufferedReader.readLine();
            String[] arguments = command.split("\\s+");
            PlayInterface.trackCount = Integer.parseInt(arguments[0]);
            PlayInterface.notesCount = Integer.parseInt(arguments[1]);
            PlayInterface.operationsCount = Integer.parseInt(arguments[2]);
            PlayInterface.backgroundImg.add(Load.backgroundImage(PlayInterface.Path + arguments[3]));
            if (PlayInterface.notesCount != 0) {
                PlayInterface.scorePerNote = (10000000 / PlayInterface.notesCount);
                PlayInterface.scoreForLastNote =(PlayInterface.notesCount * PlayInterface.scorePerNote==10000000)? PlayInterface.scorePerNote:(10000000 - PlayInterface.notesCount * PlayInterface.scorePerNote+PlayInterface.scorePerNote);
            }
            for (int i = 0; i < PlayInterface.trackCount; i++) {
                command = bufferedReader.readLine();
                arguments = command.split("\\s+");
                if(arguments.length<6) {i--;continue;}
                int id = Integer.parseInt(arguments[0]);
                int type = Integer.parseInt(arguments[1]);
                char key = arguments[2].charAt(0);
                long startTiming = Long.parseLong(arguments[3]);
                long endTiming = Long.parseLong(arguments[4]);
                PlayInterface.finalEndTime = Math.max(PlayInterface.finalEndTime, endTiming+1000);
                double positionX = Double.parseDouble(arguments[5]);
                double width = 0.06;
                int R = 160;
                int G = 160;
                int B = 160;
                switch (arguments.length) {
                    case 7:
                        width = Double.parseDouble(arguments[6]);
                        break;
                    case 9:
                        R = Integer.parseInt(arguments[6]);
                        G = Integer.parseInt(arguments[7]);
                        B = Integer.parseInt(arguments[8]);
                        break;
                    case 10:
                        width = Double.parseDouble(arguments[6]);
                        R = Integer.parseInt(arguments[7]);
                        G = Integer.parseInt(arguments[8]);
                        B = Integer.parseInt(arguments[9]);
                        break;
                    default:
                        break;
                }

                Track track = new Track(id, type, key, startTiming, endTiming, positionX, width, R, G, B);
                PlayInterface.allTracks.add(track);
            }

            for (int i = 0; i < PlayInterface.notesCount; i++) {
                command = bufferedReader.readLine();
                arguments = command.split("\\s+");
                if(arguments.length<4) {i--;continue;}
                int trackID = Integer.parseInt(arguments[0]);
                int noteType = Integer.parseInt(arguments[1]);
                char key = arguments[2].charAt(0);
                long timing = Long.parseLong(arguments[3]);
                PlayInterface.finalEndTime = Math.max(PlayInterface.finalEndTime, timing+2500);
                Track track = getTrackByID(trackID);
                if(track!=null){
                    if (noteType==1) {
                        long endTiming = Long.parseLong(arguments[4]);
                        Note note = new Note(track, noteType, key, timing, endTiming);
                        track.notes.add(note);
                        PlayInterface.finalEndTime = Math.max(PlayInterface.finalEndTime, endTiming+2500);
                        track.startTiming=Math.min(track.startTiming,note.timing-PlayInterface.remainingTime-500);
                        track.endTiming=Math.max(track.endTiming,note.endTiming+500);
                    } else {
                        Note note = new Note(track, noteType, key, timing);
                        track.notes.add(note);
                        track.startTiming=Math.min(track.startTiming,note.timing-PlayInterface.remainingTime);
                        track.endTiming=Math.max(track.endTiming,note.timing+500);
                        PlayInterface.finalEndTime = Math.max(PlayInterface.finalEndTime, timing+2500);
                    }
                }
            }

            for (int i = 0; i < PlayInterface.operationsCount; i++) {
                command = bufferedReader.readLine();
                arguments = command.split("\\s+");
                if(arguments.length<4) {i--;continue;}
                int trackID = Integer.parseInt(arguments[0]);
                int type = Integer.parseInt(arguments[1]);
                long startTiming = Long.parseLong(arguments[2]);
                long endTiming = startTiming;
                double endX = 0;
                double width = 0;
                int R = 160;
                int G = 160;
                int B = 160;
                String tempBackground = "";
                PlayOperations operation;
                Track track = getTrackByID(trackID);
                if(track!=null){
                    switch (type) {
                        case 1:
                            endTiming = Long.parseLong(arguments[3]);
                            endX = Double.parseDouble(arguments[4]);
                            operation = new PlayOperations(trackID, type, startTiming, endTiming, endX, width, R, G, B, tempBackground);
                            track.moveOperations.add(operation);
                            break;
                        case 2:
                            endTiming = Long.parseLong(arguments[3]);
                            width = Double.parseDouble(arguments[4]);
                            operation = new PlayOperations(trackID, type, startTiming, endTiming, endX, width, R, G, B, tempBackground);
                            track.changeWidthOperations.add(operation);
                            break;
                        case 3:
                            endTiming = Long.parseLong(arguments[3]);
                            R = Integer.parseInt(arguments[4]);
                            G = Integer.parseInt(arguments[5]);
                            B = Integer.parseInt(arguments[6]);
                            operation = new PlayOperations(trackID, type, startTiming, endTiming, endX, width, R, G, B, tempBackground);
                            track.changeColorOperations.add(operation);
                            break;
                        case 4:
                            tempBackground = arguments[3];
                            operation = new PlayOperations(trackID, type, startTiming, endTiming, endX, width, R, G, B, tempBackground);
                            PlayInterface.backgroundOperations.add(operation);
                            PlayInterface.backgroundImg.add(Load.backgroundImage(PlayInterface.Path + tempBackground));
                            break;
                        default:
                            break;
                    }
                }
            }
            PlayInterface.allTracks.sort(comparatorTrack);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Set up the interface of the play
     */
    public void setInterface() {
        setBounds(0, 0, Data.WIDTH, Data.HEIGHT);
        this.setVisible(true);
        setLayout(null);
    }

    private PlayInterface(){

    }
    /**
     * Initialize PlayInterface and run the game
     *
     * @param songID     ID of the song
     * @param difficulty difficulty of the song (both are used to find the source file)
     */
    public static PlayInterface getPlayInterface(int songID, int difficulty) {
        System.out.println(Thread.activeCount());
        System.out.println(Thread.currentThread());
        isPaused=false;
        isStop=false;
        Track.isPaused.set(0);
        Track.isStopped.set(0);
        frontTrack=0;
        frontOperation=0;
        PlayInterface.remainingTime=(long)(-600*Data.noteSpeed+4100);
        pureCount.set(0);
        farCount.set(0);
        lostCount.set(0);
        combo.set(0);
        currentScore.set(0);
        currentNoteCount.set(0);
        PlayInterface.songID = songID;
        PlayInterface.difficulty = difficulty;
        finalEndTime=0;
        PlayInterface.song = Load.sound(String.valueOf(songID));
        FloatControl gainControl = (FloatControl) song.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-(float)(8*(10-Data.volume))); // Reduce volume by 10 decibels.
        playinterface.loadData();
        playinterface.setInterface();
        playinterface.repaint();
        playinterface.requestFocus();
        if(Data.nowPlayer!=null)PlayInterface.prevPotential = RecordController.setAndGetPersonPotential(Data.nowPlayer.getPlayerID());
//        System.out.println(prevPotential);

        playinterface.addKeyListener( new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                try {
                    playinterface.onKeyDown(e.getKeyCode());
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                playinterface.repaint();
            }
            public void KeyReleased(KeyEvent e) {
                playinterface.onKeyUp(e.getKeyCode());
                playinterface.repaint();
            }
        });
        return playinterface;
    }

    /**
     * Stop all the operations and pop up the menu
     */
    synchronized public void pause() throws InterruptedException {
        clipTime=song.getFramePosition();
        song.stop();
        isPaused=true;
        Track.isPaused.set(1);
        pauseTime=System.currentTimeMillis();
    }

    /**
     * Resume the game
     */
    synchronized public void resumeGame() {
        resumeTime=System.currentTimeMillis();
        startTime+=(resumeTime-pauseTime);
        song.setFramePosition(clipTime);
        song.start();
        Track.isPaused.set(0);
        isPaused=false;
    }

    public void stopGame() {
        Track.isStopped.set(1);
        isStop=true;
        isPaused=false;
        System.out.println(isStop);
        song.stop();
        this.run();
    }
    /**
     * run the game
     */
    public void run() {
        System.out.println(Thread.activeCount());
        System.out.println(Thread.currentThread());
        assert song != null;
        song.start(); // 播放音乐
        startTime = System.currentTimeMillis();
        currentTime = 0;
        this.backgroundOperations.sort(comparatorOperation);
        this.repaint();
        while (currentTime < PlayInterface.finalEndTime&&!isStop) {
            //System.out.println(currentTime+" "+this.finalEndTime);
            while(isPaused);
            if(isStop) break;
            currentTime = System.currentTimeMillis() - startTime;
            while (frontTrack<allTracks.size()&&allTracks.get(frontTrack).startTiming < currentTime) {
                currentTime = System.currentTimeMillis() - startTime;
                allTracks.get(frontTrack).notes.sort(comparatorNote);
                allTracks.get(frontTrack).moveOperations.sort(comparatorOperation);
                allTracks.get(frontTrack).changeWidthOperations.sort(comparatorOperation);
                allTracks.get(frontTrack).changeColorOperations.sort(comparatorOperation);
                new Thread(allTracks.get(frontTrack)).start();
                 frontTrack++;
            }
            if (!backgroundOperations.isEmpty() && frontOperation<backgroundOperations.size()&& backgroundOperations.get(frontOperation).startTime < currentTime) {
                currentTime = System.currentTimeMillis() - startTime;
                //System.out.println(currentTime+" "+this.finalEndTime);
                this.frontBackground++;
                this.frontOperation++;
                this.repaint();
            }
            if (score+8769<PlayInterface.currentScore.get()) score+=8769;
            else score=PlayInterface.currentScore.get();
            displayer.combo=PlayInterface.combo.get();
            displayer.score=score;
            displayer.repaint();
            try{
                Thread.sleep(8);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        song.stop();
        if(isStop) {
            PlayInterface.backgroundImg.clear();
            PlayInterface.allTracks.clear();
            PlayInterface.backgroundOperations.clear();
            Data.canvas.switchScenes("Home");
            Thread.interrupted();
        }
        if(!isStop)this.finish();
    }

    /**
     * Finish the play and go to the next interface
     */
    public void finish() {
        PlayInterface.backgroundImg.clear();
        PlayInterface.allTracks.clear();
        PlayInterface.backgroundOperations.clear();
        long time = System.currentTimeMillis();

        if(Data.nowPlayer!=null){
            Record record = new Record(Data.nowPlayer.getPlayerID(), new Timestamp(time), Data.songId, Data.difficulty,
                    pureCount.get(), farCount.get(), lostCount.get(), maxCombo.get(),
                    RecordController.calculatePotential(Data.songId, Data.difficulty, new AtomicInteger(score)), score);
            //System.out.println(record.toString());
            RecordController.insertAllBestRecord(record);
            RecordController.insertRecentRecord(record);
            RecordController.insertBestRecord(record);
            this.nowPotential = RecordController.setAndGetPersonPotential(Data.nowPlayer.getPlayerID());
            Data.canvas.switchScenes("End", new RecordTemp(currentScore.get(), pureCount, farCount, lostCount, maxCombo,
                    2, nowPotential - prevPotential, nowPotential));
        }
        else{
            Record record = new Record("null", new Timestamp(time), Data.songId, Data.difficulty,
                    pureCount.get(), farCount.get(), lostCount.get(), maxCombo.get(),
                    0, score);
            //System.out.println(record.toString());
            Data.canvas.switchScenes("End", new RecordTemp(currentScore.get(), pureCount, farCount, lostCount, maxCombo,
                    2, 0, 0));
        }
//        System.out.println(nowPotential + "------" + prevPotential);
        song.stop();
        Thread.interrupted();
    }

    public Track getTrackByID(int id) {
        for(Track i:allTracks){
            if(i.id==id) return i;
        }
        return null;
    }


    @Override
    public void onKeyDown(int keyCode) throws InterruptedException {
        Data.isPressed[keyCode].set(1);
        //System.out.println(keyCode + " " + Data.keyStatus[keyCode]);
        if(isPaused){
            if(keyCode==VK_ESCAPE){
                stopGame();
            }
            if(keyCode=='C'){
                resumeGame();
            }
        }
        else{
            if(keyCode=='P'){
                pause();
            }
        }
    }

    @Override
    public void onKeyUp(int keyCode) {
        AtomicInteger tmp = new AtomicInteger();
        tmp.set(0);
        Data.isReleased[keyCode].set(1);
        //System.out.println(keyCode + " " + Data.keyStatus[keyCode]);
    }

    @Override
    public void onMouse(int x, int y, int struts) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {
            onKeyDown(e.getKeyCode());
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        onKeyUp(e.getKeyCode());
    }


}
