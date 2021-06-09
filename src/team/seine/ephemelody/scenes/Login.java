package team.seine.ephemelody.scenes;

import database.Entity.Player;
import database.PlayerController;
import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.utils.Load;
import team.seine.ephemelody.utils.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class Login extends JPanel implements Scenes, MouseMotionListener, MouseListener {
    String username;
    String password;
    public Image loginBackground;
    public Image[] loginButton;
    public Integer loginButtonStatus;
    public Login() {
        setBounds(0, 0, Data.WIDTH, Data.HEIGHT);
        setVisible(true);
        setOpaque(false);
        loginButtonStatus = 0;
        loginBackground = Load.image("login/登录背景.png");
        loginButton = new Image[] {
                Load.image("login/登录或注册.png"), Load.image("login/登录或注册_鼠标悬停.png"), Load.image("login/登录或注册_按下.png")
        };
//        new Home.UpdateUI().start();
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
        loginButtonStatus = 0;
        int buttonStruts = struts == Scenes.MOUSE_MOVED ? 1 : struts == Scenes.MOUSE_DOWN ? 2 : 0;
        if(Rect.isInternal(x, y, 100, 650, 393, 120)) {
            loginButtonStatus = buttonStruts;
            if (struts == Scenes.MOUSE_DOWN) {
                username = LoginComponent.usernameField.getText();
                password = String.valueOf(LoginComponent.passwordField.getPassword());
                System.out.println(username + "---" + password);
                Player player = PlayerController.selectPlayerById(username);
                if (player != null) {
                    if (player.getPassword().equals(password)) {
                        System.out.println("登录成功");
                        Data.nowPlayer = PlayerController.selectPlayerById(username);
                        Data.canvas.frame.setFocusable(true);
                        Data.canvas.switchScenes("Home"); // 到时候改成如果登录成功，用户名显示出来

                    } else {
                        System.out.println("用户名已存在, 注册失败");
                    }
                } else {
                    PlayerController.insertPlayer(username, password);
                    System.out.println("注册成功");
                    Data.nowPlayer = PlayerController.selectPlayerById(username);
                    Data.canvas.frame.setFocusable(true);
                    Data.canvas.switchScenes("Home"); // 到时候改成如果登录成功，用户名显示出来
                }

            }
        } else if (Rect.isInternal(x, y, 757, 0, Data.WIDTH - 757, Data.HEIGHT)) {
            if (struts == Scenes.MOUSE_DOWN) {
                Data.canvas.switchScenes("Home"); // 直接返回
            }
        }
    }

    public void paint(Graphics g) {
        g.drawImage(loginBackground, 0, 0, null);
        g.drawImage(loginButton[loginButtonStatus], 100, 650, null);
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

}
