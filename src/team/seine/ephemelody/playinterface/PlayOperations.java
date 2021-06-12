package team.seine.ephemelody.playinterface;

public class PlayOperations {
    public int trackId;
    public int type;
    public long startTime;
    public long endTime;
    public double endX;
    public double endWidth;
    public int endR;
    public int endG;
    public int endB;
    public String background;

    /**
     * Constructor of the operation
     * @param trackId 需要操作的轨道
     * @param type 操作类型 type: 1:移动 2:改变宽度 3: 变色 4:换背景
     * @param startTime 操作的起始时间
     * @param endTime 操作的终止时间
     * @param endX 平移的终点
     * @param endWidth 宽度改变的终点
     * @param endR 颜色R改变的终点
     * @param endG 颜色G改变的终点
     * @param endB 颜色B改变的终点
     * @param background 需要更换的背景的地址
     */
    public PlayOperations(int trackId, int type, long startTime, long endTime, double endX,double endWidth,int endR, int endG, int endB,String background) {
        this.trackId = trackId;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.endX = endX;
        this.endWidth=endWidth;
        this.endR = endR;
        this.endG = endG;
        this.endB = endB;
        this.background = background;
    }

    /**
     * 测试用的操作的toString
     * @return 返回操作信息
     */
    @Override
    public String toString() {
        return "PlayOperations{" +
                "trackId=" + trackId +
                ", type=" + type +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", endX=" + endX +
                ", endWidth=" + endWidth +
                ", endR=" + endR +
                ", endG=" + endG +
                ", endB=" + endB +
                ", background='" + background + '\'' +
                '}';
    }
}
