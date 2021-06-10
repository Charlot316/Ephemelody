package team.seine.ephemelody.scenes;

import database.PlayerController;
import javafx.scene.media.AudioClip;
import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.playinterface.RecordTemp;
import team.seine.ephemelody.utils.Load;
import team.seine.ephemelody.utils.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Home extends JPanel implements Scenes, MouseMotionListener, MouseListener, KeyListener {
    int buttonRatingStatus = 0, buttonSetUpBackStatus = 0, buttonLoginStatus = 0, buttonEasyStatus = 0, buttonNormalStatus = 0, buttonDifficultStatus = 0,
            buttonPlayStatus = 0, buttonSongInfStatus = 0, buttonUpStatus = 0, buttonDownStatus = 0;
    public Image[] playButton;
    public Image[] easyButton;
    public Image[] normalButton;
    public Image[] difficultButton;
    public Image[] upButton;
    public Image[] downButton;
    //    public Image song1;
    public Image selectedImg;
    public Image nowSongImg;
    public Image[] songInfButton;
    public Image songNameImg;
    public Image hitSongImg;
    public Boolean playFlag;
    public ChooseSong chooseSong;
    public AudioClip audioClip;
    public static boolean isEnd;

    /**
     * Home构造函数
     */
    public Home() {
        Home.isEnd = false;
        setBounds(0, 0, Data.WIDTH, Data.HEIGHT);
        setLayout(null);
        if (Data.isFirstLogin) {
            Data.checkLogin();
        }

        chooseSong = new ChooseSong();
        playFlag = false;
        playButton = new Image[]{
                Load.image("home/开始游戏.png"), Load.image("home/开始游戏_鼠标悬停.png"), Load.image("home/开始游戏_按下.png")
        };
        easyButton = new Image[]{
                Load.image("home/简单.png"), Load.image("home/简单_鼠标悬停.png"), Load.image("home/简单_按下.png")
        };
        normalButton = new Image[]{
                Load.image("home/普通.png"), Load.image("home/普通_鼠标悬停.png"), Load.image("home/普通_按下.png")
        };
        difficultButton = new Image[]{
                Load.image("home/困难.png"), Load.image("home/困难_鼠标悬停.png"), Load.image("home/困难_按下.png")
        };
        songInfButton = new Image[]{
                Load.image("home/歌曲详情.png"), Load.image("home/歌曲详情_鼠标悬停.png"), Load.image("home/歌曲详情_按下.png")
        };
        upButton = new Image[]{
                Load.image("home/up.png"), Load.image("home/up_鼠标悬停.png"), Load.image("home/up_按下.png")
        };
        downButton = new Image[]{
                Load.image("home/down.png"), Load.image("home/down_鼠标悬停.png"), Load.image("home/down_按下.png")
        };
        nowSongImg = Load.image("home/song1.png");
        selectedImg = Load.image("home/被选中的.png");
        songNameImg = Load.image("home/歌曲条.png");
        hitSongImg = Load.image("home/当前歌曲指示条.png");
//        setBackground(null);
//        Load.sound("1").loop(Clip.LOOP_CONTINUOUSLY); // 播放音乐
        setOpaque(false);
        new UpdateUI().start();
        addMouseMotionListener(this);
        addMouseListener(this);

    }

    /**
     * 响应键盘事件
     * @param keyCode 按键对应code
     */
    @Override
    public void onKeyDown(int keyCode) {
        AtomicInteger tmp = new AtomicInteger();
        tmp.set(1);
        Data.keyStatus[keyCode] = tmp;
        System.out.println(keyCode + " " + Data.keyStatus[keyCode]);
    }

    /**
     * 响应键盘事件
     * @param keyCode 按键对应code
     */
    @Override
    public void onKeyUp(int keyCode) {
        AtomicInteger tmp = new AtomicInteger();
        tmp.set(0);
        Data.keyStatus[keyCode] = tmp;
        System.out.println(keyCode + " " + Data.keyStatus[keyCode]);
    }

    /**
     * 响应鼠标事件
     * @param x 鼠标所在横坐标
     * @param y 鼠标所在纵坐标
     * @param struts 鼠标状态
     */
    public void onMouse(int x, int y, int struts) {
        if (buttonEasyStatus != MOUSE_DOWN) {
            buttonEasyStatus = 0;
        }
        if (buttonNormalStatus != MOUSE_DOWN) {
            buttonNormalStatus = 0;
        }
        if (buttonDifficultStatus != MOUSE_DOWN) {
            buttonDifficultStatus = 0;
        }
        if (buttonSetUpBackStatus != MOUSE_DOWN) {
            buttonSetUpBackStatus = 0;
        }
        buttonRatingStatus = buttonLoginStatus = buttonPlayStatus = buttonSongInfStatus = buttonUpStatus = buttonDownStatus = 0;

        int buttonStruts = struts == Scenes.MOUSE_MOVED ? 1 : struts == Scenes.MOUSE_DOWN ? 2 : 0;
        if (Rect.isInternal(x, y, 523, 580, 202, 125)) {
            if (buttonEasyStatus != MOUSE_DOWN) {
                buttonEasyStatus = buttonStruts;
            }
            /*
                为什么不用else，因为用else的话刚点下去的一瞬间，另外两个按钮不会立刻跳转
             */
            if (buttonEasyStatus == MOUSE_DOWN) {
                buttonNormalStatus = MOUSE_UP;
                buttonDifficultStatus = MOUSE_UP;
            }
            if (struts == Scenes.MOUSE_DOWN) {
                Data.difficulty = 1;
            }
        } else if (Rect.isInternal(x, y, 783, 580, 202, 125)) {

            if (buttonNormalStatus != MOUSE_DOWN) {
                buttonNormalStatus = buttonStruts;
            }
            if (buttonNormalStatus == MOUSE_DOWN) {
                buttonEasyStatus = MOUSE_UP;
                buttonDifficultStatus = MOUSE_UP;
            }
            if (struts == Scenes.MOUSE_DOWN) {
                Data.difficulty = 2;
            }
        } else if (Rect.isInternal(x, y, 1043, 580, 202, 125)) {
            if (buttonDifficultStatus != MOUSE_DOWN) {
                buttonDifficultStatus = buttonStruts;
            } /*else {
//                buttonSetUpBackStatus = MOUSE_UP;
                buttonEasyStatus = MOUSE_UP;
                buttonNormalStatus = MOUSE_UP;
            }*/
            if (buttonDifficultStatus == MOUSE_DOWN) {
                buttonEasyStatus = MOUSE_UP;
                buttonNormalStatus = MOUSE_UP;
            }
            if (struts == Scenes.MOUSE_DOWN) {
                Data.difficulty = 3;
            }
        } else if (Rect.isInternal(x, y, 643, 750, 500, 114)) {
            buttonPlayStatus = buttonStruts;
            playFlag = buttonEasyStatus == MOUSE_DOWN || buttonNormalStatus == MOUSE_DOWN || buttonDifficultStatus == MOUSE_DOWN;
            if (struts == Scenes.MOUSE_DOWN && playFlag) {
                Home.isEnd = true;
                Data.canvas.switchScenes("PlayInterface");
//                System.exit(0);
//                Data.canvas.switchScenes("End");
            }
        } else if (Rect.isInternal(x, y, 1038, 850, 230, 69)) {
            buttonSongInfStatus = buttonStruts;
            if (struts == Scenes.MOUSE_DOWN && playFlag) {
                Home.isEnd = true;
                Data.canvas.switchScenes("End", new RecordTemp(1));
            }
        } else if (Rect.isInternal(x, y, 120, 60, 126, 95)) {
            buttonUpStatus = buttonStruts;
            if (struts == Scenes.MOUSE_DOWN) {
                chooseSong.way = 1;
                new Thread(chooseSong).start();
            }
        } else if (Rect.isInternal(x, y, 120, 800, 126, 95)) {
            buttonDownStatus = buttonStruts;
            if (struts == Scenes.MOUSE_DOWN) {
                chooseSong.way = 2;
                new Thread(chooseSong).start();
            }

        }
    }

    /**
     * 绘画主页
     * @param g 图形
     */
    public void paint(Graphics g) {
        playFlag = buttonEasyStatus == MOUSE_DOWN || buttonNormalStatus == MOUSE_DOWN || buttonDifficultStatus == MOUSE_DOWN;
        g.drawImage(nowSongImg, Data.WIDTH / 2, 100, null);
        g.drawImage(easyButton[buttonEasyStatus], Data.WIDTH / 2 - 120, 580, null);
        g.drawImage(normalButton[buttonNormalStatus], Data.WIDTH / 2 + 140, 580, null);
        g.drawImage(difficultButton[buttonDifficultStatus], Data.WIDTH / 2 + 400, 580, null);
        if (playFlag) {
            g.drawImage(playButton[buttonPlayStatus], Data.WIDTH / 2, 750, null);
            g.drawImage(songInfButton[buttonSongInfStatus], 1038, 850, null);
        }
        g.drawImage(upButton[buttonUpStatus], 120, 60, null);
        g.drawImage(downButton[buttonDownStatus], 120, 800, null);
        g.drawImage(hitSongImg, 380, 413, null);
        Font f = new Font("黑体", Font.BOLD, 65);
        Data.canvas.drawCenteredStringByOutline(g, String.valueOf(Data.currentSong.easy), 212, Data.WIDTH / 2 - 120,
                1, f, 675, Color.WHITE, new Color(117, 188, 214));
        Data.canvas.drawCenteredStringByOutline(g, String.valueOf(Data.currentSong.normal), 212, Data.WIDTH / 2 + 140,
                1, f, 675, Color.WHITE, new Color(237, 114, 209));
        Data.canvas.drawCenteredStringByOutline(g, String.valueOf(Data.currentSong.hard), 212, Data.WIDTH / 2 + 400,
                1, f, 675, Color.WHITE, new Color(245, 165, 152));
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
//        onMouse(e.getX(), e.getY(), Scenes.MOUSE_DOWN);
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


    class UpdateUI extends Thread {
        public void run() {
            System.out.println("Home的线程开始了");
            int sleepTime = 1000 / Data.FPS;
            while (!isEnd) {
                try {
                    updateUI();
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Home的线程结束了");
        }
    }
}
