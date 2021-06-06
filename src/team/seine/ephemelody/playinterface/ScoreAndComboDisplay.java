package team.seine.ephemelody.playinterface;

import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.scenes.PlayInterface;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class ScoreAndComboDisplay extends JPanel implements Runnable{
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
        g_2d.drawString(String.valueOf(count.get()), Data.WIDTH*4/5,50);
        g_2d.drawString(String.valueOf(PlayInterface.combo.get()), Data.WIDTH/2,50);
    }
    public void run(){
        count.set(0);
        while((System.currentTimeMillis()- PlayInterface.startTime)<PlayInterface.finalEndTime){
            this.repaint();
            while(count.get()<PlayInterface.currentScore.get()){
                if(count.get()+8769<=PlayInterface.currentScore.get())count.getAndAdd(8769);
                else count.set(PlayInterface.currentScore.get());
                try {
                    sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
