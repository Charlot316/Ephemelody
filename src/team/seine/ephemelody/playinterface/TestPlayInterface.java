package team.seine.ephemelody.playinterface;

import team.seine.ephemelody.scenes.PlayInterface;
import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class TestPlayInterface {
    public static JFrame Frame;

    public static void main(String[] args) {
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
}
