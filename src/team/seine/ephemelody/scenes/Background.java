package team.seine.ephemelody.scenes;

import com.sun.imageio.plugins.common.ImageUtil;
import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.utils.Load;

import javax.swing.*;
import java.awt.*;

public class Background extends JPanel{

    public Image backgroundImg;
    public Image menuImg;
//    public JButton button;
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

    public void paint(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, null);
        g.drawImage(menuImg, 0, 0, null);
    }

}
