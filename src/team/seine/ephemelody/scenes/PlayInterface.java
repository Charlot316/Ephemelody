package team.seine.ephemelody.scenes;

import database.Entity.Record;
import database.RecordController;
import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.playinterface.*;
import team.seine.ephemelody.utils.Load;

import javax.sound.sampled.Clip;
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
import static java.lang.Thread.sleep;

public class PlayInterface extends JPanel implements Scenes, Runnable, KeyListener {//Set up the play interface
    int songID;
    int difficulty;
    int trackCount;
    public static int notesCount;
    int operationsCount;
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
    public int score=0;
    public static Hashtable<Integer, Track> currentTracks = new Hashtable<>();
    public ArrayList<Track> allTracks = new ArrayList<>();
    ArrayList<PlayOperations> backgroundOperations = new ArrayList<>();
    ArrayList<Image> backgroundImg = new ArrayList<>();
    int frontTrack = 0;
    int frontOperation = 0;
    int frontBackground = 0;
    public String Path;
    public ScoreAndComboDisplay displayer = new ScoreAndComboDisplay();
    public Clip song;
    public double prevPotential;
    public double nowPotential;
    /**
     * read in information of the display
     */
    public void paint(Graphics g) {
        Graphics2D g_2d = (Graphics2D) g;
        g.drawImage(backgroundImg.get(frontBackground), 0, 0, Data.WIDTH,Data.HEIGHT,null);
        g_2d.setStroke(new BasicStroke(3.0f,CAP_BUTT, JOIN_BEVEL));
        g_2d.setColor(new Color(255,255,255));
        g_2d.drawLine(0,(int)(PlayInterface.finalY*Data.HEIGHT),Data.WIDTH,(int)(PlayInterface.finalY*Data.HEIGHT));
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
        this.Path = this.songID + "/";
        String displayPath = this.Path + this.difficulty + ".txt";
        BufferedReader bufferedReader = Load.File(displayPath);
        try {
            String command = bufferedReader.readLine();
            String[] arguments = command.split("\\s+");
            this.trackCount = Integer.parseInt(arguments[0]);
            PlayInterface.notesCount = Integer.parseInt(arguments[1]);
            this.operationsCount = Integer.parseInt(arguments[2]);
            this.backgroundImg.add(Load.backgroundImage(this.Path + arguments[3]));
            if (PlayInterface.notesCount != 0) {
                PlayInterface.scorePerNote = (10000000 / PlayInterface.notesCount);
                PlayInterface.scoreForLastNote =(PlayInterface.notesCount * PlayInterface.scorePerNote==10000000)? PlayInterface.scorePerNote:(10000000 - PlayInterface.notesCount * PlayInterface.scorePerNote+PlayInterface.scorePerNote);
            }
            for (int i = 0; i < this.trackCount; i++) {
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
                this.allTracks.add(track);
            }

            for (int i = 0; i < PlayInterface.notesCount; i++) {
                command = bufferedReader.readLine();
                arguments = command.split("\\s+");
                if(arguments.length<4) {i--;continue;}
                int trackID = Integer.parseInt(arguments[0]);
                int noteType = Integer.parseInt(arguments[1]);
                char key = arguments[2].charAt(0);
                long timing = Long.parseLong(arguments[3]);
                PlayInterface.finalEndTime = Math.max(PlayInterface.finalEndTime, timing+1000);
                Track track = getTrackByID(trackID);
                if(track!=null){
                    if (noteType==1) {
                        long endTiming = Long.parseLong(arguments[4]);
                        Note note = new Note(track, noteType, key, timing, endTiming);
                        track.notes.add(note);
                        PlayInterface.finalEndTime = Math.max(PlayInterface.finalEndTime, endTiming+1000);
                        track.startTiming=Math.min(track.startTiming,note.timing-PlayInterface.remainingTime-500);
                        track.endTiming=Math.max(track.endTiming,note.endTiming+500);
                    } else {
                        Note note = new Note(track, noteType, key, timing);
                        track.notes.add(note);
                        track.startTiming=Math.min(track.startTiming,note.timing-PlayInterface.remainingTime);
                        track.endTiming=Math.max(track.endTiming,note.timing+500);
                        PlayInterface.finalEndTime = Math.max(PlayInterface.finalEndTime, timing+1000);
                    }
                }
            }

            for (int i = 0; i < this.operationsCount; i++) {
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
                            this.backgroundOperations.add(operation);
                            this.backgroundImg.add(Load.backgroundImage(this.Path + tempBackground));
                            break;
                        default:
                            break;
                    }
                }
            }
            this.allTracks.sort(comparatorTrack);
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

    /**
     * Initialize PlayInterface and run the game
     *
     * @param songID     ID of the song
     * @param difficulty difficulty of the song (both are used to find the source file)
     */
    public PlayInterface(int songID, int difficulty) {
        PlayInterface.remainingTime=(long)(-600*Data.noteSpeed+4100);
        pureCount.set(0);
        farCount.set(0);
        lostCount.set(0);
        combo.set(0);
        currentScore.set(0);
        currentNoteCount.set(0);
        this.songID = songID;
        this.difficulty = difficulty;
        this.loadData();
        this.setInterface();
        this.repaint();
        this.requestFocus();
        this.prevPotential = RecordController.setAndGetPersonPotential(Data.nowPlayer.getPlayerID());
//        System.out.println(prevPotential);
        this.song = Load.sound(String.valueOf(songID));
        assert song != null;
        song.start(); // 播放音乐
        addKeyListener( new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                onKeyDown(e.getKeyCode());
                repaint();
            }
            public void KeyReleased(KeyEvent e) {
                onKeyUp(e.getKeyCode());
                repaint();
            }
        });
    }

    /**
     * Stop all the operations and pop up the menu
     */
    public void pause() throws InterruptedException {
        for (Map.Entry<Integer, Track> entry : currentTracks.entrySet()) {
            entry.wait();
        }
    }

    /**
     * Resume the game
     */
    public void resumeGame() {
        for (Map.Entry<Integer, Track> entry : currentTracks.entrySet()) {
            entry.notify();
        }
    }

    /**
     * run the game
     */
    public void run() {
        startTime = System.currentTimeMillis();
        currentTime = 0;
        this.backgroundOperations.sort(comparatorOperation);
        this.repaint();
        while (currentTime < PlayInterface.finalEndTime) {
            currentTime = System.currentTimeMillis() - startTime;
            //System.out.println(currentTime+" "+this.finalEndTime);
            while (frontTrack<allTracks.size()&&allTracks.get(frontTrack).startTiming < currentTime) {
                currentTime = System.currentTimeMillis() - startTime;
                allTracks.get(frontTrack).notes.sort(comparatorNote);
                allTracks.get(frontTrack).moveOperations.sort(comparatorOperation);
                allTracks.get(frontTrack).changeWidthOperations.sort(comparatorOperation);
                allTracks.get(frontTrack).changeColorOperations.sort(comparatorOperation);
                new Thread(allTracks.get(frontTrack)).start();
                currentTracks.put(allTracks.get(frontTrack).id,allTracks.get(frontTrack));
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
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.finish();
    }

    /**
     * Finish the play and go to the next interface
     */
    public void finish() {
        long time = System.currentTimeMillis();
        Record record = new Record(Data.nowPlayer.getPlayerID(), new Timestamp(time), Data.songId, Data.difficulty,
                pureCount.get(), farCount.get(), lostCount.get(), maxCombo.get(),
                RecordController.calculatePotential(Data.songId, Data.difficulty, new AtomicInteger(score)), score);
//        System.out.println(nowPotential + "------" + prevPotential);
        System.out.println(record.toString());
        RecordController.insertAllBestRecord(record);
        RecordController.insertRecentRecord(record);
        RecordController.insertBestRecord(record);
        this.nowPotential = RecordController.setAndGetPersonPotential(Data.nowPlayer.getPlayerID());
        Data.canvas.switchScenes("End", new RecordTemp(score, pureCount, farCount, lostCount, maxCombo,
                2, nowPotential - prevPotential, nowPotential));

        song.stop();
    }

    public Track getTrackByID(int id) {
        for(Track i:allTracks){
            if(i.id==id) return i;
        }
        return null;
    }


    @Override
    public void onKeyDown(int keyCode) {
        Data.isPressed[keyCode].set(1);
        //System.out.println(keyCode + " " + Data.keyStatus[keyCode]);
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
        onKeyDown(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        onKeyUp(e.getKeyCode());
    }


}
