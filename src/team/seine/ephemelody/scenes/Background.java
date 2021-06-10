package team.seine.ephemelody.scenes;

import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.utils.Load;

import javax.swing.*;
import java.awt.*;

public class Background extends JPanel implements Scenes{

    public Image backgroundImg;
    public Image menuImg;

    /**
     * 构造函数
     */
    public Background() {
        setBounds(0, 0, Data.WIDTH, Data.HEIGHT);
        setVisible(true);
        setLayout(null);
        backgroundImg = Load.image("home/背景.png");
        menuImg = Load.image("home/菜单.png");
        /*button = new JButton("da");
        button.setBounds(100, 100, 100, 100);
        add(button);*/
    }

    /**
     * 绘画初始背景
     * @param g 图形
     */
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
