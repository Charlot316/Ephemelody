package team.seine.ephemelody.data;

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
    public static List<String> songList; // 歌曲列表，存放歌曲名
    public static int offset = 0, noteVolume = 1, volume = 1; // 存放设置中的偏移、音效、音量
    public static double noteSpeed = 5.0; // 存放设置中的流速
    public static AtomicInteger[] keyStatus = new AtomicInteger[200];
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
        //songList = Arrays.asList("最炫民族风", "你是我的小呀小苹果", "怎么爱你都不嫌多", "我到底在干啥", "这就是最后一首歌");

    }

}
