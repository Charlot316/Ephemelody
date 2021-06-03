package team.seine.ephemelody.main;

import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.scenes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.font.GlyphVector;

// 画布类
public class Canvas extends JLayeredPane{
    Scenes firstScenes = null;
    Scenes secondScenes = null;
    Scenes thirdScenes = null;
    Scenes loginComScenes = null; // 登录界面的组件所在页面
    Background background = null;
    Home home = null;
    public Canvas() {
        /*nowScenes = new Home();
        bgScenes = new Background();*/
        /*background = new Background();
        home = new Home();*/
        switchScenes("Home");
        setVisible(true);
    }

    public void switchScenes(String name) {
        if (name.equals("Home")) {
            this.firstScenes = new Background();
            this.secondScenes = new Home();
            this.removeAll();
            this.add((Background) firstScenes, new Integer(0));
            this.add((Home) secondScenes, new Integer(1));
        } else if (name.equals("End")) {
            this.firstScenes = new Background();
            this.secondScenes = new Home();
            this.thirdScenes = new End();
            this.removeAll();
            this.add((Background) firstScenes, new Integer(0));
            this.add((Home) secondScenes, new Integer(1));
            this.add((End) thirdScenes, new Integer(2));
        } else if (name.equals("PlayInterface")) {
        //    this.nowScenes =
            this.firstScenes = new PlayInterface(1, 1);
            this.removeAll();
            this.add((PlayInterface)firstScenes, new Integer(0));
            this.repaint();
            ((PlayInterface) firstScenes).display();

//            this.add((PlayInterface) nowScenes, new Integer(1));
        } else if (name.equals("Login")) {
//            System.out.println("1");
            this.thirdScenes = new Login();
            this.loginComScenes = new LoginComponent();
//            this.removeAll();
            this.add((Login) thirdScenes, new Integer(3));
            this.add((LoginComponent) loginComScenes, new Integer(4));
        } else if (name.equals("SetUp")) {
            this.thirdScenes = new SetUp();
            this.add((SetUp) thirdScenes, new Integer(3));
        }
//        System.out.println("前面都执行完了");
    }

    public void paintString(String str, Graphics g, Integer x, Integer y, Integer size) {
        Font f = new Font("黑体", Font.BOLD, size);
        GlyphVector v = f.createGlyphVector(getFontMetrics(f).getFontRenderContext(), str);
        Shape shape = v.getOutline();

        Rectangle bounds = shape.getBounds();

        Graphics2D gg = (Graphics2D) g;
        gg.translate(
                x, y
                /*(getWidth() - bounds.width) / 2 - bounds.x,
                (getHeight() - bounds.height) / 2 - bounds.y*/
        );
//        gg.setClip(x, y, x, y);
        gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gg.setColor(Color.WHITE);
        gg.fill(shape);
        gg.setColor(new Color(119, 97, 125));
        gg.setStroke(new BasicStroke(3));
        gg.draw(shape);
    }


}

