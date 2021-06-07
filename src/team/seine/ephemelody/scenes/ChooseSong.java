package team.seine.ephemelody.scenes;

import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.utils.Load;

import javax.swing.*;
import java.awt.*;

public class ChooseSong extends JPanel implements Scenes, Runnable{
    public Image songNameImg;
    public Image hitSongImg;
    public Integer way;
    public Integer x;
    public Integer y;
    public Integer x1;
    public Integer y1;
    public Integer count;
    public String[] song;
    public ChooseSong() {
        setBounds(0, 180, 529, 600);
        setVisible(true);
        setOpaque(false);
        this.x = -100;
        this.y = -8;
        this.x1 = 0;
        this.y1 = 60;
        this.count = 130;
        hitSongImg = Load.image("home/当前歌曲指示条.png");
        songNameImg = Load.image("home/歌曲条.png");
    }

    public void paint(Graphics g) {

        g.drawImage(songNameImg, x, y - count, null);
        g.setFont(new Font("黑体", Font.BOLD, 45));
        g.drawImage(songNameImg, x, y, null);
        g.drawImage(songNameImg, x, y + count, null);
        g.drawImage(songNameImg, x, y + count * 2, null);
        g.drawImage(songNameImg, x, y + count * 3, null);
        g.drawImage(songNameImg, x, y + count * 4, null);
        g.drawImage(songNameImg, x, y + count * 5, null);
        g.drawImage(hitSongImg, 380, 233, null);
        song = new String[] {
                Data.songList.get(0), Data.songList.get(1), Data.songList.get(2), Data.songList.get(3),  Data.songList.get(4)
        };
        Font font = new Font("黑体", Font.BOLD, 45);
        FontMetrics metrics = g.getFontMetrics(font);
        x1 = (390 - metrics.stringWidth(song[0])) / 2 + 0;
        Data.canvas.paintString(song[0], font, g, x1, y1, 1, Color.WHITE, Color.BLACK);
        g.translate(-x1, -y1);
        x1 = (390 - metrics.stringWidth(song[1])) / 2 + 0;
        Data.canvas.paintString(song[1], font, g, x1, y1 + count, 1, Color.WHITE, Color.BLACK);
        g.translate(-x1, -y1 - count);
        x1 = (390 - metrics.stringWidth(song[2])) / 2 + 0;
        Data.canvas.paintString(song[2], font, g, x1, y1 + count * 2, 1, Color.WHITE, Color.BLACK);
        g.translate(-x1, -y1 - count * 2);
        x1 = (390 - metrics.stringWidth(song[3])) / 2 + 0;
        Data.canvas.paintString(song[3], font, g, x1, y1 + count * 3, 1, Color.WHITE, Color.BLACK);
        g.translate(-x1, -y1 - count * 3);
        x1 = (390 - metrics.stringWidth(song[4])) / 2 + 0;
        Data.canvas.paintString(song[4], font, g, x1, y1 + count * 4, 1, Color.WHITE, Color.BLACK);
        g.translate(-x1, -y1 - count * 4);
        /*FontMetrics metrics = g.getFontMetrics(font);
        for (String song : Data.songList) { // 到时候改成待展示的歌曲名单列表
            x1 = (390 - metrics.stringWidth(song)) / 2 + 0;
            Data.canvas.paintString(song, font, g, x1, y1, 1, Color.WHITE, Color.BLACK);
            g.translate(-x1, -y1);
            y1 += 130;
        }*/
//        x1 = (390 - metrics.stringWidth(Data.songList.get(0))) / 2 + 0;
//        Data.canvas.paintString(Data.songList.get(0), font, g, 0, -10, 1, Color.WHITE, Color.BLACK);
        /*g.translate(-x1, -y1);
        System.out.println(x1 + " " + y1);*/
        /*for (int i = 0; i < Data.songList.size(); i++) { // 到时候改成待展示的歌曲名单列表
            System.out.println(i);
            String song = Data.songList.get(i);
            x1 = (390 - metrics.stringWidth(song)) / 2 + 0;
            Data.canvas.paintString(song, font, g, x1, y1, 1, Color.WHITE, Color.BLACK);
            g.translate(-x1, -y1);
            y1 += 130;
        }*/
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
    public void run() {
        if (way == 2) {
            while(true){
                if(y >= 122){
                    y = -8;
                    y1 = 60;
                    break;
                }
                else{
                    y = y + 5;
                    y1 = y1 + 5;
                }
                this.repaint();
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Data.changeSongList(2, 0);
        } else if (way == 1) {
            while(true){
                if(y <= -138){
                    y = -8;
                    y1 = 60;
                    break;
                }
                else{
                    y = y - 5;
                    y1 = y1 - 5;
                }
                this.repaint();
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Data.changeSongList(1, 0);
        }

    }
}
