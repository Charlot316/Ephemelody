package team.seine.ephemelody.utils;

import javafx.beans.NamedArg;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Objects;

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

    public static BufferedReader File(String path) {
        InputStream is = Load.class.getResourceAsStream("/resources/display/" + path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        return bufferedReader;
    }

    public static Clip sound(String path) {
        try {
            BufferedInputStream loadPath = new BufferedInputStream(Load.class.getResourceAsStream("/resources/sound/" + path + ".wav"));
            AudioInputStream ais = AudioSystem.getAudioInputStream(loadPath);
            Clip sound = AudioSystem.getClip();
            sound.open(ais);
            return sound;
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void playSound(String path) {
        new Thread(() -> Load.sound(path).start()).start();
    }

}
