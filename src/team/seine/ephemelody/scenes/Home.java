package team.seine.ephemelody.scenes;

import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.utils.Load;

import javax.swing.*;
import java.awt.*;

public class Home extends JPanel{
    public JButton ratingButton;
    public JButton setupButton;
    public JButton playButton;
    public JButton homeButton;
    public JButton easyButton;
    public JButton normalButton;
    public JButton difficultButton;
    public ImageIcon ratingIcon;
    public ImageIcon setupIcon;
    public ImageIcon playIcon;
    public ImageIcon homeIcon;
    public ImageIcon easyIcon;
    public ImageIcon normalIcon;
    public ImageIcon difficultIcon;

    public Home() {
        ratingIcon = new ImageIcon("/resources/home/rating_6.png");
        setupIcon = new ImageIcon("/resources/home/设置.png");
        playIcon = new ImageIcon("/resources/home/开始游戏.png");
        homeIcon = new ImageIcon("/resources/home/主页.png");
        easyIcon = new ImageIcon("/resources/home/简单.png");
        normalIcon = new ImageIcon("/resources/home/普通.png");
        difficultIcon = new ImageIcon("/resources/home/困难.png");
        ratingButton = new JButton(ratingIcon);
        setupButton = new JButton(setupIcon);
        playButton = new JButton(playIcon);
        homeButton = new JButton(homeIcon);
        easyButton = new JButton(easyIcon);
        normalButton = new JButton(normalIcon);
        difficultButton = new JButton(difficultIcon);
        easyButton.setBounds(Data.WIDTH / 2, 500, 202, 125);
        add(easyButton);
    }
    /*@Override
    public void onKeyDown(int keyCode) {

    }

    @Override
    public void onKeyUp(int keyCode) {

    }

    @Override
    public void onMouse(int x, int y, int struts) {

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage()
    }*/
}
