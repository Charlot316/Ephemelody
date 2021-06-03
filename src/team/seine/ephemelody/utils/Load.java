package team.seine.ephemelody.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Load {
    public static Image image(String path) {
        BufferedImage img = null;
        URL url = Load.class.getResource("/resources/img/" + path);
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
    public static Image backgroundImage(String path) {
        BufferedImage img = null;
        URL url = Load.class.getResource("/resources/display/" + path);
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
    public static BufferedReader File(String path){
        InputStream is=Load.class.getResourceAsStream("/resources/display/"+path);
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
        return bufferedReader;
    }
}
