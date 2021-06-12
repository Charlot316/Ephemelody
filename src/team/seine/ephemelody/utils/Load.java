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
    /**
     * 绘制图片
     * @param path 文件路径
     * @return Image图片
     */
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

    /**
     * 绘制背景图片
     * @param path 图片路径
     * @return Image图片
     */
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

    /**
     * 读取歌曲谱
     * @param path 所在路径
     * @return 谱子的BufferReader
     */
    public static BufferedReader File(String path) {
        InputStream is = Load.class.getResourceAsStream("/resources/display/" + path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        return bufferedReader;
    }

    /**
     * 导入音乐
     * @param path 音乐所在路径
     * @return 音乐的Clip
     */
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

}
