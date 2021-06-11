package team.seine.ephemelody.data;

import com.sun.org.apache.bcel.internal.generic.FADD;
import database.Entity.Player;
import database.Entity.Song;
import database.PlayerController;
import team.seine.ephemelody.utils.Load;
import team.seine.ephemelody.main.Canvas;

import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.font.GlyphVector;
import java.io.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.jar.JarOutputStream;

public class Data {
    public static final int WIDTH = 1286, HEIGHT = 965, FPS = 100;
    public static Canvas canvas;
    public static List<Song> songList; // 歌曲列表，存放歌曲名，用来实现动画效果
    public static List<Song> realSongList; // 真正的歌曲列表，不会改变
    public static int offset = 0, noteVolume = 10, volume = 10; // 存放设置中的偏移、音效、音量
    public static double noteSpeed = 5.0; // 存放设置中的流速
    public static AtomicInteger[] isPressed = new AtomicInteger[200];
    public static AtomicInteger[] isReleased = new AtomicInteger[200];
    public static AtomicInteger[] isUsing = new AtomicInteger[200];
    public static AtomicInteger[] keyStatus = new AtomicInteger[200];
    public static int difficulty;
    public static int chooseSongId;
    public static int songId;
    public static Player nowPlayer;
    public static int frontSong = 0;
    public static Song currentSong;
    public static boolean isFirstLogin;

    /**
     * 初始化游戏的基础信息
     */
    public static void init() {
        isFirstLogin = true;
        songList = Arrays.asList(new Song(), new Song(), new Song(), new Song(), new Song());
        realSongList = Arrays.asList(
                new Song(0, "新手指导", 1, 2, 3),
                new Song(1, "熱愛発覚中", 2, 5, 9),
                new Song(2, " world.excute(me);", 3, 6, 10),
                new Song(3, "迷える音色は恋の歌", 3, 6, 11)
        );
        readSongList();
        currentSong = songList.get(2);
        Data.songId = Data.currentSong.getSongID();
    }

    /**
     * 使展示用歌曲列表读取真正的歌曲列表
     */
    public static void readSongList() {
        for (int index = frontSong, i = 0; i < 5; i++, index++) {
            if (index >= realSongList.size()) index = 0;
            songList.set(i, realSongList.get(index));
        }
    }

    /**
     * 移动歌曲列表
     * @param way 移动歌曲列表的方式
     */
    public static void changeSongList(int way) {
        if (way == 1) {
            frontSong++;
            if (frontSong >= realSongList.size()) frontSong = 0;
            readSongList();
        } else if (way == 2) {
            frontSong--;
            if (frontSong < 0) frontSong = realSongList.size() - 1;
            readSongList();
        }
        Data.currentSong = Data.songList.get(2);
        Data.songId = Data.currentSong.getSongID();
    }

    /**
     * 检查用户是否登录过，若登录过则自动登录
     */
    public static void checkLogin() {
        isFirstLogin = false;
        InputStream inputStream = Data.class.getResourceAsStream("/resources/inf/record.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String s, userId = null, password = null;
        List<String> user;
        try {
            if ((s = bufferedReader.readLine()) != null) {
                user = Arrays.asList(s.split("\\s+"));
                userId = user.get(0);
                password = user.get(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert bufferedReader != null;
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (userId != null && password != null) {
            if (password.equals(PlayerController.selectPlayerById(userId).getPassword())) {
                Data.nowPlayer = PlayerController.selectPlayerById(userId);
            }
        }
    }

    /**
     * 记录当前用户的登录信息
     * @param inf 用户账号加密码的String类型
     */
    public static void recordLoginInf(String inf) {
//        String filePath = Objects.requireNonNull(Data.class.getClassLoader().getResource("/resources/inf/record.txt")).getPath();
//        URL url = Data.class.getResource("/resources/inf/record.txt");
//        InputStream inputStream = Data.class.getResourceAsStream("/resources/inf/record.txt");
        File file = new File("/resources/inf/record.txt");
        FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        try {
            fileWriter = new FileWriter(file);
            printWriter = new PrintWriter(fileWriter);
            printWriter.write(inf);
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}