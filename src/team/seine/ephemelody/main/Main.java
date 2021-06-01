package team.seine.ephemelody.main;

import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.scenes.Background;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Ephemelody");
        Data.init();
        frame.setVisible(true);
        Data.canvas = new Canvas();
//        Background background = new Background();
        frame.setContentPane(Data.canvas);
//        frame.add(background);
        frame.setSize(Data.WIDTH, Data.HEIGHT);
        // 窗口居中显示
        frame.setLocationRelativeTo(frame.getOwner());
        // 窗口关闭时结束程序
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
