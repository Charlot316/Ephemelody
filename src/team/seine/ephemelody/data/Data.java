package team.seine.ephemelody.data;

import database.Entity.Player;
import team.seine.ephemelody.utils.Load;
import team.seine.ephemelody.main.Canvas;

import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.font.GlyphVector;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Data {
    public static final int WIDTH = 1286, HEIGHT = 965, FPS = 100;
    public static Canvas canvas;
    public static List<String> songList; // 歌曲列表，存放歌曲名，用来实现动画效果
    public static List<String> realSongList; // 真正的歌曲列表，不会改变
    public static int offset = 0, noteVolume = 1, volume = 1; // 存放设置中的偏移、音效、音量
    public static double noteSpeed = 5.0; // 存放设置中的流速
    public static AtomicInteger[] isPressed =new AtomicInteger[200];
    public static AtomicInteger[] isReleased =new AtomicInteger[200];
    public static AtomicInteger[] isUsing = new AtomicInteger[200];
    public static AtomicInteger[] keyStatus = new AtomicInteger[200];
    public static int difficulty;
    public static int chooseSongId;
    public static int songId;
    public static Player nowPlayer;
    public static int frontSong=0;
    /*private static Image[] setupButton;
    private static Image[] ratingButton;
    private static Image[] difficultyButton;
    private static Image backgroundImg, startButton;*/

    public static void init() {
        /*setupButton = new Image[]{
                Load.image("home/设置.png"), Load.image("home/设置_按下.png"),
                Load.image("home/设置_未选中.png"), Load.image("home/设置_鼠标悬停.png")
        };
        difficultyButton = new Image[]{
                Load.image("home/简单.png"), Load.image("home/简单_按下.png"),
                Load.image("home/普通.png"), Load.image("home/普通_按下.png"),
                Load.image("home/困难.png"), Load.image("home/困难_按下.png")
        };
        ratingButton = new Image[]{
                Load.image("home/潜力值_0.png"), Load.image("home/潜力值_1.png"),
                Load.image("home/潜力值_2.png"), Load.image("home/潜力值_3.png"),
                Load.image("home/潜力值_4.png"), Load.image("home/潜力值_5.png"),
                Load.image("home/潜力值_6.png"), Load.image("home/rating_down.png"),
                Load.image("home/rating_keep.png"), Load.image("home/rating_up.png"),
        };
        backgroundImg = Load.image("home/背景.png");
        startButton = Load.image("home/开始游戏.png");*/

        songList = Arrays.asList("第四首歌", "第五首歌", "第一首歌", "第二首歌", "第三首歌");
        realSongList = Arrays.asList("新手引导", "熱愛発覚中"," world.excute(me);", "第2首歌","第3首歌", "第4首歌","第5首歌", "第6首歌");
        readSongList();
        songId = 2;
    }
    public static void readSongList(){
        for(int index=frontSong, i=0;i<5;i++,index++){
            if(index>=realSongList.size()) index=0;
            songList.set(i,realSongList.get(index));
        }
    }
    public static void changeSongList(int way, int chooseSong) {
        if (way == 1) {
            frontSong++;
            if(frontSong>=realSongList.size()) frontSong=0;
            readSongList();

//            String song = songList.get(0);
//            for (int i = 0; i < 4; i++) {
//                songList.set(i, songList.get(i + 1));
//            }
//            songList.set(4, song);
        } else if (way == 2) {
            frontSong--;
            if(frontSong<0) frontSong=realSongList.size()-1;
            readSongList();
//            String song = songList.get(4);
//            for (int i = 4; i > 0; i--) {
//                songList.set(i, songList.get(i - 1));
//            }
//            songList.set(0, song);
        } /*else if (way == 3) {

        }*/
    }

}
