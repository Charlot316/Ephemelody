package team.seine.ephemelody.playinterface;

import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.scenes.PlayInterface;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class ScoreAndComboDisplay extends JPanel{
    public int score;
    public int combo;
    public static AtomicInteger count=new AtomicInteger();
    public ScoreAndComboDisplay(){
        setBounds(0, 0, Data.WIDTH, Data.HEIGHT);
        this.setVisible(true);
        setLayout(null);
        this.setOpaque(false);
    }
    public void paint(Graphics g) {
        Graphics2D g_2d = (Graphics2D) g;
        Font f=new Font(null,Font.BOLD,40);
        g_2d.setFont(f);
        g_2d.setColor(new Color(255, 255, 255, 200));
        g_2d.drawString(String.valueOf(score), Data.WIDTH*4/5,50);
        g_2d.drawString(String.valueOf(combo), Data.WIDTH/2,50);
    }
}
