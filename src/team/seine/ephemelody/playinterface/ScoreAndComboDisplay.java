package team.seine.ephemelody.playinterface;

import team.seine.ephemelody.scenes.PlayInterface;
import team.seine.ephemelody.utils.Load;
import team.seine.ephemelody.data.Data;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class ScoreAndComboDisplay extends JPanel {
    public int score;
    public int combo;
    public static AtomicInteger count = new AtomicInteger();
    public Image scoreBackgroundImg;
    public ScoreAndComboDisplay() {
        scoreBackgroundImg = Load.image("playing/scorebg.png");
        setBounds(0, 0, Data.WIDTH, Data.HEIGHT);
        this.setVisible(true);
        setLayout(null);
        this.setOpaque(false);
    }

    public void paint(Graphics g) {
        g.drawImage(scoreBackgroundImg, 0, 0, null);
        Font f = new Font("Artifakt Element", Font.PLAIN, 50);
        g.setFont(f);
        g.setColor(new Color(255, 255, 255, 200));
        g.drawString(String.format("%08d", score), Data.WIDTH * 4 / 5, 50);
        g.drawString(String.valueOf(combo), Data.WIDTH / 2 - 30, 50);
//        f = new Font("黑体", Font.PLAIN, 30);
//        g.setFont(f);
//        if(!PlayInterface.isPaused){
//            g.drawString("P:暂停", 10, 50);
//        }
//        else{
//            g.drawString("C:继续", 10, 50);
//            g.drawString("ESC:退出", 10, 100);
//        }
    }
}
