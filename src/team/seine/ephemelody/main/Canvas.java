package team.seine.ephemelody.main;

import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.scenes.Background;
import team.seine.ephemelody.scenes.End;
import team.seine.ephemelody.scenes.Home;
import team.seine.ephemelody.scenes.Scenes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

// 画布类
public class Canvas extends JLayeredPane{
    Scenes nowScenes = null;
    Scenes bgScenes = null;
    Background background = null;
    Home home = null;
    public Canvas() {
        nowScenes = new Home();
        bgScenes = new Background();
        /*background = new Background();
        home = new Home();*/
        switchScenes("Home");
        setVisible(true);
    }

    public void switchScenes(String name) {
        if (name.equals("Home")) {
            this.bgScenes = new Background();
            this.nowScenes = new Home();
            this.removeAll();
            this.add((Background) bgScenes, new Integer(0));
            this.add((Home) nowScenes, new Integer(1));
        } else if (name.equals("End")) {
            this.nowScenes = new End();
            this.removeAll();
            this.add((End) nowScenes, new Integer(1));
        }
    }



}

