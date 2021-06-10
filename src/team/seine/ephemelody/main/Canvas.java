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
    PlayInterface tempPlayInterface=null;

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

    public void switchScenes(String name, @Nullable RecordTemp... recordTemps) {
        switch (name) {
            case "Home":
//            frame.addKeyListener(new OnKeyEvent());
                System.out.println("Home被调用了");
                End.isRemoved.set(1);
                this.removeAll();
                this.add(Background.getTheBackground(), new Integer(0));
                this.add(Home.getHome(), new Integer(1));
                this.add(MenuOption.getMenuOption(), new Integer(2));
                this.add(ChooseSong.getChooseSong(), new Integer(3));
                break;
            case "End":
                Home.isRemoved.set(1);
                MenuOption.isRemoved.set(1);
                this.removeAll();
                this.add(Background.getTheBackground(), new Integer(0));
//            this.add((Home) secondScenes, new Integer(1));
                this.add(End.getEnd(recordTemps[0]), new Integer(1));
                break;
            case "PlayInterface":
                Home.isRemoved.set(1);
                MenuOption.isRemoved.set(1);
                End.isRemoved.set(1);
                //    this.nowScenes =
//            this.secondScenes =  new Track(0, 1,'c', 1230, 2230, 0.5, 0.06, 255, 160, 160);
                this.removeAll();
                tempPlayInterface=PlayInterface.getPlayInterface(Data.songId, Data.difficulty);
                this.add(tempPlayInterface, new Integer(0));
                for (Track i : PlayInterface.allTracks) {
                    this.add(i, new Integer(i.id + 1));
                }
                this.add(PlayInterface.displayer, new Integer(PlayInterface.allTracks.size() + 1));
                new Thread(tempPlayInterface).start();
//            this.add((PlayInterface) nowScenes, new Integer(1));
                break;
            case "Login":
//            System.out.println("1");
//            this.removeAll();
//            this.remove((ChooseSong) thirdScenes);
                this.add(Login.getLogin(), new Integer(4));
                this.add( LoginComponent.getLoginComponent(), new Integer(5));
                break;
            case "SetUp":
                this.add(SetUp.getSetUp(), new Integer(5));
                break;
        }
//        System.out.println("前面都执行完了");
    }

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
    class OnKeyEvent extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            try {
                tempPlayInterface.onKeyDown(e.getKeyCode());
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        public void keyReleased(KeyEvent e) {
            tempPlayInterface.onKeyUp(e.getKeyCode());
        }
    }

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

}

