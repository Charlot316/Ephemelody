package database;

import database.Entity.Player;
import database.Entity.Record;
import database.Entity.Song;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RecordController {

    static Connection con;
    static String uri = "jdbc:mysql://localhost:3306/seine? useSSL=true&characterEncoding=utf-8";
    static String user = "root";
    static String password = "123456";

    /**
     * 插入游玩记录，personal_recent_records记录个人最近30条；personal_best_records记录个人单曲最好成绩;
     *
     * @param record
     */
    public static void insertRecord(Record record) {
        PreparedStatement sql;
        ResultSet rs;
        int rowCount;
        try {
            con = DriverManager.getConnection(uri, user, password);
            //查找最近记录条数
            String sqlStr = "SELECT * FROM seine.personal_recent_records WHERE playerID = ? ";
            sql = con.prepareStatement(sqlStr);
            sql.setString(1, record.getPlayerID());
            rs = sql.executeQuery();
            rowCount = rs.getRow();
            if (rowCount == 30) {
                //等于30条就删掉最近一条
                String sqlStr1 = "DELETE FROM seine.personal_recent_records WHERE playerID = ? ORDER BY time LIMIT 1";
                sql = con.prepareStatement(sqlStr1);
                sql.setString(1, record.getPlayerID());
                rs = sql.executeQuery();
            }
            //查看同一首歌最佳分数
            String sqlStr2 = "SELECT * FROM seine.personal_best_records WHERE playerID = ? and songID = ? and songDifficulty = ?";
            sql = con.prepareStatement(sqlStr2);
            sql.setString(1, record.getPlayerID());
            sql.setInt(2, record.getSongID());
            sql.setInt(3, record.getSongDifficulty());
            rs = sql.executeQuery();
            //没有最佳记录就插入
            if (rs.getRow() == 0) {
                String sqlStr3 = "INSERT INTO seine.personal_best_records(playerID, time, songID, songDifficulty, pureCount, farCount, lostCount, maxCombo, potential, score) " +
                        "VALUES (?,?,?,?,?,?,?,?,?,?)";
                sql = con.prepareStatement(sqlStr3);
                sql.setString(1, record.getPlayerID());
                sql.setDate(2, record.getTime());
                sql.setInt(3, record.getSongID());
                sql.setInt(4, record.getSongDifficulty());
                sql.setInt(5, record.getPureCount());
                sql.setInt(6, record.getFarCount());
                sql.setInt(7, record.getLostCount());
                sql.setInt(8, record.getMaxCombo());
                sql.setDouble(9, record.getPotential());
                sql.setInt(10, record.getScore());
                rs = sql.executeQuery();
            }
            //有记录且刷新纪录就更新
            else if (rs.next()) {
                if (record.getScore() > rs.getInt(10)) {
                    String sqlStr4 = "UPDATE seine.personal_best_records SET score = ?,time = ? WHERE playerID = ? and songID = ? and songDifficulty = ?";
                    sql = con.prepareStatement(sqlStr4);
                    sql.setInt(1, record.getScore());
                    sql.setDate(2, record.getTime());
                    sql.setString(3, record.getPlayerID());
                    sql.setInt(4, record.getSongID());
                    sql.setInt(5, record.getSongDifficulty());
                    rs = sql.executeQuery();
                }
            }
            //插入最近记录
            String sqlStr5 = "INSERT INTO seine.personal_recent_records(playerID, time, songID, songDifficulty, pureCount, farCount, lostCount, maxCombo, potential, score) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?)";
            sql = con.prepareStatement(sqlStr5);
            sql.setString(1, record.getPlayerID());
            sql.setDate(2, record.getTime());
            sql.setInt(3, record.getSongID());
            sql.setInt(4, record.getSongDifficulty());
            sql.setInt(5, record.getPureCount());
            sql.setInt(6, record.getFarCount());
            sql.setInt(7, record.getLostCount());
            sql.setInt(8, record.getMaxCombo());
            sql.setDouble(9, record.getPotential());
            sql.setInt(10, record.getScore());
            rs = sql.executeQuery();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * 从数据库中返回3条个人最好成绩
     *
     * @param playerID
     * @return
     */
    public static ResultSet getPersonalBestRecords(String playerID) {
        PreparedStatement sql;
        ResultSet rs = null;
        try {
            con = DriverManager.getConnection(uri, user, password);
            String sqlStr = "SELECT * FROM seine.personal_best_records WHERE playerID = ? ORDER BY score DESC LIMIT 3";
            sql = con.prepareStatement(sqlStr);
            sql.setString(1, playerID);
            rs = sql.executeQuery();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rs;
    }

    /**
     * 从数据库中返回10条最近的最好成绩
     *
     * @param playerID
     * @return
     */
    public static ResultSet getPersonalRecentRecords(String playerID) {
        PreparedStatement sql;
        ResultSet rs = null;
        try {
            con = DriverManager.getConnection(uri, user, password);
            String sqlStr = "SELECT * FROM seine.personal_recent_records WHERE playerID = ? ORDER BY score DESC LIMIT 10";
            sql = con.prepareStatement(sqlStr);
            sql.setString(1, playerID);
            rs = sql.executeQuery();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rs;
    }
}
