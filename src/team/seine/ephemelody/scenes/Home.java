package team.seine.ephemelody.scenes;

import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.utils.Load;

import javax.swing.*;
import java.awt.*;

public class Home extends JPanel{
    public Image ratingButton;
    public Image setupButton;
    public Image playButton;
    public Image homeButton;
    public Image easyButton;
    public Image normalButton;
    public Image difficultButton;
    public Image song1;
    public Home() {
        setBounds(0, 0, Data.WIDTH, Data.HEIGHT);
        setLayout(null);
        ratingButton = Load.image("home/潜力值_6.png");
        setupButton = Load.image("home/设置.png");
        playButton = Load.image("home/开始游戏.png");
        homeButton = Load.image("home/主页.png");
        easyButton = Load.image("home/简单.png");
        normalButton = Load.image("home/普通.png");
        difficultButton = Load.image("home/困难.png");
        song1 = Load.image("home/歌曲1.png");
        setBackground(null);
        setOpaque(false);
    }

    public void paint(Graphics g) {
        g.drawImage(song1, Data.WIDTH / 2, 80, null);
        g.drawImage(easyButton, Data.WIDTH / 2 - 160, 500, null);
        g.drawImage(normalButton, Data.WIDTH / 2 + 160, 500, null);
        g.drawImage(difficultButton, Data.WIDTH / 2 + 480, 500, null);
    }
}
