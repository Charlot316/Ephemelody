package team.seine.ephemelody.playinterface;

import javax.swing.*;
import java.awt.*;
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
    public AtomicInteger perfectCount;
    public AtomicInteger greatCount;
    public AtomicInteger goodCount;
    public AtomicInteger missCount;
    public AtomicInteger combo;
    public AtomicInteger currentScore;
    public static long startTime;
    public static long currentTime;//Used to tell the current time
    public static double finalY;
    public static long remainingTime;
    public long finalEndTime;
    public HashMap<Integer, Track> currentTracks = new HashMap<>();
    public HashMap<Integer, PlayOperations> currentOperations = new HashMap<>();
    public HashMap<Integer, Note> currentNotes = new HashMap<>();
    public ArrayList<PlayOperations> allOperations = new ArrayList<>();
    public ArrayList<Track> allTracks = new ArrayList<>();
    String background = "";
    int frontTrack;
    int frontOperation;
    public JLayeredPane layeredPane;
    public static JTextArea textArea;

    /**
     * read in information of the display
     * display the notes whose timing is less than retention time
     */
    public PlayInterface(int songID, int difficulty) {
        this.songID = songID;
        this.difficulty = difficulty;
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
     * Stop all the operations and pop up the menu
     */
    public void pause() throws InterruptedException {
        for (Map.Entry<Integer, Track> entry : currentTracks.entrySet()) {
            entry.wait();
        }
        for (Map.Entry<Integer, PlayOperations> entry : currentOperations.entrySet()) {
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
        for (Map.Entry<Integer, PlayOperations> entry : currentOperations.entrySet()) {
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

    public Track getTrackByID(int id) {
        for (Track i : this.allTracks) {
            if (id == i.id)
                return i;
        }
        return null;
    }


}
