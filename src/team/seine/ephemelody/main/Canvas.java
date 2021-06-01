package team.seine.ephemelody.main;

import team.seine.ephemelody.scenes.Background;
import team.seine.ephemelody.scenes.Home;
import team.seine.ephemelody.scenes.Scenes;

import javax.swing.*;
import java.awt.*;

// 画布类
public class Canvas extends JLayeredPane {
    Background background = null;
    Home home = null;
    public Canvas() {
        background = new Background();
        home = new Home();
        add(background, new Integer(0));
        add(home, new Integer(1));
        setVisible(true);
    }

}

