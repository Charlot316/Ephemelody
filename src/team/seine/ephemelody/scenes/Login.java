package team.seine.ephemelody.scenes;

import database.Entity.Player;
import database.PlayerController;
import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.utils.Load;
import team.seine.ephemelody.utils.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JPanel implements Scenes, MouseMotionListener, MouseListener {
    String username;
    String password;
    public Image loginBackground;
    public Image[] loginButton;
    public Integer loginButtonStatus;
    public String message;
    public Login() {
        setBounds(0, 0, Data.WIDTH, Data.HEIGHT);
        setVisible(true);
        setOpaque(false);
        loginButtonStatus = 0;
        loginBackground = Load.image("login/登录背景.png");
        loginButton = new Image[] {
                Load.image("login/登录或注册.png"), Load.image("login/登录或注册_鼠标悬停.png"), Load.image("login/登录或注册_按下.png")
        };
        message = "";
        addMouseMotionListener(this);
        addMouseListener(this);
    }
    @Override
    public void onKeyDown(int keyCode) {

    }

    @Override
    public void onKeyUp(int keyCode) {

    }

    /**
     * 响应鼠标事件
     * @param x 鼠标所在横坐标
     * @param y 鼠标所在纵坐标
     * @param struts 鼠标状态
     */
    @Override
    public void onMouse(int x, int y, int struts) {
        loginButtonStatus = 0;
        int buttonStruts = struts == Scenes.MOUSE_MOVED ? 1 : struts == Scenes.MOUSE_DOWN ? 2 : 0;
        if(Rect.isInternal(x, y, 100, 650, 393, 120)) {
            loginButtonStatus = buttonStruts;
            if (struts == Scenes.MOUSE_DOWN) {
                username = LoginComponent.usernameField.getText();
                password = String.valueOf(LoginComponent.passwordField.getPassword());
                Player player = PlayerController.selectPlayerById(username);
                if (player != null) {
                    if (player.getPassword().equals(password)) {
                        message = "登录成功";
                        Data.nowPlayer = PlayerController.selectPlayerById(username);
                        Data.canvas.frame.setFocusable(true);
                        Home.isEnd = true;
                        Data.canvas.switchScenes("Home"); // 到时候改成如果登录成功，用户名显示出来
                    } else {
                        message = "密码错误，登录失败";
                        this.repaint();
                    }
                } else {
                    if (username.length() < 3 || password.length() < 3) {
                        message = "用户名与密码的位数不得小于3";
                        LoginComponent.usernameField.setText("");
                        LoginComponent.passwordField.setText("");
                        this.repaint();
                    } else {
                        PlayerController.insertPlayer(username, password);
                        message = "注册成功";
                        Data.nowPlayer = PlayerController.selectPlayerById(username);
                        Data.canvas.frame.setFocusable(true);
                        Home.isEnd = true;
                        Data.canvas.switchScenes("Home"); // 到时候改成如果登录成功，用户名显示出来
                    }

                }

            }
        } else if (Rect.isInternal(x, y, 757, 0, Data.WIDTH - 757, Data.HEIGHT)) {
            if (struts == Scenes.MOUSE_DOWN) {
                Data.canvas.switchScenes("Home"); // 直接返回
            }
        }
    }

    /**
     * 绘画登录界面
     * @param g 图形
     */
    public void paint(Graphics g) {
        g.drawImage(loginBackground, 0, 0, null);
        g.drawImage(loginButton[loginButtonStatus], 100, 650, null);
        g.setFont(new Font("黑体", Font.PLAIN, 40));
        g.setColor(Color.WHITE);
        g.drawString(message, 100, 200);
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
