package team.seine.ephemelody.scenes;

import com.sun.imageio.plugins.common.ImageUtil;
import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.utils.Load;

import javax.swing.*;
import java.awt.*;

public class Background extends Panel implements Scenes{

    public Image backgroundImg;
    public Image menuImg;
    public Image song1;
    public ImageIcon icon;
    public JButton button;

    public Background() {
        backgroundImg = Load.image("home/背景.png");
        menuImg = Load.image("home/菜单.png");
        song1 = Load.image("home/歌曲1.png");
        icon = new ImageIcon("D:\\资料\\2 大二下\\Java\\大作业\\seine\\src\\resources\\img\\home\\困难.png");
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

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, null);
        g.drawImage(menuImg, 0, 0, null);
        g.drawImage(song1, Data.WIDTH / 2, 80, null);
    }
}
