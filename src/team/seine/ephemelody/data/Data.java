package team.seine.ephemelody.data;

import database.Entity.Player;
import database.Entity.Song;
import team.seine.ephemelody.utils.Load;
import team.seine.ephemelody.main.Canvas;

import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.font.GlyphVector;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Data {
    public static final int WIDTH = 1286, HEIGHT = 965, FPS = 100;
    public static Canvas canvas;
    public static List<Song> songList; // 歌曲列表，存放歌曲名，用来实现动画效果
    public static List<Song> realSongList; // 真正的歌曲列表，不会改变
    public static int offset = 0, noteVolume = 10, volume = 10; // 存放设置中的偏移、音效、音量
    public static double noteSpeed = 5.0; // 存放设置中的流速
    public static AtomicInteger[] isPressed =new AtomicInteger[200];
    public static AtomicInteger[] isReleased =new AtomicInteger[200];
    public static AtomicInteger[] isUsing = new AtomicInteger[200];
    public static AtomicInteger[] keyStatus = new AtomicInteger[200];
    public static int difficulty;
    public static int chooseSongId;
    public static int songId;
    public static Player nowPlayer;
    public static int frontSong=0;
    public static Song currentSong;
    public static void init() {

        songList = Arrays.asList(new Song(), new Song(), new Song(), new Song(), new Song());
        realSongList = Arrays.asList(new Song(0,"新手指导",1,2,3), new Song(1,"熱愛発覚中",2,5,9),new Song(2," world.excute(me);",3,6,10) );
        readSongList();
        currentSong=songList.get(2);
        Data.songId=Data.currentSong.getSongID();
    }
    public static void readSongList(){
        for(int index=frontSong, i=0;i<5;i++,index++){
            if(index>=realSongList.size()) index=0;
            songList.set(i,realSongList.get(index));
        }
    }
    public static void changeSongList(int way, int chooseSong) {
        if (way == 1) {
            frontSong++;
            if(frontSong>=realSongList.size()) frontSong=0;
            readSongList();
        } else if (way == 2) {
            frontSong--;
            if(frontSong<0) frontSong=realSongList.size()-1;
            readSongList();
        }
        Data.currentSong=Data.songList.get(2);
        Data.songId=Data.currentSong.getSongID();
    }

}