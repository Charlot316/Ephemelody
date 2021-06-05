package team.seine.ephemelody.playinterface;

import team.seine.ephemelody.scenes.PlayInterface;

import java.util.concurrent.atomic.AtomicInteger;

public class ScorePresenter extends Thread {
    public static AtomicInteger count=new AtomicInteger();
    public void run(){
        count.set(0);
        while((System.currentTimeMillis()- PlayInterface.startTime)<PlayInterface.finalEndTime){
            while(count.get()<PlayInterface.currentScore.get()){
                if(count.get()+8769<=PlayInterface.currentScore.get())count.getAndAdd(8769);
                else count.set(PlayInterface.currentScore.get());
                try {
                    sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
