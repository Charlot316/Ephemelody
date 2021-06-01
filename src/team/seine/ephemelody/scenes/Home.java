package team.seine.ephemelody.scenes;

import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.utils.Load;

import javax.swing.*;
import java.awt.*;

public class Home extends JPanel{
    public Image ratingButton;
    public Image setupButton;
    public Image setupBackButton;
    public Image playButton;
    public Image homeButton;
    public Image easyButton;
    public Image normalButton;
    public Image difficultButton;
    public Image upButton;
    public Image downButton;
    public Image song1;
    public Home() {
        setBounds(0, 0, Data.WIDTH, Data.HEIGHT);
        setLayout(null);
        ratingButton = Load.image("home/潜力值_6.png");
        setupButton = Load.image("home/设置.png");
        setupBackButton = Load.image("home/设置_未选中.png");
        playButton = Load.image("home/start.png");
        homeButton = Load.image("home/主页.png");
        easyButton = Load.image("home/简单.png");
        normalButton = Load.image("home/普通.png");
        difficultButton = Load.image("home/困难.png");
        upButton = Load.image("home/up.png");
        downButton = Load.image("home/down.png");
        song1 = Load.image("home/song1.png");
        setBackground(null);
        setOpaque(false);
    }

    public void paint(Graphics g) {
        g.drawImage(song1, Data.WIDTH / 2, 100, null);
        g.drawImage(easyButton, Data.WIDTH / 2 - 140, 580, null);
        g.drawImage(normalButton, Data.WIDTH / 2 + 140, 580, null);
        g.drawImage(difficultButton, Data.WIDTH / 2 + 420, 580, null);
        g.drawImage(playButton, Data.WIDTH / 2, 750, null);
        g.drawImage(upButton, 100, 60, null);
        g.drawImage(downButton, 100, 800, null);
        g.drawImage(ratingButton, Data.WIDTH / 2 - 60, -16, null);
        g.drawImage(setupBackButton, 900, 0, null);
        g.drawImage(setupButton, 934, 8, null);
        g.drawImage(homeButton, 1050, 0, null);
    }
}
