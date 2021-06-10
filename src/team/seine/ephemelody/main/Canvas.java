package team.seine.ephemelody.main;

import com.sun.istack.internal.Nullable;
import team.seine.ephemelody.playinterface.RecordTemp;
import team.seine.ephemelody.playinterface.Track;
import team.seine.ephemelody.scenes.*;
import team.seine.ephemelody.data.Data;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.GlyphVector;
import java.sql.SQLException;

// 画布类
public class Canvas extends JLayeredPane{
    public JFrame frame;
    public KeyListener keyListener;
    Scenes firstScenes = null;
    Scenes secondScenes = null;
    Scenes thirdScenes = null;
    Scenes loginComScenes = null; // 登录界面的组件所在页面
    Scenes menuOptionScenes = null;
    Scenes chooseSongScenes = null;

    /**
     * 初始化画板类
     * @param frame 当前的JFrame的对象
     * @throws SQLException 抛出SQLException
     */
    public Canvas(JFrame frame) throws SQLException {
        /*nowScenes = new Home();
        bgScenes = new Background();*/
        /*background = new Background();
        home = new Home();*/
        this.frame = frame;
        switchScenes("Home");
        setVisible(true);
        frame.addKeyListener(new OnKeyEvent());
//        nowScenes = new PlayInterface(1, 1);
    }

    /**
     * 切换场景页面
     * @param name 切换的页面名
     * @param recordTemps 可选参数，切换至结算页面时传入当前游戏的数据
     */
    public void switchScenes(String name, @Nullable RecordTemp... recordTemps) {
        if (name.equals("Home")) {
//            frame.addKeyListener(new OnKeyEvent());
            this.firstScenes = new Background();
            this.secondScenes = new Home();
            this.menuOptionScenes = new MenuOption();
            this.chooseSongScenes = new ChooseSong();
            this.removeAll();
            this.add((Background) firstScenes, new Integer(0));
            this.add((Home) secondScenes, new Integer(1));
            this.add((MenuOption) menuOptionScenes, new Integer(2));
            this.add(((Home) secondScenes).chooseSong, new Integer(3));
        } else if (name.equals("End")) {
            this.firstScenes = new Background();
//            this.secondScenes = new Home();
            this.thirdScenes = new End(recordTemps[0]);
            this.menuOptionScenes = new MenuOption();
            this.removeAll();
            this.add((Background) firstScenes, new Integer(0));
//            this.add((Home) secondScenes, new Integer(1));
            this.add((MenuOption) menuOptionScenes, new Integer(1));
            this.add((End) thirdScenes, new Integer(2));
        } else if (name.equals("PlayInterface")) {
        //    this.nowScenes =
            this.firstScenes = new PlayInterface(Data.songId, Data.difficulty);
//            this.secondScenes =  new Track(0, 1,'c', 1230, 2230, 0.5, 0.06, 255, 160, 160);
            this.removeAll();
            this.add((PlayInterface)firstScenes, new Integer(0));

            for(Track i:((PlayInterface) firstScenes).allTracks){
                this.add(i,new Integer(i.id+1));
            }
            this.add(((PlayInterface) firstScenes).displayer,new Integer(((PlayInterface) firstScenes).allTracks.size()+1));
            new Thread((PlayInterface) firstScenes).start();
//            this.add((PlayInterface) nowScenes, new Integer(1));
        } else if (name.equals("Login")) {
//            System.out.println("1");
            this.thirdScenes = new Login();
            this.loginComScenes = new LoginComponent();
//            this.removeAll();
//            this.remove((ChooseSong) thirdScenes);
            this.add((Login) thirdScenes, new Integer(4));
            this.add((LoginComponent) loginComScenes, new Integer(5));
        } else if (name.equals("SetUp")) {
            this.thirdScenes = new SetUp();
            this.add((SetUp) thirdScenes, new Integer(5));
        }
//        System.out.println("前面都执行完了");
    }

    /**
     * 绘画带有描边的字符串
     * @param str 字符串
     * @param f 字体
     * @param g 图形
     * @param x 横坐标
     * @param y 纵坐标
     * @param width 描边宽度
     * @param color1 填充颜色
     *
     * @param color2 描边颜色
     */
    public void paintString(String str, Font f, Graphics g, Integer x, Integer y, Integer width, Color color1, Color color2) {
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
        gg.setColor(color1);
        gg.fill(shape);
        gg.setColor(color2);
        gg.setStroke(new BasicStroke(width));
        gg.draw(shape);
    }

    class OnKeyEvent extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            firstScenes.onKeyDown(e.getKeyCode());
        }
        public void keyReleased(KeyEvent e) {
            firstScenes.onKeyUp(e.getKeyCode());
        }
    }

    /**
     * 绘画给定位置居中的字符串
     * @param g 图形
     * @param text 字符串
     * @param width1 区域宽度
     * @param width2 起始横坐标
     * @param font 字体
     * @param y 纵坐标
     */
    public void drawCenteredString(Graphics g, String text, int width1, int width2, Font font, int y) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = (width1 - metrics.stringWidth(text)) / 2 + width2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)// Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }

    /**
     * 绘画居中且带有描边的字符串
     * @param g 图形
     * @param text 字符串
     * @param width1 区域宽度
     * @param width2 起始横坐标
     * @param width 描边宽度
     * @param font 字体
     * @param y 纵坐标
     * @param color1 填充颜色
     * @param color2 描边颜色
     */
    public void drawCenteredStringByOutline(Graphics g, String text, int width1, int width2, int width, Font font, int y, Color color1, Color color2) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = (width1 - metrics.stringWidth(text)) / 2 + width2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)// Set the font
        g.setFont(font);
        // Draw the String
//        g.drawString(text, x, y);
        paintString(text, font, g, x, y, width, color1, color2);
        g.translate(-x, -y);
    }
}

