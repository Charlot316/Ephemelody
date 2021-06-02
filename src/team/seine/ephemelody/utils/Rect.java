package team.seine.ephemelody.utils;

public class Rect {
    /* 判断点 (x,y) 是否在矩形 (rx, ry, rx+width, ry+height) 内部 */
    public static boolean isInternal(int x, int y, int rx, int ry, int width, int height) {
        int cx = x - rx, cy = y - ry;
        return cx > 0 && cy > 0 && cx < width && cy < height;
    }
}
