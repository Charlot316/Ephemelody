package team.seine.ephemelody.scenes;

import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.utils.Load;

import javax.swing.*;
import java.awt.*;

public class End extends JPanel implements Scenes {
    public Image backgroundImg;
    public Image menuImg;
    public End() {
        setBounds(0, 0, Data.WIDTH, Data.HEIGHT);
        setVisible(true);
        setLayout(null);
        backgroundImg = Load.image("home/背景.png");
        menuImg = Load.image("home/菜单.png");
    }
    public void paint(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, null);
        g.drawImage(menuImg, 0, 0, null);
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
}

