package team.seine.ephemelody.main;

import team.seine.ephemelody.scenes.Background;
import team.seine.ephemelody.scenes.Home;
import team.seine.ephemelody.scenes.Scenes;

import javax.swing.*;
import java.awt.*;

// 画布类
public class Canvas extends JLayeredPane {
    Scenes bgScenes = null;
    JPanel nowScenes = null;
    public Canvas() {
        bgScenes = new Background();
        nowScenes = new Home();
        add((Background) bgScenes, new Integer(0));
        add(nowScenes, new Integer(1));
        setVisible(true);
    }

    public void paint(Graphics g) {
        // 绘制当前场景
        bgScenes.draw(g);
    }
}

