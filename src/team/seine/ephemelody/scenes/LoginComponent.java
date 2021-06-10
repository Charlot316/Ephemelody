package team.seine.ephemelody.scenes;

import team.seine.ephemelody.data.Data;

import javax.swing.*;
import java.awt.*;

public class LoginComponent extends JPanel implements Scenes{
    public static JTextField usernameField;
    public static JPasswordField passwordField;
    private static LoginComponent loginComponent=new LoginComponent();
    private LoginComponent(){
    }
    public static LoginComponent getLoginComponent() {
        loginComponent.setLayout(null);
        loginComponent.setBounds(0, 0, Data.WIDTH, Data.HEIGHT);
        loginComponent.setVisible(true);
        loginComponent.setOpaque(false);
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
        loginComponent.add(usernameField);
        loginComponent.add(passwordField);
        return loginComponent;
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
