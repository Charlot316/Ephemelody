package team.seine.ephemelody.playinterface;

import team.seine.ephemelody.scenes.PlayInterface;
import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class TestPlayInterface {
    public static JFrame Frame;

    /**
     * 生成颜色
     */
    public static void color(){
        Scanner input = new Scanner(System.in);
        while(true){
            String s=input.nextLine();
            String[] arguments = s.split("\\s+");
            int i=0+(int)(Math.random()*256);
            int j=0+(int)(Math.random()*256);
            int k=0+(int)(Math.random()*256);

            if(s!=""){
                System.out.println(arguments[0]+"\t"+"3"+"\t"+arguments[3]+"\t"+arguments[3]+"\t"+i+"\t"+j+"\t"+k);
            }
        }
    }


    /**
     * 生成重复运动的轨道指令
     */
    public static void move(){
        Scanner input = new Scanner(System.in);
        int i=0;
        while(true){
            String s=input.nextLine();
            String[] arguments = s.split("\\s+");

            if(s!=""){
                double zuo,you;
                if (i%2==0) {zuo=0.8;you=0.2;}
                else {zuo=0.2;you=0.8;}
                i++;
                System.out.println("5\t"+"1"+"\t"+arguments[0]+"\t"+arguments[1]+"\t"+zuo+"\t"+you+"\t");
            }
        }
    }
    /**
     * 生成一些谱面需要的演出
     * @param args 系统参数
     */
    public static void main(String[] args) {
        TestPlayInterface.color();
    }
}
