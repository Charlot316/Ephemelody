package team.seine.ephemelody.playinterface;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayInterface extends JPanel {//Set up the play interface
    int songID;
    int difficulty;
    int trackCount;
    int notesCount;
    int operationsCount;
    int scorePerNote;
    int scoreForLastNote;
    public static AtomicInteger pureCount=new AtomicInteger();
    public static AtomicInteger farCount=new AtomicInteger();
    public static AtomicInteger lostCount=new AtomicInteger();
    public static AtomicInteger combo=new AtomicInteger();
    public int maxCombo;
    public static AtomicInteger currentScore=new AtomicInteger();
    public static long startTime;
    public static long currentTime;//Used to tell the current time
    public static double finalY;
    public static long remainingTime;
    public long finalEndTime;
    public HashMap<Integer, Track> currentTracks = new HashMap<>();
    public ArrayList<Track> allTracks = new ArrayList<>();
    ArrayList<PlayOperations> backgroundOperations = new ArrayList<>();
    public String background = "";
    int frontTrack;
    int frontOperation;
    public JLayeredPane layeredPane;
    public static JTextArea textArea;
    public String Path;

    /**
     * read in information of the display
     */
    public void loadData(){
        this.Path="/resources/display/"+this.songID+"/"+this.difficulty+"/";
        String displayPath=this.Path+"display.txt";
        System.out.println(displayPath);
        InputStream is=this.getClass().getResourceAsStream(displayPath);
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));

        try {
            String command = bufferedReader.readLine();
            String []arguments=command.split("\\s+");
            this.trackCount=Integer.parseInt(arguments[0]);
            this.notesCount=Integer.parseInt(arguments[1]);
            this.operationsCount=Integer.parseInt(arguments[2]);
            this.background=arguments[3];

            for(int i=0;i<this.trackCount;i++){
                command = bufferedReader .readLine();
                arguments=command.split("\\s+");
                int id=Integer.parseInt(arguments[0]);
                int type=Integer.parseInt(arguments[1]);
                char key=arguments[2].charAt(0);
                long startTiming= Long.parseLong(arguments[3]);
                long endTiming=Long.parseLong(arguments[4]);
                this.finalEndTime= Math.max(this.finalEndTime, endTiming);
                double positionX=Double.parseDouble(arguments[5]);
                double width=0.06;
                int R=160;
                int G=160;
                int B=160;
                switch (arguments.length){
                    case 7:
                        width=Double.parseDouble(arguments[6]);
                        break;
                    case 9:
                        R=Integer.parseInt(arguments[6]);
                        G=Integer.parseInt(arguments[7]);
                        B=Integer.parseInt(arguments[8]);
                        break;
                    case 10:
                        width=Double.parseDouble(arguments[6]);
                        R=Integer.parseInt(arguments[7]);
                        G=Integer.parseInt(arguments[8]);
                        B=Integer.parseInt(arguments[9]);
                        break;
                    default:
                        break;
                }
                Track track=new Track(id,type,key,startTiming,endTiming,positionX,width,R,G,B);
                this.allTracks.add(track);
                System.out.println(track);
            }

            for(int i=0;i<this.notesCount;i++){
                command = bufferedReader .readLine();
                arguments=command.split("\\s+");
                int trackID=Integer.parseInt(arguments[0]);
                int noteType=Integer.parseInt(arguments[1]);
                char key=arguments[2].charAt(0);
                long timing=Long.parseLong(arguments[3]);
                Track track=getTrackByID(trackID);
                if(arguments.length==5){
                    long endTiming=Long.parseLong(arguments[4]);
                    Note note=new Note(track,noteType,key,timing,endTiming);
                    track.notes.add(note);
                    this.finalEndTime= Math.max(this.finalEndTime, endTiming);
                }
                else{
                    Note note=new Note(track,noteType,key,timing);
                    track.notes.add(note);
                    this.finalEndTime= Math.max(this.finalEndTime, timing);
                }
            }

            for(int i=0;i<this.operationsCount;i++){
                command = bufferedReader.readLine();
                arguments=command.split("\\s+");
                int trackID=Integer.parseInt(arguments[0]);
                int type=Integer.parseInt(arguments[1]);
                long startTiming=Long.parseLong(arguments[2]);
                long endTiming=startTiming;
                double endX=0;
                double width=0;
                int R=160;
                int G=160;
                int B=160;
                String tempBackground=this.background;
                PlayOperations operation;
                Track track=getTrackByID(trackID);
                switch(type){
                    case 1:
                        endTiming= Long.parseLong(arguments[3]);
                        endX=Double.parseDouble(arguments[4]);
                        operation=new PlayOperations(trackID,type,startTiming,endTiming,endX,width,R,G,B,tempBackground);
                        track.moveOperations.add(operation);
                        break;
                    case 2:
                        endTiming= Long.parseLong(arguments[3]);
                        width=Double.parseDouble(arguments[4]);
                        operation=new PlayOperations(trackID,type,startTiming,endTiming,endX,width,R,G,B,tempBackground);
                        track.changeWidthOperations.add(operation);
                        break;
                    case 3:
                        endTiming= Long.parseLong(arguments[3]);
                        R=Integer.parseInt(arguments[4]);
                        G=Integer.parseInt(arguments[5]);
                        B=Integer.parseInt(arguments[6]);
                        operation=new PlayOperations(trackID,type,startTiming,endTiming,endX,width,R,G,B,tempBackground);
                        track.changeColorOperations.add(operation);
                        break;
                    case 4:
                        tempBackground=arguments[3];
                        operation=new PlayOperations(trackID,type,startTiming,endTiming,endX,width,R,G,B,tempBackground);
                        this.backgroundOperations.add(operation);
                        break;
                    default:
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
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
    public void setInterface(){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        layeredPane = new JLayeredPane();
        layeredPane.setSize(TestPlayInterface.Frame.getWidth(), TestPlayInterface.Frame.getHeight());
        KeyBoardListener keyBoardListener = new KeyBoardListener();
        JTextArea textArea = new JTextArea(9, 30);
        JPanel panel = new JPanel(new GridLayout(1, 1));
        panel.setBounds(30, 30, 100, 100);
        Button button = new Button();
        panel.add(textArea);
        textArea.addKeyListener(keyBoardListener);
        layeredPane.add(panel);
        textArea.append("阿巴阿巴阿巴阿巴阿巴阿巴");
        layeredPane.setVisible(true);
        textArea.setVisible(true);
        add(layeredPane);
    }
    /**
     * Initialize PlayInterface and run the game
     * @param songID ID of the song
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
        this.display();
        this.finish();
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
     * Constantly update currentTracks and run the game
     */
    public void display() {

    }

    /**
     * Finish the play and go to the next interface
     */
    public void finish() {

    }

    public Track getTrackByID(int id){
        if(id>=allTracks.size()||id<0) return null;
        else return allTracks.get(id);
    }


}
