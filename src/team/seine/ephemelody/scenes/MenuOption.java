package team.seine.ephemelody.scenes;

import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.utils.Load;
import team.seine.ephemelody.utils.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MenuOption extends JPanel implements Scenes, MouseMotionListener, MouseListener {
    int buttonRatingStatus = 0, buttonSetUpBackStatus = 0, buttonLoginStatus = 0;
    public Image[] ratingButton;
    public Image setupButton;
    public Image[] setupBackButton;
    public Image[] loginButton;
    public double potential;
    public MenuOption() {
        setBounds(0, 0, Data.WIDTH, 93);
//        setLayout(null);
        setOpaque(false);
        setVisible(true);

        ratingButton = new Image[]{
                Load.image("home/潜力值_0.png"), Load.image("home/潜力值_1.png"),
                Load.image("home/潜力值_2.png"), Load.image("home/潜力值_3.png"),
                Load.image("home/潜力值_4.png"), Load.image("home/潜力值_5.png")
        };
        setupButton = Load.image("home/设置.png");
        setupBackButton = new Image[]{
                Load.image("home/设置_未选中.png"), Load.image("home/设置_鼠标悬停.png"), Load.image("home/设置_按下.png")
        };
        loginButton = new Image[] {
                Load.image("home/登录.png"), Load.image("home/登录_鼠标悬停.png"), Load.image("home/登录_按下.png")
        };
        if (Data.nowPlayer != null) {
            this.potential = Data.nowPlayer.getPotential();
            if (potential < 8){
                buttonRatingStatus = 0;
            } else if (potential >= 8 && potential < 9) {
                buttonRatingStatus = 1;
            } else if (potential >= 9 && potential < 10) {
                buttonRatingStatus = 2;
            } else if (potential >= 10 && potential < 11) {
                buttonRatingStatus = 3;
            } else if (potential >= 11 && potential < 12) {
                buttonRatingStatus = 4;
            } else if (potential >= 12 && potential <= 12.5) {
                buttonRatingStatus = 5;
            } else {
                buttonRatingStatus = 6;
            }
        }

        addMouseMotionListener(this);
        addMouseListener(this);
    }
    @Override
    public void onKeyDown(int keyCode) {

    }

    @Override
    public void onKeyUp(int keyCode) {

    }

    @Override
    public void onMouse(int x, int y, int struts) {
        if (buttonSetUpBackStatus != MOUSE_DOWN) {
            buttonSetUpBackStatus = 0;
        }
        buttonLoginStatus = 0;
        int buttonStruts = struts == Scenes.MOUSE_MOVED ? 1 : struts == Scenes.MOUSE_DOWN ? 2 : 0;

        if(Rect.isInternal(x, y, 900, 0, 98, 50)) {
            buttonSetUpBackStatus = buttonStruts;
            if(struts == Scenes.MOUSE_DOWN) {
                Data.canvas.switchScenes("SetUp");
            }
        } else if (Rect.isInternal(x, y, 1080, -3, 188, 69)) {
            buttonLoginStatus = buttonStruts;
            if(struts == Scenes.MOUSE_DOWN) {
                Data.canvas.switchScenes("Login");
//                System.exit(0);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        onMouse(e.getX(), e.getY(), Scenes.MOUSE_DOWN);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        onMouse(e.getX(), e.getY(), Scenes.MOUSE_MOVED);
    }

    public void paint(Graphics g) {
        g.drawImage(ratingButton[buttonRatingStatus], Data.WIDTH / 2 - 60, -16, null);
        g.drawImage(setupBackButton[buttonSetUpBackStatus], 900, 0, null);
        g.drawImage(setupButton, 934, 8, null);
        g.drawImage(loginButton[buttonLoginStatus], 1080, -3, null);
    }
}
