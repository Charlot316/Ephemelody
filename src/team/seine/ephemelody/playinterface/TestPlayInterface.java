package team.seine.ephemelody.playinterface;

import team.seine.ephemelody.scenes.PlayInterface;
import javax.swing.*;
import java.awt.*;

public class TestPlayInterface {
    public static JFrame Frame;

    public static void main(String[] args) {
        Frame = new JFrame("游玩界面");
        Frame.setSize(200, 200);
        Frame.setVisible(true);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PlayInterface playInterface = new PlayInterface(1, 1);
        playInterface.setOpaque(true);
        Frame.add(playInterface);
        Frame.setContentPane(playInterface);
    }
}
