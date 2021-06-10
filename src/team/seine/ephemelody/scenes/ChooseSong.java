package team.seine.ephemelody.scenes;

import database.Entity.Song;
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
    public Song[] song;
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
        song = new Song[] {
                Data.songList.get(0), Data.songList.get(1), Data.songList.get(2), Data.songList.get(3),  Data.songList.get(4)
        };
        g.drawImage(songNameImg, x, y - count, null);
        g.setFont(new Font("黑体", Font.BOLD, 45));
        g.drawImage(songNameImg, x, y, null);
        g.drawImage(songNameImg, x, y + count, null);
        g.drawImage(songNameImg, x, y + count * 2, null);
        g.drawImage(songNameImg, x, y + count * 3, null);
        g.drawImage(songNameImg, x, y + count * 4, null);
        g.drawImage(songNameImg, x, y + count * 5, null);

        Font font = new Font("黑体", Font.BOLD, 45);
        FontMetrics metrics = g.getFontMetrics(font);
        x1 = (390 - metrics.stringWidth(song[0].name)) / 2 + 0;
        Data.canvas.paintString(song[0].name, font, g, x1, y1, 1, Color.WHITE, Color.BLACK);
        g.translate(-x1, -y1);
        x1 = (390 - metrics.stringWidth(song[1].name)) / 2 + 0;
        Data.canvas.paintString(song[1].name, font, g, x1, y1 + count, 1, Color.WHITE, Color.BLACK);
        g.translate(-x1, -y1 - count);
        x1 = (390 - metrics.stringWidth(song[2].name)) / 2 + 0;
        Data.canvas.paintString(song[2].name, font, g, x1, y1 + count * 2, 1, Color.WHITE, Color.BLACK);
        g.translate(-x1, -y1 - count * 2);
        x1 = (390 - metrics.stringWidth(song[3].name)) / 2 + 0;
        Data.canvas.paintString(song[3].name, font, g, x1, y1 + count * 3, 1, Color.WHITE, Color.BLACK);
        g.translate(-x1, -y1 - count * 3);
        x1 = (390 - metrics.stringWidth(song[4].name)) / 2 + 0;
        Data.canvas.paintString(song[4].name, font, g, x1, y1 + count * 4, 1, Color.WHITE, Color.BLACK);
        g.translate(-x1, -y1 - count * 4);
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
        System.out.println("ChooseSong线程开始了");
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
        System.out.println("ChooseSong线程结束了");
    }
}
