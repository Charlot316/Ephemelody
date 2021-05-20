package team.seine.ephemelody.playinterface;

public abstract class PlayOperations extends Thread {//Denotes the operations the game should implement
    int id;
    int type;//Define which type of operation it is
    int trackId;//Indicates the track it related to
    double endX;
    long startTime;
    long endTime;
    double endWidth;
    int endR;
    int endG;
    int endB;
    String background;

    /**
     * Responsible for calculating the positionX at each moment, starting from positionX at currentTime, and endX at endTime
     *
     * @param id      track's id
     * @param endX    the destination of the track
     * @param endTime time when the move should be finished
     */
    public void moveTrack(int id, double endX, long endTime) {
        double currentTime;
    }

    /**
     * Responsible for calculating the width at each moment, starting from width at currentTime, and endWidth at endTime
     *
     * @param id       track's id
     * @param endWidth the final width of the track
     * @param endTime  time when the distortion should be finished
     */
    public abstract void changeWidth(int id, double endWidth, long endTime);

    /**
     * Execute the operations
     */
    public abstract void run();
}
