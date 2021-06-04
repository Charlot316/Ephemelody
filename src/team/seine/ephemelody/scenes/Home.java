package team.seine.ephemelody.scenes;

import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.main.Canvas;
import team.seine.ephemelody.utils.Load;
import team.seine.ephemelody.utils.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.GlyphVector;
import java.util.concurrent.atomic.AtomicInteger;

public class Home extends JPanel implements Scenes, MouseMotionListener, MouseListener, KeyListener {
    int buttonRatingStatus = 0, buttonSetUpBackStatus = 0, buttonLoginStatus = 0, buttonEasyStatus = 0, buttonNormalStatus = 0, buttonDifficultStatus = 0,
            buttonPlayStatus = 0, buttonSongInfStatus = 0, buttonUpStatus = 0, buttonDownStatus = 0;
    public Double rate; //潜力值
    /*public Image[] ratingButton;
    public Image setupButton;
    public Image[] setupBackButton;
    public Image[] loginButton;*/
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

    public Boolean playFlag;
    public Home() {
        setBounds(0, 0, Data.WIDTH, Data.HEIGHT);
        setLayout(null);
        /*ratingButton = new Image[]{
                Load.image("home/潜力值_0.png"), Load.image("home/潜力值_1.png"),
                Load.image("home/潜力值_2.png"), Load.image("home/潜力值_3.png"),
                Load.image("home/潜力值_4.png"), Load.image("home/潜力值_5.png")
        };
        setupButton = Load.image("home/设置.png");
        setupBackButton = new Image[]{
                Load.image("home/设置_未选中.png"), Load.image("home/设置_鼠标悬停.png"), Load.image("home/设置_按下.png")
        };
        loginButton = new Image[] {
                Load.image("home/登录.png"), Load.image("home/登录_鼠标悬停.png"), Load.image("home/登录_按下.png")
        };*/
        playFlag = false;
        playButton = new Image[]{
                Load.image("home/开始游戏.png"), Load.image("home/开始游戏_鼠标悬停.png"), Load.image("home/开始游戏_按下.png")
        };
        easyButton = new Image[] {
                Load.image("home/简单.png"), Load.image("home/简单_鼠标悬停.png"), Load.image("home/简单_按下.png")
        };
        normalButton = new Image[] {
                Load.image("home/普通.png"), Load.image("home/普通_鼠标悬停.png"), Load.image("home/普通_按下.png")
        };
        difficultButton = new Image[] {
                Load.image("home/困难.png"), Load.image("home/困难_鼠标悬停.png"), Load.image("home/困难_按下.png")
        };
        songInfButton = new Image[] {
                Load.image("home/歌曲详情.png"), Load.image("home/歌曲详情_鼠标悬停.png"), Load.image("home/歌曲详情_按下.png")
        };
        upButton = new Image[] {
                Load.image("home/up.png"), Load.image("home/up_鼠标悬停.png"), Load.image("home/up_按下.png")
        };
        downButton = new Image[] {
                Load.image("home/down.png"), Load.image("home/down_鼠标悬停.png"), Load.image("home/down_按下.png")
        };
        nowSongImg = Load.image("home/song1.png");
        selectedImg = Load.image("home/被选中的.png");
//        setBackground(null);
        setOpaque(false);
        new UpdateUI().start();
        addMouseMotionListener(this);
        addMouseListener(this);

    }

    @Override
    public void onKeyDown(int keyCode) {
        AtomicInteger tmp = new AtomicInteger();
        tmp.set(1);
        Data.keyStatus[keyCode] = tmp;
        System.out.println(keyCode + " " + Data.keyStatus[keyCode]);
    }

    @Override
    public void onKeyUp(int keyCode) {
        AtomicInteger tmp = new AtomicInteger();
        tmp.set(0);
        Data.keyStatus[keyCode] = tmp;
        System.out.println(keyCode + " " + Data.keyStatus[keyCode]);
    }

    public void onMouse(int x, int y, int struts) {
//        System.out.println(x + " " + y);
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
        // e.getY() 获取的坐标包含了 窗口标题栏的高度，判断点击位置时，需要减去，如果监听鼠标事件时，监听对象为JPanel，则不需要此步骤,, 本程序监听的是JFrame对象
        // 275 + Data.TITLE_BOX_HEIGHT, 这里因为以上原因，在画面上看到的位置，还需要加一个标题栏高度
        // 这里判断的就是 鼠标点击的位置，是否在相应的按钮上方
        /*if(Rect.isInternal(x, y, 900, 0, 98, 50)) {
            if (buttonSetUpBackStatus != MOUSE_DOWN) {
                buttonSetUpBackStatus = buttonStruts;
            } else {
                Data.canvas.switchScenes("SetUp");
                *//*buttonEasyStatus = MOUSE_UP;
                buttonNormalStatus = MOUSE_UP;
                buttonDifficultStatus = MOUSE_UP;*//*
            }

            if(struts == Scenes.MOUSE_DOWN) {
//                Data.canvas.switchScenes("Game");
            }
        } else */
        if (Rect.isInternal(x, y, 503, 580, 202, 125)) {
            if (buttonEasyStatus != MOUSE_DOWN) {
                buttonEasyStatus = buttonStruts;
            } /*else {
//                buttonSetUpBackStatus = MOUSE_UP;
                buttonNormalStatus = MOUSE_UP;
                buttonDifficultStatus = MOUSE_UP;
            }*/
            /*
                为什么不用else，因为用else的话刚点下去的一瞬间，另外两个按钮不会立刻跳转
             */
            if (buttonEasyStatus == MOUSE_DOWN) {
                buttonNormalStatus = MOUSE_UP;
                buttonDifficultStatus = MOUSE_UP;
            }
            if(struts == Scenes.MOUSE_DOWN) {
//                System.out.println("666");
//                Data.canvas.switchScenes("End");
//                Data.canvas.switchScenes("About");
            }
        }else if (Rect.isInternal(x, y, 783, 580, 202, 125)) {

            if (buttonNormalStatus != MOUSE_DOWN) {
                buttonNormalStatus = buttonStruts;
            } /*else {
//                buttonSetUpBackStatus = MOUSE_UP;
                buttonEasyStatus = MOUSE_UP;
                buttonDifficultStatus = MOUSE_UP;
            }*/
            if (buttonNormalStatus == MOUSE_DOWN) {
                buttonEasyStatus = MOUSE_UP;
                buttonDifficultStatus = MOUSE_UP;
            }
            if(struts == Scenes.MOUSE_DOWN) {
//                Data.canvas.switchScenes("Site");
            }
        }else if (Rect.isInternal(x, y, 1063, 580, 202, 125)) {
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
            if(struts == Scenes.MOUSE_DOWN) {
//                Data.canvas.switchScenes("End");
            }
        } else if (Rect.isInternal(x, y, 643, 750, 500, 114)) {
            buttonPlayStatus = buttonStruts;
            playFlag = buttonEasyStatus == MOUSE_DOWN || buttonNormalStatus == MOUSE_DOWN || buttonDifficultStatus == MOUSE_DOWN;
            if(struts == Scenes.MOUSE_DOWN && playFlag) {
                Data.canvas.switchScenes("PlayInterface");
//                System.exit(0);
//                Data.canvas.switchScenes("End");
            }
        } else if (Rect.isInternal(x, y, 1038, 850, 230, 69)) {
            buttonSongInfStatus = buttonStruts;
            if (struts == Scenes.MOUSE_DOWN) {
                Data.canvas.switchScenes("End");
            }
        } else if (Rect.isInternal(x, y, 100, 60, 126, 95)) {
            buttonUpStatus = buttonStruts;
        } else if (Rect.isInternal(x, y, 100, 800, 126, 95)) {
            buttonDownStatus = buttonStruts;
        }
            /*else if (Rect.isInternal(x, y, 1080, -3, 188, 69)) {
            buttonLoginStatus = buttonStruts;
            if(struts == Scenes.MOUSE_DOWN) {
                Data.canvas.switchScenes("Login");
//                System.exit(0);
            }
        }*/
    }
    public void paint(Graphics g) {
        playFlag = buttonEasyStatus == MOUSE_DOWN || buttonNormalStatus == MOUSE_DOWN || buttonDifficultStatus == MOUSE_DOWN;
        g.drawImage(nowSongImg, Data.WIDTH / 2, 100, null);
        g.drawImage(easyButton[buttonEasyStatus], Data.WIDTH / 2 - 140, 580, null);
        g.drawImage(normalButton[buttonNormalStatus], Data.WIDTH / 2 + 140, 580, null);
        g.drawImage(difficultButton[buttonDifficultStatus], Data.WIDTH / 2 + 420, 580, null);
        if (playFlag) {
            g.drawImage(playButton[buttonPlayStatus], Data.WIDTH / 2, 750, null);
        }
        g.drawImage(upButton[buttonUpStatus], 100, 60, null);
        g.drawImage(downButton[buttonDownStatus], 100, 800, null);
        /*g.drawImage(ratingButton[buttonRatingStatus], Data.WIDTH / 2 - 60, -16, null);
        g.drawImage(setupBackButton[buttonSetUpBackStatus], 900, 0, null);
        g.drawImage(setupButton, 934, 8, null);
        g.drawImage(loginButton[buttonLoginStatus], 1080, -3, null);*/
        g.drawImage(selectedImg, 0, Data.HEIGHT / 2 - 30, null);
        g.drawImage(songInfButton[buttonSongInfStatus], 1038, 850, null);
        g.setFont(new Font("黑体", Font.PLAIN, 65));
//        g.setColor(new Color(117, 188, 214));
        Data.canvas.paintString("3", g, 595, 675, 65, new Color(117, 188, 214));
        g.translate(-595, -675);
        Data.canvas.paintString("6", g, 875, 675, 65, new Color(237, 114, 209));
        g.translate(-875, -675);
        Data.canvas.paintString("9", g, 1155, 675, 65, new Color(245, 165, 152));
//        g.drawString("3", 595, 675);
        /*g.setColor(new Color(237, 114, 209));
        g.drawString("6", 875, 675);
        g.setColor(new Color(245, 165, 152));
        g.drawString("9", 1155, 675);*/
//        Integer countX = 65, count = 1;
//        for (String song : Data.songList) { // 到时候改成待展示的歌曲名单列表
//            if (count == 3) {
//                countX += 20;
//                Data.canvas.paintString(song, g, countX, 200, 60);
//                countX -= 20;
//                g.translate(-20, 0);
//            } else {
//                Data.canvas.paintString(song, g, countX, 200, 40);
//            }
//            g.translate(-countX, -50);
//            count += 1;
//        }
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
