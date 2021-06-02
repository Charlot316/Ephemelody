package team.seine.ephemelody.main;

import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.scenes.Background;
import team.seine.ephemelody.scenes.Home;
import team.seine.ephemelody.scenes.Scenes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

// 画布类
public class Canvas extends JLayeredPane implements MouseMotionListener {
    Scenes nowScenes = null;
    Background background = null;
    Home home = null;
    public Canvas() {
        nowScenes = new Home();
        background = new Background();
        home = new Home();
        add(background, new Integer(0));
        add(home, new Integer(1));
        setVisible(true);
        /*addMouseMotionListener(this);
        new Canvas.UpdateUI().start();*/
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        nowScenes.onMouse(e.getX(), e.getY(), Scenes.MOUSE_MOVED);
    }
//    class UpdateUI extends Thread {
//        public void run() {
//            int sleepTime = 1000 / Data.FPS;
//            while (true) {
//                try {
//                    updateUI();
//                    Thread.sleep(sleepTime);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

}

