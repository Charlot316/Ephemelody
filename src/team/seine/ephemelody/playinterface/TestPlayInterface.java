package team.seine.ephemelody.playinterface;

import team.seine.ephemelody.scenes.PlayInterface;
import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class TestPlayInterface {
    public static JFrame Frame;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int i=0;
        while(true){
            String s=input.nextLine();
            String[] arguments = s.split("\\s+");
//            int i=0+(int)(Math.random()*256);
//            int j=0+(int)(Math.random()*256);
//            int k=0+(int)(Math.random()*256);

            if(s!=""){
                double zuo,you;
                if (i%2==0) {zuo=0.8;you=0.2;}
                else {zuo=0.2;you=0.8;}
                i++;
                System.out.println("5\t"+"1"+"\t"+arguments[0]+"\t"+arguments[1]+"\t"+zuo+"\t"+you+"\t");
            }
        }
    }
}
