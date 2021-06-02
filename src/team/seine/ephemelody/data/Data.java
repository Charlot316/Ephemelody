package team.seine.ephemelody.data;

import team.seine.ephemelody.utils.Load;
import team.seine.ephemelody.main.Canvas;
import java.awt.*;

public class Data {
    public static final int WIDTH = 1286, HEIGHT = 965, FPS = 100;
    // 显示游戏画面的面板
    public static Canvas canvas;

    private static Image[] setupButton;
    private static Image[] ratingButton;
    private static Image[] difficultyButton;
    private static Image backgroundImg, startButton;

    public static void init() {
        setupButton = new Image[]{
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
        startButton = Load.image("home/开始游戏.png");

    }
}
