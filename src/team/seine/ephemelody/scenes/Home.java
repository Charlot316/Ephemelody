package team.seine.ephemelody.scenes;

import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.main.Canvas;
import team.seine.ephemelody.utils.Load;
import team.seine.ephemelody.utils.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Home extends JPanel implements Scenes, MouseMotionListener, MouseListener{
    int buttonRatingStatus = 0, buttonSetUpBackStatus = 0, buttonHomeStatus = 0, buttonEasyStatus = 0, buttonNormalStatus = 0, buttonDifficultStatus = 0;
    public Image ratingButton[];
    public Image setupButton;
    public Image setupBackButton[];
    public Image playButton;
    public Image homeButton[];
    public Image easyButton[];
    public Image normalButton[];
    public Image difficultButton[];
    public Image upButton;
    public Image downButton;
    public Image song1;
    public Image selectedImg;
    public Home() {
        setBounds(0, 0, Data.WIDTH, Data.HEIGHT);
        setLayout(null);
        ratingButton = new Image[]{
                Load.image("home/潜力值_0.png"), Load.image("home/潜力值_1.png"),
                Load.image("home/潜力值_2.png"), Load.image("home/潜力值_3.png"),
                Load.image("home/潜力值_4.png"), Load.image("home/潜力值_5.png")
        };
        setupButton = Load.image("home/设置.png");
        setupBackButton = new Image[]{
                Load.image("home/设置_未选中.png"), Load.image("home/设置_鼠标悬停.png"), Load.image("home/设置_按下.png")
        };
        playButton = Load.image("home/start.png");
        homeButton = new Image[] {
                Load.image("home/主页.png"), Load.image("home/主页_按下.png")
        };
        easyButton = new Image[] {
                Load.image("home/简单.png"), Load.image("home/简单_鼠标悬停.png"), Load.image("home/简单_按下.png")
        };
        normalButton = new Image[] {
                Load.image("home/普通.png"), Load.image("home/普通_鼠标悬停.png"), Load.image("home/普通_按下.png")
        };
        difficultButton = new Image[] {
                Load.image("home/困难.png"), Load.image("home//困难_鼠标悬停.png"), Load.image("home/困难_按下.png")
        };
        upButton = Load.image("home/up.png");
        downButton = Load.image("home/down.png");
        song1 = Load.image("home/song1.png");
        selectedImg = Load.image("home/被选中的.png");
        setBackground(null);
        setOpaque(false);
        new Home.UpdateUI().start();
        addMouseMotionListener(this);
        addMouseListener(this);

    }

    @Override
    public void onKeyDown(int keyCode) {

    }

    @Override
    public void onKeyUp(int keyCode) {

    }

    public void onMouse(int x, int y, int struts) {
        System.out.println(x + " " + y);
        buttonRatingStatus = buttonSetUpBackStatus = buttonHomeStatus = buttonEasyStatus = buttonNormalStatus = buttonDifficultStatus = 0;

        int buttonStruts = struts == Scenes.MOUSE_MOVED ? 1 : struts == Scenes.MOUSE_DOWN ? 2 : 0;
        // e.getY() 获取的坐标包含了 窗口标题栏的高度，判断点击位置时，需要减去，如果监听鼠标事件时，监听对象为JPanel，则不需要此步骤,, 本程序监听的是JFrame对象
        // 275 + Data.TITLE_BOX_HEIGHT, 这里因为以上原因，在画面上看到的位置，还需要加一个标题栏高度
        // 这里判断的就是 鼠标点击的位置，是否在相应的按钮上方
        if(Rect.isInternal(x, y, 900, 0, 98, 50)) {
            buttonSetUpBackStatus = buttonStruts;
            if(struts == Scenes.MOUSE_DOWN) {
//                Data.canvas.switchScenes("Game");
            }
        }else if(Rect.isInternal(x, y, 503, 580, 202, 125)) {
            buttonEasyStatus = buttonStruts;
            if(struts == Scenes.MOUSE_DOWN) {
//                Data.canvas.switchScenes("About");
            }
        }else if(Rect.isInternal(x, y, 783, 580, 202, 125)) {
            buttonNormalStatus = buttonStruts;
            if(struts == Scenes.MOUSE_DOWN) {
//                Data.canvas.switchScenes("Site");
            }
        }else if(Rect.isInternal(x, y, 1063, 580, 202, 125)) {
            buttonDifficultStatus = buttonStruts;
            if(struts == Scenes.MOUSE_DOWN) {
//                Data.canvas.switchScenes("Recording");
            }
        }else if(Rect.isInternal(x, y, 1150, -7, 131, 69)) {
            buttonHomeStatus = buttonStruts;
            if(struts == Scenes.MOUSE_DOWN) {
//                System.exit(0);
            }
        }
    }
    public void paint(Graphics g) {
        g.drawImage(song1, Data.WIDTH / 2, 100, null);
        g.drawImage(easyButton[buttonEasyStatus], Data.WIDTH / 2 - 140, 580, null);
        g.drawImage(normalButton[buttonNormalStatus], Data.WIDTH / 2 + 140, 580, null);
        g.drawImage(difficultButton[buttonDifficultStatus], Data.WIDTH / 2 + 420, 580, null);
        g.drawImage(playButton, Data.WIDTH / 2, 750, null);
        g.drawImage(upButton, 100, 60, null);
        g.drawImage(downButton, 100, 800, null);
        g.drawImage(ratingButton[buttonRatingStatus], Data.WIDTH / 2 - 60, -16, null);
        g.drawImage(setupBackButton[buttonSetUpBackStatus], 900, 0, null);
        g.drawImage(setupButton, 934, 8, null);
        g.drawImage(homeButton[buttonHomeStatus], 1150, -7, null);
        g.drawImage(selectedImg, 0, Data.HEIGHT / 2 - 30, null);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        onMouse(e.getX(), e.getY(), Scenes.MOUSE_MOVED);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        onMouse(e.getX(), e.getY(), Scenes.MOUSE_DOWN);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    class UpdateUI extends Thread {
        public void run() {
            int sleepTime = 1000 / Data.FPS;
            while (true) {
                try {
                    updateUI();
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
