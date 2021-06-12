package team.seine.ephemelody.utils;

public class Rect {
    /**
     *  判断点 (x,y) 是否在矩形 (rx, ry, rx+width, ry+height) 内部
     * @param x 鼠标的横坐标
     * @param y 鼠标的纵坐标
     * @param rx 所在矩形区域的左上角点的横坐标
     * @param ry 所在矩形区域的左上角点的纵坐标
     * @param width 矩形区域的宽度
     * @param height 矩形区域的高度
     * @return bool值，true则在区域内，false则在区域外
     */
    public static boolean isInternal(int x, int y, int rx, int ry, int width, int height) {
        int cx = x - rx, cy = y - ry;
        return cx > 0 && cy > 0 && cx < width && cy < height;
    }
}
