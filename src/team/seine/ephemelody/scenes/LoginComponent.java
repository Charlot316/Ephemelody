package team.seine.ephemelody.scenes;

import team.seine.ephemelody.data.Data;

import javax.swing.*;
import java.awt.*;

public class LoginComponent extends JPanel implements Scenes{
    public static JTextField usernameField;
    public static JPasswordField passwordField;

    /**
     * 登录组件构造函数，添加文本框等组件
     */
    public LoginComponent() {
        setLayout(null);
        setBounds(0, 0, Data.WIDTH, Data.HEIGHT);
        setVisible(true);
        setOpaque(false);
        usernameField = new JTextField();
        usernameField.setBounds(230, 335, 200, 50);
        usernameField.setFont(new Font("宋体", Font.PLAIN, 30));
        usernameField.setForeground(Color.WHITE);
        usernameField.setOpaque(false);
        passwordField = new JPasswordField();
        passwordField.setBounds(230, 425, 200, 50);
        passwordField.setFont(new Font("宋体", Font.PLAIN, 30));
        passwordField.setForeground(Color.WHITE);
        passwordField.setOpaque(false);
        add(usernameField);
        add(passwordField);
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
