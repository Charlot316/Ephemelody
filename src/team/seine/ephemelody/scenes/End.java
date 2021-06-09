package team.seine.ephemelody.scenes;

import database.PlayerController;
import database.RecordController;
import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.playinterface.RecordTemp;
import team.seine.ephemelody.utils.Load;
import team.seine.ephemelody.utils.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class End extends JPanel implements Scenes, MouseMotionListener, MouseListener {
    public Image backgroundImg;
    public Image menuImg;
    public Image endBackgroundImg;
    public Image nowSongImg;
    public Image listImg;
    public Image myGradeImg;
    public Image gradeImg;
    public Image[] resultImg;
    public Image[] tryAgainButton;
    public Image[] returnButton;
    public Image[] ratingButton;
    public int buttonTryAgainStatus = 0;
    public int buttonReturnStatus = 0;
    public int resultStatus;
    public String songName;
    public int nowPoints;
    public int highestPoints;
    public AtomicInteger pureCount;
    public AtomicInteger farCount;
    public AtomicInteger lostCount;
    public double changePotential;
    public double nowPotential;
    public int way;
    public End(RecordTemp recordTemp) {
        AtomicInteger tmp = new AtomicInteger();
        this.pureCount = new AtomicInteger();
        this.farCount = new AtomicInteger();
        this.lostCount = new AtomicInteger();
        this.way = recordTemp.way;
        if (recordTemp.way == 1) {
            try {
                ResultSet rs;
                if (Data.nowPlayer == null) {
                    rs = RecordController.getBestRecords(Data.songId);
                } else {
                    rs = RecordController.getPersonalBestRecordsBySongId(Data.nowPlayer.getPlayerID(), Data.songId,Data.difficulty);
                }
                while (rs.next()) {
                    this.highestPoints = rs.getInt("score");
                    this.nowPoints = highestPoints;
                    tmp.set(rs.getInt("pureCount"));
                    this.pureCount = tmp;
                    tmp.set(rs.getInt("farCount"));
                    this.farCount = tmp;
                    tmp.set(rs.getInt("lostCount"));
                    this.lostCount = tmp;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
                //System.out.println(Data.nowPlayer.getPlayerID() + " " + Data.songId);
                this.nowPoints = recordTemp.score;
                this.changePotential = recordTemp.changePotential;
                this.nowPotential = recordTemp.nowPotential;
                this.highestPoints = recordTemp.score;
                this.pureCount = recordTemp.pureCount;
                this.farCount = recordTemp.farCount;
                this.lostCount = recordTemp.lostCount;
        }
        setBounds(0, 0, Data.WIDTH, Data.HEIGHT);
        setVisible(true);
        setOpaque(false);
        endBackgroundImg = Load.image("end/结算背景.png");
        nowSongImg = Load.image("song/1/song1.png"); // 到时候得改成具体的歌曲背景
        listImg = Load.image("end/排行榜.png");
        myGradeImg = Load.image("end/我的成绩.png");
        gradeImg = Load.image("end/成绩条.png");
        resultImg = new Image[] {
                Load.image("end/clear_normal.png"), Load.image("end/clear_full.png"), Load.image("end/clear_pure.png")
        };
        tryAgainButton = new Image[] {
                Load.image("end/重试.png"), Load.image("end/重试_鼠标悬停.png"), Load.image("end/重试_按下.png")
        };
        returnButton = new Image[] {
                Load.image("end/返回.png"), Load.image("end/返回_鼠标悬停.png"), Load.image("end/返回_按下.png")
        };
        ratingButton = new Image[] {
                Load.image("end/rating_down.png"), Load.image("end/rating_keep.png"), Load.image("end/rating_up.png")
        };
        songName = Data.realSongList.get(Data.songId);
        addMouseListener(this);
        addMouseMotionListener(this);
        new End.UpdateUI().start();
    }
    public void paint(Graphics g) {
        g.drawImage(endBackgroundImg, 0, (Data.HEIGHT - 632) / 2, null);
        g.drawImage(listImg, 980, 310, null);
        g.drawImage(nowSongImg, 90, 369, null);
        g.drawImage(myGradeImg, 993, 680, null);
        g.drawImage(resultImg[resultStatus], 360, 300, null);
        int countY = 360;
        for (int i = 0; i < 4; i++) {
            g.drawImage(gradeImg, 993, countY, null);
            countY += 60;
        }
        g.drawImage(returnButton[buttonReturnStatus], 0, 850, null);
        g.drawImage(tryAgainButton[buttonTryAgainStatus], 1038, 850, null);

        g.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 20));
        g.setColor(new Color(119, 97, 125));
        g.drawString("COMBO:", 90, 360);


        g.setColor(new Color(255, 255, 255));
        Data.canvas.drawCenteredString(g, songName, Data.WIDTH, 0, new Font("黑体", Font.PLAIN, 75), 255);

        g.setFont(new Font("黑体", Font.PLAIN, 60));
        g.setColor(new Color(255, 255, 255));
        g.drawString(String.format("%08d", nowPoints), 550, 435);

        g.setFont(new Font("黑体", Font.PLAIN, 35));
        g.setColor(new Color(255, 255, 255));
        g.drawString(String.format("%08d", highestPoints), 680, 485);
//        g.drawImage(ratingButton[0], Data.WIDTH / 2 + 50, 20, null);

        g.setFont(new Font("黑体", Font.BOLD, 20));
        g.setColor(Color.WHITE);
//        g.drawString(String.format("%.2f", 2.8), Data.WIDTH / 2 - 22,  58);
        if (way == 2) {
            //System.out.println(changePotential);
            if (changePotential == 0) {
                g.drawImage(ratingButton[1], Data.WIDTH / 2 + 50, 20, null);
            } else if (changePotential > 0) {
                g.drawImage(ratingButton[2], Data.WIDTH / 2 + 50, 20, null);
                g.drawString("+" + String.format("%.2f", changePotential), Data.WIDTH / 2 + 72,  58);
            } else {
                g.drawImage(ratingButton[0], Data.WIDTH / 2 + 50, 20, null);
                g.drawString(String.format("%.2f", changePotential), Data.WIDTH / 2 + 72,  58);
            }
            g.drawString(String.format("%.2f", nowPotential), Data.WIDTH / 2 - 22,  58);
        }
        Font f = new Font("黑体", Font.BOLD, 35);
        Data.canvas.paintString(String.format("%03d", pureCount.get()), f, g, 700, 570, 1, Color.WHITE, Color.BLACK);
        g.translate(-700, -570);
        Data.canvas.paintString(String.format("%03d", farCount.get()), f, g, 700, 632, 1, Color.WHITE, Color.BLACK);
        g.translate(-700, -632);
        Data.canvas.paintString(String.format("%03d", lostCount.get()), f, g, 700, 695, 1, Color.WHITE, Color.BLACK);
        g.translate(-700, -695);

//        Data.canvas.paintString("+" + String.format("%.2f", 0.15), f, g, Data.WIDTH / 2 + 75,  58, 1, Color.WHITE, Color.BLACK);
    }

    @Override
    public void onKeyDown(int keyCode) {

    }

    @Override
    public void onKeyUp(int keyCode) {

    }

    @Override
    public void onMouse(int x, int y, int struts) {
        buttonReturnStatus = buttonTryAgainStatus = 0;
//        System.out.println(buttonReturnStatus);
        int buttonStruts = struts == Scenes.MOUSE_MOVED ? 1 : struts == Scenes.MOUSE_DOWN ? 2 : 0;
//        System.out.println(buttonStruts);
        if(Rect.isInternal(x, y, 0, 850, 230, 69)) {
            buttonReturnStatus = buttonStruts;
//            System.out.println(buttonReturnStatus);
            if(struts == Scenes.MOUSE_DOWN) {
                Data.canvas.switchScenes("Home");
            }
        } else if (Rect.isInternal(x, y, 1038, 850, 230, 69)) {
            buttonTryAgainStatus = buttonStruts;
            if(struts == Scenes.MOUSE_DOWN) {
                Data.canvas.switchScenes("PlayInterface");
            }
        }
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

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        onMouse(e.getX(), e.getY(), Scenes.MOUSE_MOVED);
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

