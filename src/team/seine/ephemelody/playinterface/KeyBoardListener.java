package team.seine.ephemelody.playinterface;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardListener extends Thread implements KeyListener {//Used to listen the status of every key.
    boolean[] keyStatus = new boolean[300];
    boolean[] lastStatus = new boolean[300];
    boolean[] currentStatus = new boolean[300];

    public void run() {
        while (true) {
            PlayInterface.textArea.append("阿巴阿巴阿巴阿巴阿巴阿巴\n");
            for (int i = 65; i <= 90; i++) {
                lastStatus[i] = currentStatus[i];
                currentStatus[i] = keyStatus[i];
                if (!lastStatus[i] && lastStatus[i]) {
                    PlayInterface.textArea.append(i + " is pressed\n");
                }
                if (lastStatus[i] && !lastStatus[i]) {
                    PlayInterface.textArea.append(i + " is unpressed\n");
                }
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyStatus[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyStatus[e.getKeyCode()] = false;
    }
}
