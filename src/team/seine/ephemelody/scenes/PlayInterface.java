package team.seine.ephemelody.scenes;

import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.main.Canvas;
import team.seine.ephemelody.playinterface.*;
import team.seine.ephemelody.utils.Load;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayInterface extends JPanel implements Scenes, Runnable{//Set up the play interface
    int songID;
    int difficulty;
    int trackCount;
    int notesCount;
    int operationsCount;
    int scorePerNote;
    int scoreForLastNote;
    public static AtomicInteger pureCount = new AtomicInteger();
    public static AtomicInteger farCount = new AtomicInteger();
    public static AtomicInteger lostCount = new AtomicInteger();
    public static AtomicInteger combo = new AtomicInteger();
    public static AtomicInteger currentNoteCount = new AtomicInteger();
    public static AtomicInteger maxCombo = new AtomicInteger();
    public static AtomicInteger currentScore = new AtomicInteger();
    public static long startTime;
    public static long currentTime;//Used to tell the current time
    public static double finalY;
    public static long remainingTime;
    public long finalEndTime;
    public HashMap<Integer, Track> currentTracks = new HashMap<>();
    public ArrayList<Track> allTracks = new ArrayList<>();
    ArrayList<PlayOperations> backgroundOperations = new ArrayList<>();
    ArrayList<Image> backgroundImg = new ArrayList<>();
    int frontTrack = 0;
    int frontOperation = 0;
    int frontBackground = 0;
    int BackgroundCount = 0;
    public String Path;

    /**
     * read in information of the display
     */
    public void paint(Graphics g) {
        g.drawImage(backgroundImg.get(frontBackground), 0, 0, null);
    }

    public void loadData() {
        this.Path = this.songID + "/";
        String displayPath = this.Path + this.difficulty + ".txt";
        BufferedReader bufferedReader = Load.File(displayPath);
        try {
            String command = bufferedReader.readLine();
            String[] arguments = command.split("\\s+");
            this.trackCount = Integer.parseInt(arguments[0]);
            this.notesCount = Integer.parseInt(arguments[1]);
            this.operationsCount = Integer.parseInt(arguments[2]);
            this.backgroundImg.add(Load.backgroundImage(this.Path + arguments[3]));
            if (this.notesCount != 0) {
                this.scorePerNote = (int) (10000000 / this.notesCount);
                this.scoreForLastNote = 10000000 - this.notesCount * this.scorePerNote;
            }
            for (int i = 0; i < this.trackCount; i++) {
                command = bufferedReader.readLine();
                arguments = command.split("\\s+");
                int id = Integer.parseInt(arguments[0]);
                int type = Integer.parseInt(arguments[1]);
                char key = arguments[2].charAt(0);
                long startTiming = Long.parseLong(arguments[3]);
                long endTiming = Long.parseLong(arguments[4]);
                this.finalEndTime = Math.max(this.finalEndTime, endTiming);
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
                System.out.println(track);
            }

            for (int i = 0; i < this.notesCount; i++) {
                command = bufferedReader.readLine();
                arguments = command.split("\\s+");
                int trackID = Integer.parseInt(arguments[0]);
                int noteType = Integer.parseInt(arguments[1]);
                char key = arguments[2].charAt(0);
                long timing = Long.parseLong(arguments[3]);
                Track track = getTrackByID(trackID);
                if (arguments.length == 5) {
                    long endTiming = Long.parseLong(arguments[4]);
                    Note note = new Note(track, noteType, key, timing, endTiming);
                    track.notes.add(note);
                    this.finalEndTime = Math.max(this.finalEndTime, endTiming);
                } else {
                    Note note = new Note(track, noteType, key, timing);
                    track.notes.add(note);
                    this.finalEndTime = Math.max(this.finalEndTime, timing);
                }
            }

            for (int i = 0; i < this.operationsCount; i++) {
                command = bufferedReader.readLine();
                arguments = command.split("\\s+");
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
        pureCount.set(0);
        farCount.set(0);
        lostCount.set(0);
        combo.set(0);
        currentScore.set(0);
        this.songID = songID;
        this.difficulty = difficulty;
        this.loadData();
        this.setInterface();
        this.repaint();
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
        this.repaint();
        while (currentTime < this.finalEndTime) {
            currentTime = System.currentTimeMillis() - startTime;
            //System.out.println(currentTime+" "+this.finalEndTime);
            if (frontTrack<allTracks.size()&&allTracks.get(frontTrack).startTiming < currentTime) {
                currentTime = System.currentTimeMillis() - startTime;
                new Thread(allTracks.get(frontTrack)).start();
                System.out.println(currentTime+" "+this.finalEndTime);
                System.out.println(allTracks.get(frontTrack).startTiming);
                 frontTrack++;
            }
            if (!backgroundOperations.isEmpty() && backgroundOperations.get(frontOperation).startTime < currentTime) {
                this.frontBackground++;
                this.repaint();
            }
        }
        this.finish();
    }

    /**
     * Finish the play and go to the next interface
     */
    public void finish() {
        Data.canvas.switchScenes("Home");
        System.out.println("嘿");
    }

    public Track getTrackByID(int id) {
        if (id >= allTracks.size() || id < 0) return null;
        else return allTracks.get(id);
    }


    @Override
    public void onKeyDown(int keyCode) {

    }

    @Override
    public void onKeyUp(int keyCode) {

    }

    @Override
    public void onMouse(int x, int y, int struts) {

    }
}
