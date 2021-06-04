package team.seine.ephemelody.scenes;

import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.utils.Load;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class End extends JPanel implements Scenes, MouseMotionListener, MouseListener {
    public Image backgroundImg;
    public Image menuImg;
    public Image endBackgroundImg;
    public Image nowSongImg;
    public Image listImg;
    public Image myGradeImg;
    public Image gradeImg;
    public Image fullRecallImg;
    public End() {
        setBounds(0, 0, Data.WIDTH, Data.HEIGHT);
        setVisible(true);
        setOpaque(false);
        endBackgroundImg = Load.image("end/结算背景.png");
        nowSongImg = Load.image("song/1/song1.png"); // 到时候得改成具体的歌曲背景
        listImg = Load.image("end/排行榜.png");
        myGradeImg = Load.image("end/我的成绩.png");
        gradeImg = Load.image("end/成绩条.png");
        fullRecallImg = Load.image("end/clear_full.png");
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    public void paint(Graphics g) {
        g.drawImage(endBackgroundImg, 0, (Data.HEIGHT - 632) / 2, null);
        g.drawImage(listImg, 980, 310, null);
        g.drawImage(nowSongImg, 90, 369, null);
        g.drawImage(myGradeImg, 993, 680, null);
        int countY = 360;
        for (int i = 0; i < 4; i++) {
            g.drawImage(gradeImg, 993, countY, null);
            countY += 60;
        }
//        g.drawImage(gradeImg, 993, 40, null);
        g.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 20));
        g.setColor(new Color(119, 97, 125));
        g.drawString("COMBO:", 90, 360);
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

    @Override
    public void mouseClicked(MouseEvent e) {

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

    }
}

