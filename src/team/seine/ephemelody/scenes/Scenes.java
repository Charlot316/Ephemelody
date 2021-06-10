package team.seine.ephemelody.scenes;

import javax.swing.*;
import java.awt.*;

/**
 * 所有的场景都必须实现此接口
 */
public interface Scenes  {
    // 规定了鼠标的按下，弹起，悬浮和左中右键的按下和弹起
    static final int MOUSE_DOWN = 2, MOUSE_UP = 0, MOUSE_MOVED = 1, DOWN_LEFT = 10, DOWN_CENTER = 20, DOWN_RIGHT = 30, UP_LEFT = 11, UP_CENTER = 21, UP_RIGHT = 31;

    // 监听键盘按
    void onKeyDown(int keyCode);

    // 监听键盘弹起
    void onKeyUp(int keyCode);

    // 监听鼠标左键弹起
    void onMouse(int x, int y, int struts);

    /*// 绘制自身
    void draw(Graphics g);*/
}
