package team.seine.ephemelody.main;
import team.seine.ephemelody.data.Data;
import javax.swing.*;

public class TestMain {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Ephemelody");
        frame.setVisible(true);
        TestCanvas testCanvas = new TestCanvas();
        frame.add(testCanvas);
        frame.setSize(Data.WIDTH, Data.HEIGHT);
        // 窗口居中显示
        frame.setLocationRelativeTo(frame.getOwner());
        // 窗口关闭时结束程序
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
