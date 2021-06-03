package database;

import database.Entity.Song;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SongController {

    static Connection con;
    static String uri = "jdbc:mariadb://47.93.249.243:3306/seine1? useSSL=true&characterEncoding=utf-8";
    static String user = "root";
    static String password = "123456";

    /**
     * 从数据库中根据歌曲ID和歌曲难度得到歌曲信息
     * @param songID 歌曲ID
     * @param songDifficulty 歌曲难度
     * @return selectedSong
     */
    public static Song selectSongById(int songID, int songDifficulty) {
        Song selectedSong = null;
        PreparedStatement sql;
        ResultSet rs;
        try {
            con = DriverManager.getConnection(uri, user, password);
            String sqlStr = "SELECT * FROM seine.songs WHERE songID = ? and songDifficulty = ? ";
            sql = con.prepareStatement(sqlStr);
            sql.setInt(1, songID);
            sql.setInt(2, songDifficulty);
            rs = sql.executeQuery();
            if (rs.next()) {
                selectedSong = new Song();
                selectedSong.setSongID(rs.getInt(1));
                selectedSong.setSongDifficulty(rs.getInt(2));
                selectedSong.setChartConstant(rs.getDouble(3));
                selectedSong.setNoteCount(rs.getInt(4));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return selectedSong;
    }
}
