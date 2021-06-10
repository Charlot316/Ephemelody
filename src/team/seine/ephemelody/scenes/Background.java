package team.seine.ephemelody.scenes;

import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.utils.Load;

import javax.swing.*;
import java.awt.*;

public class Background extends JPanel implements Scenes{

    public static Image backgroundImg;
    public static Image menuImg;
//    public JButton button;
    private static Background background=new Background();
    private Background(){

    }
    public static Background getTheBackground() {
        background.setBounds(0, 0, Data.WIDTH, Data.HEIGHT);
        background.setVisible(true);
        background.setLayout(null);
        backgroundImg = Load.image("home/背景.png");
        menuImg = Load.image("home/菜单.png");
        /*button = new JButton("da");
        button.setBounds(100, 100, 100, 100);
        add(button);*/
        return background;
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
