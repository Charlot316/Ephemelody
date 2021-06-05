package team.seine.ephemelody.playinterface;

import team.seine.ephemelody.scenes.PlayInterface;

import java.util.concurrent.atomic.AtomicInteger;

public class ScorePresenter extends Thread {
    public static AtomicInteger count=new AtomicInteger();
    public void run(){
        count.set(0);
        while((System.currentTimeMillis()- PlayInterface.startTime)<PlayInterface.finalEndTime){
            while(count.get()<PlayInterface.currentScore.get()){
                count.getAndIncrement();
                if(count.get()%4000==0){
                    try {
                        sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}
