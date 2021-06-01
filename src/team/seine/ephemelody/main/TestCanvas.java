package team.seine.ephemelody.main;

import team.seine.ephemelody.utils.Load;

import javax.swing.*;
import java.awt.*;

public class TestCanvas extends JPanel {
    public Image testImg;
    public TestCanvas() {
        setBounds(0, 0, 1200, 700);
        testImg = Load.image("home/背景.png");
    }
    public void paint(Graphics g) {
        g.drawImage(testImg, 0, 0, null);
    }
}
