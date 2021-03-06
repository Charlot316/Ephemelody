package team.seine.ephemelody.scenes;

import database.Entity.Song;
import database.RecordController;
import database.SongController;
import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.playinterface.RecordTemp;
import team.seine.ephemelody.utils.Load;
import team.seine.ephemelody.utils.Rect;

import javax.crypto.spec.PSource;
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
    public Image[] nowSongImg;
    public Image listImg;
    public Image myGradeImg;
    public Image gradeImg;
    public Image[] resultImg;
    public Image[] tryAgainButton;
    public Image[] returnButton;
    public Image[] ratingButton;
    public int buttonTryAgainStatus = 0;
    public int buttonReturnStatus = 0;
    public int resultStatus = 0;
    public String songName;
    public int nowPoints;
    public int highestPoints;
    public int nowRanking;
    public boolean rankingFlag;
    public AtomicInteger pureCount;
    public AtomicInteger farCount;
    public AtomicInteger lostCount;
    public AtomicInteger maxCombo;
    public double changePotential;
    public double nowPotential;
    public int way;
    public static boolean isEnd;
    public String[] rankingUserId = new String[100];
    public int[] rankingPoints = new int[100];

    /**
     * End构造函数
     * @param recordTemp 保存玩家游玩信息的实例
     */
    public End(RecordTemp recordTemp) {
        End.isEnd = false;
        this.pureCount = new AtomicInteger();
        this.farCount = new AtomicInteger();
        this.lostCount = new AtomicInteger();
        this.way = recordTemp.way;
        ResultSet rs, resultSet;
        Song nowSong;
        int count = 0;
        if (recordTemp.way == 1) {
            try {
                if (Data.nowPlayer == null) {
                    rs = RecordController.getBestRecords(Data.songId, Data.difficulty);
                } else {
                    rs = RecordController.getPersonalBestRecordsBySongId(Data.nowPlayer.getPlayerID(), Data.songId, Data.difficulty);
                }
                while (rs.next()) {
                    AtomicInteger tmp1 = new AtomicInteger();
                    AtomicInteger tmp2 = new AtomicInteger();
                    AtomicInteger tmp3 = new AtomicInteger();
                    this.highestPoints = rs.getInt("score");
                    this.nowPoints = highestPoints;
                    tmp1.set(rs.getInt("pureCount"));
                    this.pureCount = tmp1;
                    tmp2.set(rs.getInt("farCount"));
                    this.farCount = tmp2;
                    tmp3.set(rs.getInt("lostCount"));
                    this.lostCount = tmp3;
                }
                resultSet = RecordController.getAllBestRecords(Data.songId, Data.difficulty);
                count = 0;
                rankingFlag = true;
                while (resultSet.next()) {
                    rankingUserId[count] = resultSet.getString("playerID");
                    rankingPoints[count] = resultSet.getInt("score");
                    if (Data.nowPlayer != null) {
                        if (nowPoints == rankingPoints[count] && rankingFlag) {
                            nowRanking = count + 1;
                            rankingFlag = false;
                        }
                    }
                    count += 1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            try {
                if (Data.nowPlayer != null) {
                    rs = RecordController.getPersonalBestRecordsBySongId(Data.nowPlayer.getPlayerID(), Data.songId, Data.difficulty);
                    nowSong = SongController.selectSongById(Data.songId, Data.difficulty);
                    this.nowPoints = recordTemp.score;
                    this.changePotential = recordTemp.changePotential;
                    this.nowPotential = recordTemp.nowPotential;
                    while (rs.next()) {
                        this.highestPoints = rs.getInt("score");
                    }
                    this.pureCount = recordTemp.pureCount;
                    this.farCount = recordTemp.farCount;
                    this.lostCount = recordTemp.lostCount;
                    this.maxCombo = recordTemp.combo;
                    resultSet = RecordController.getAllBestRecords(Data.songId, Data.difficulty);
                    count = 0;
                    rankingFlag = true;
                    while (resultSet.next()) {
                        rankingUserId[count] = resultSet.getString("playerID");
                        rankingPoints[count] = resultSet.getInt("score");
                        if (Data.nowPlayer.getPlayerID().equals(rankingUserId[count]) && rankingFlag) {
                            nowRanking = count + 1;
                            rankingFlag = false;
                        }
                        count += 1;
                    }
                    if (pureCount.get() == nowSong.getNoteCount()) {
                        resultStatus = 2;
                    } else if (maxCombo.get() == nowSong.getNoteCount()) {
                        resultStatus = 1;
                    } else {
                        resultStatus = 0;
                    }
                } else {
                    this.nowPoints = recordTemp.score;
                    this.changePotential = recordTemp.changePotential;
                    this.nowPotential = recordTemp.nowPotential;
                    this.highestPoints = recordTemp.score;
                    this.pureCount = recordTemp.pureCount;
                    this.farCount = recordTemp.farCount;
                    this.lostCount = recordTemp.lostCount;
                    resultSet = RecordController.getAllBestRecords(Data.songId, Data.difficulty);
                    count = 0;
                    while (resultSet.next()) {
                        rankingUserId[count] = resultSet.getString("playerID");
                        rankingPoints[count] = resultSet.getInt("score");
                        count += 1;
                    }
                    rankingFlag = true;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        setBounds(0, 0, Data.WIDTH, Data.HEIGHT);
        setVisible(true);
        setOpaque(false);
        endBackgroundImg = Load.image("end/结算背景.png");
        nowSongImg = new Image[] {
                Load.image("cover/0.jpg"), Load.image("cover/1.jpg"), Load.image("cover/2.jpg"),
                Load.image("cover/3.jpg"), Load.image("cover/4.jpg")
        };
        listImg = Load.image("end/排行榜.png");
        myGradeImg = Load.image("end/我的成绩.png");
        gradeImg = Load.image("end/成绩条.png");
        resultImg = new Image[]{
                Load.image("end/clear_normal.png"), Load.image("end/clear_full.png"), Load.image("end/clear_pure.png")
        };
        tryAgainButton = new Image[]{
                Load.image("end/重试.png"), Load.image("end/重试_鼠标悬停.png"), Load.image("end/重试_按下.png")
        };
        returnButton = new Image[]{
                Load.image("end/返回.png"), Load.image("end/返回_鼠标悬停.png"), Load.image("end/返回_按下.png")
        };
        ratingButton = new Image[]{
                Load.image("end/rating_down.png"), Load.image("end/rating_keep.png"), Load.image("end/rating_up.png")
        };
        songName = Data.currentSong.name;
        addMouseListener(this);
        addMouseMotionListener(this);
        new End.UpdateUI().start();
    }

    /**
     * 绘画结算页面
     * @param g 图形
     */
    public void paint(Graphics g) {
        g.drawImage(endBackgroundImg, 0, (Data.HEIGHT - 632) / 2, null);
        g.drawImage(listImg, 980, 310, null);
        g.drawImage(nowSongImg[Data.songId], 90, 369, 350, 350, null);
        g.drawImage(myGradeImg, 993, 680, null);
        g.drawImage(resultImg[resultStatus], 360, 300, null);
        int countY = 360;
        for (int i = 0; i < 3; i++) {
            g.drawImage(gradeImg, 993, countY, null);
            countY += 60;
        }
        g.drawImage(returnButton[buttonReturnStatus], 0, 860, null);
        g.drawImage(tryAgainButton[buttonTryAgainStatus], 1038, 860, null);

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
        if (way == 2 && Data.nowPlayer != null) {
            g.setFont(new Font("黑体", Font.BOLD, 20));
            g.setColor(Color.WHITE);
            if (changePotential == 0) {
                g.drawImage(ratingButton[1], Data.WIDTH / 2 + 50, 20, null);
            } else if (changePotential > 0) {
                g.drawImage(ratingButton[2], Data.WIDTH / 2 + 50, 20, null);
                g.drawString("+" + String.format("%.2f", changePotential), Data.WIDTH / 2 + 72, 58);
            } else {
                g.drawImage(ratingButton[0], Data.WIDTH / 2 + 50, 20, null);
                g.drawString(String.format("%.2f", changePotential), Data.WIDTH / 2 + 72, 58);
            }
        }
        Font f = new Font("黑体", Font.BOLD, 35);
        Data.canvas.paintString(String.format("%03d", pureCount.get()), f, g, 700, 570, 1, Color.WHITE, Color.BLACK);
        g.translate(-700, -570);
        Data.canvas.paintString(String.format("%03d", farCount.get()), f, g, 700, 632, 1, Color.WHITE, Color.BLACK);
        g.translate(-700, -632);
        Data.canvas.paintString(String.format("%03d", lostCount.get()), f, g, 700, 695, 1, Color.WHITE, Color.BLACK);
        g.translate(-700, -695);
        int count1 = 395, count2 = 420, count3 = 408;
        for (int i = 0; i < 3; i++) {
            g.setFont(new Font("黑体", Font.PLAIN, 30));
            g.setColor(Color.WHITE);
            if (rankingUserId[i] != null) {
                g.drawString(rankingUserId[i], 1050, count1);
            }
            g.setFont(new Font("黑体", Font.PLAIN, 25));
            if (rankingPoints[i] != 0) {
                g.drawString(String.valueOf(rankingPoints[i]), 1050, count2);
            }
            g.setFont(new Font("黑体", Font.PLAIN, 30));
            g.setColor(new Color(92, 64, 100));
            g.drawString(String.valueOf(i + 1), 1173, count3);
            count1 += 60;
            count2 += 60;
            count3 += 60;
        }

        if (!rankingFlag) {

            g.setFont(new Font("黑体", Font.PLAIN, 30));
            g.setColor(new Color(92, 64, 100));
            g.drawString(String.valueOf(nowRanking), 1173, 730);
        } else {
            g.setFont(new Font("黑体", Font.PLAIN, 30));
            g.setColor(new Color(92, 64, 100));
            g.drawString("-", 1173, 730);
        }
        if (Data.nowPlayer != null){
            g.setFont(new Font("黑体", Font.PLAIN, 25));
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(highestPoints), 1050, 738);
        } else {
            g.setFont(new Font("黑体", Font.PLAIN, 25));
            g.setColor(Color.WHITE);
            g.drawString("00000000", 1050, 738);
        }
    }

    @Override
    public void onKeyDown(int keyCode) {

    }

    @Override
    public void onKeyUp(int keyCode) {

    }

    /**
     * 重写的鼠标事件
     * @param x 鼠标点击的横坐标
     * @param y 鼠标点击的纵坐标
     * @param struts 鼠标的状态
     */
    @Override
    public void onMouse(int x, int y, int struts) {
        buttonReturnStatus = buttonTryAgainStatus = 0;
        int buttonStruts = struts == Scenes.MOUSE_MOVED ? 1 : struts == Scenes.MOUSE_DOWN ? 2 : 0;
        if (Rect.isInternal(x, y, 0, 850, 230, 69)) {
            buttonReturnStatus = buttonStruts;
            if (struts == Scenes.MOUSE_DOWN) {
                End.isEnd = true;
                Data.canvas.switchScenes("Home");
            }
        } else if (Rect.isInternal(x, y, 1038, 850, 230, 69)) {
            buttonTryAgainStatus = buttonStruts;
            if (struts == Scenes.MOUSE_DOWN) {
                End.isEnd = true;
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
            while (!isEnd) {
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

