package database;

import database.Entity.Record;
import database.Entity.Song;

import java.sql.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RecordController {

    static Connection con;
    static String uri = "jdbc:mariadb://47.93.249.243:3306/seine? useSSL=true&characterEncoding=utf-8";
    static String user = "root";
    static String password = "123456";

    /**
     * 计算单首歌的潜力值
     * @param SongID 歌曲ID
     * @param SongDifficulty 歌曲难度
     * @param score 歌曲分数
     * @return 潜力值
     */
    public static double calculatePotential(int SongID,int SongDifficulty,AtomicInteger score){
        double chartConstant;
        Song song=SongController.selectSongById(SongID,SongDifficulty);
        chartConstant=song.getChartConstant();
        double potential;
        if(score.get()>=10000000){
            potential=chartConstant+2;
        }
        else if(score.get()>=9800000){
            potential=1+chartConstant+((double)score.get() - 9800000.0)/200000.0;
        }
        else {
            potential=chartConstant+((double)score.get() - 9500000.0)/300000.0;
        }
        if(potential<0) potential=0;
        return potential;
    }

    /**
     * 计算一个玩家的潜力值
     * 潜力值=（最好的10次结果+最近的30次里最好的10次结果）/20
     * @param playerID 用户ID
     * @return 用户潜力值
     */
    public static double setAndGetPersonPotential(String playerID){
        PreparedStatement sql;
        ResultSet B10, R10;
        double BPotential=0,RPotential=0,potential=0;
        try {
            B10=RecordController.getPersonalBestRecords(playerID);
            R10=RecordController.getPersonalRecentRecords(playerID);
            while (B10.next()) {
                BPotential+=B10.getDouble("potential");
            }
            while(R10.next()){
                RPotential+=R10.getDouble("potential");
            }
            potential=(BPotential+RPotential)/20.0;
            con = DriverManager.getConnection(uri, user, password);
            String sqlStr2 = "UPDATE seine.players SET potential= ? WHERE playerID= ?";
            sql = con.prepareStatement(sqlStr2);
            sql.setDouble(1, potential);
            sql.setString(2, playerID);
            sql.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return potential;
    }

    /**
     * 向数据库中插入游玩记录，personal_recent_records记录个人最近30条
     *
     * @param record 插入记录
     */
    public static void insertRecentRecord(Record record) {
        PreparedStatement sql;
        ResultSet rs,countRs;
        int count=0;
        double leastPotential = 0;
        int rowCount;
        try {
            con = DriverManager.getConnection(uri, user, password);
            String sqlStr = "SELECT * FROM seine.personal_recent_records WHERE playerID = ? ORDER BY potential LIMIT 10";
            sql = con.prepareStatement(sqlStr);
            sql.setString(1, record.getPlayerID());
            rs = sql.executeQuery();
            rs.last();
            //该分数大于数据库中30条最近数据中潜力值最高的10个值最小的值，且分数大于9000000，即可插入
            if (rs.getRow()<10||record.getScore() < 9000000 || (record.getScore() >= 9000000 && rs.getDouble(9) > record.getPotential())) {
                //查找最近记录条数
                String sqlStr1 = "SELECT * FROM seine.personal_recent_records WHERE playerID = ? ";
                sql = con.prepareStatement(sqlStr1);
                sql.setString(1, record.getPlayerID());
                rs = sql.executeQuery();
                rowCount = rs.getRow();
                if (rowCount > 30) {
                    //等于30条就删掉时间最早的一条
                    String sqlStr2 = "DELETE FROM seine.personal_recent_records WHERE playerID = ? ORDER BY time LIMIT 1";
                    sql = con.prepareStatement(sqlStr2);
                    sql.setString(1, record.getPlayerID());
                    sql.executeQuery();
                }
                //插入最近记录
                String sqlStr3 = "INSERT INTO seine.personal_recent_records(playerID, time, songID, songDifficulty, pureCount, farCount, lostCount, maxCombo, potential, score) " +
                        "VALUES (?,?,?,?,?,?,?,?,?,?)";
                System.out.println("8");
                sql = con.prepareStatement(sqlStr3);
                sql.setString(1, record.getPlayerID());
                sql.setTimestamp(2, record.getTime());
                sql.setInt(3, record.getSongID());
                sql.setInt(4, record.getSongDifficulty());
                sql.setInt(5, record.getPureCount());
                sql.setInt(6, record.getFarCount());
                sql.setInt(7, record.getLostCount());
                sql.setInt(8, record.getMaxCombo());
                sql.setDouble(9, record.getPotential());
                sql.setInt(10, record.getScore());
                sql.executeQuery();
                System.out.println("9");
            }
            System.out.println("10");
            con.close();
            System.out.println("11");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向数据库中插入游玩记录，personal_best_records记录个人单曲最好成绩
     *
     * @param record 插入记录
     */
    public static void insertBestRecord(Record record) {
        PreparedStatement sql;
        ResultSet rs;
        try {
            con = DriverManager.getConnection(uri, user, password);
            //查看同一首歌最佳分数
            String sqlStr = "SELECT * FROM seine.personal_best_records WHERE playerID = ? and songID = ? and songDifficulty = ?";
            sql = con.prepareStatement(sqlStr);
            sql.setString(1, record.getPlayerID());
            sql.setInt(2, record.getSongID());
            sql.setInt(3, record.getSongDifficulty());
            rs = sql.executeQuery();
            rs.last();
            int row = rs.getRow();
            rs.beforeFirst();
            //没有最佳记录就插入
            if (row == 0) {
                String sqlStr1 = "INSERT INTO seine.personal_best_records(playerID, time, songID, songDifficulty, pureCount, farCount, lostCount, maxCombo, potential, score) " +
                        "VALUES (?,?,?,?,?,?,?,?,?,?)";
                sql = con.prepareStatement(sqlStr1);
                sql.setString(1, record.getPlayerID());
                sql.setTimestamp(2, record.getTime());
                sql.setInt(3, record.getSongID());
                sql.setInt(4, record.getSongDifficulty());
                sql.setInt(5, record.getPureCount());
                sql.setInt(6, record.getFarCount());
                sql.setInt(7, record.getLostCount());
                sql.setInt(8, record.getMaxCombo());
                sql.setDouble(9, record.getPotential());
                sql.setInt(10, record.getScore());
                sql.executeQuery();
            }
            //有记录且刷新纪录就更新
            else if (rs.next()) {
                if (record.getScore() > rs.getInt(10)) {
                    String sqlStr2 = "UPDATE seine.personal_best_records SET score = ?,time = ? WHERE playerID = ? and songID = ? and songDifficulty = ?";
                    sql = con.prepareStatement(sqlStr2);
                    sql.setInt(1, record.getScore());
                    sql.setTimestamp(2, record.getTime());
                    sql.setString(3, record.getPlayerID());
                    sql.setInt(4, record.getSongID());
                    sql.setInt(5, record.getSongDifficulty());
                    sql.executeQuery();
                }
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入远程数据库最佳分数记录
     *
     * @param record 插入记录
     */
    public static void insertAllBestRecord(Record record) {
        PreparedStatement sql;
        ResultSet rs;
        int rowCount;
        try {
            con = DriverManager.getConnection(uri, user, password);
            //查看同一首歌最佳分数
            String sqlStr = "SELECT * FROM seine.all_best_records WHERE playerID = ? and songID = ? and songDifficulty = ?";
            sql = con.prepareStatement(sqlStr);
            sql.setString(1, record.getPlayerID());
            sql.setInt(2, record.getSongID());
            sql.setInt(3, record.getSongDifficulty());
            rs = sql.executeQuery();
            rs.last();
            int row = rs.getRow();
            rs.beforeFirst();
            //没有最佳记录就插入
            if (row == 0) {
                String sqlStr1 = "INSERT INTO seine.all_best_records(playerID, time, songID, songDifficulty, pureCount, farCount, lostCount, maxCombo, potential, score) " +
                        "VALUES (?,?,?,?,?,?,?,?,?,?)";
                sql = con.prepareStatement(sqlStr1);
                sql.setString(1, record.getPlayerID());
                sql.setTimestamp(2, record.getTime());
                sql.setInt(3, record.getSongID());
                sql.setInt(4, record.getSongDifficulty());
                sql.setInt(5, record.getPureCount());
                sql.setInt(6, record.getFarCount());
                sql.setInt(7, record.getLostCount());
                sql.setInt(8, record.getMaxCombo());
                sql.setDouble(9, record.getPotential());
                sql.setInt(10, record.getScore());
                sql.executeQuery();
            }
            //有记录且刷新纪录就更新
            else if (rs.next()) {
                if (record.getScore() > rs.getInt(10)) {
                    String sqlStr2 = "UPDATE seine.all_best_records SET score = ?,time = ? WHERE playerID = ? and songID = ? and songDifficulty = ?";
                    sql = con.prepareStatement(sqlStr2);
                    sql.setInt(1, record.getScore());
                    sql.setTimestamp(2, record.getTime());
                    sql.setString(3, record.getPlayerID());
                    sql.setInt(4, record.getSongID());
                    sql.setInt(5, record.getSongDifficulty());
                    sql.executeQuery();
                }
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从数据库中返回10条个人最好成绩
     *
     * @param playerID 玩家ID
     * @return 成绩集
     */
    public static ResultSet getPersonalBestRecords(String playerID) {
        PreparedStatement sql;
        ResultSet rs = null;
        try {
            con = DriverManager.getConnection(uri, user, password);
            String sqlStr = "SELECT * FROM seine.personal_best_records WHERE playerID = ? ORDER BY score DESC LIMIT 10";
            sql = con.prepareStatement(sqlStr);
            sql.setString(1, playerID);
            rs = sql.executeQuery();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    /**
     * 从数据库中返回某首曲子的个人最好成绩
     *
     * @param playerID 玩家ID
     * @param songID 音乐ID
     * @return 成绩集
     */
    public static ResultSet getPersonalBestRecordsBySongId(String playerID, int songID,int songDifficulty) {
        PreparedStatement sql;
        ResultSet rs = null;
        try {
            con = DriverManager.getConnection(uri, user, password);
            String sqlStr = "SELECT * FROM seine.personal_best_records WHERE playerID = ? and songID = ? and songDifficulty = ? ";
            sql = con.prepareStatement(sqlStr);
            sql.setString(1, playerID);
            sql.setInt(2, songID);
            sql.setInt(3, songDifficulty);
            rs = sql.executeQuery();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    /**
     * 从数据库中返回10条最近的最好成绩
     *
     * @param playerID 玩家ID
     * @return 成绩集
     */
    public static ResultSet getPersonalRecentRecords(String playerID) {
        PreparedStatement sql;
        ResultSet rs = null;
        try {
            con = DriverManager.getConnection(uri, user, password);
            String sqlStr = "SELECT * FROM seine.personal_recent_records WHERE playerID = ? ORDER BY potential DESC LIMIT 10";
            sql = con.prepareStatement(sqlStr);
            sql.setString(1, playerID);
            rs = sql.executeQuery();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * 从数据库中返回10条最佳成绩
     */
    public static ResultSet getAllBestRecords(int songId, int difficulty) {
        PreparedStatement sql;
        ResultSet rs = null;
        try {
            con = DriverManager.getConnection(uri, user, password);
            String sqlStr = "SELECT * FROM seine.all_best_records WHERE songID = ? AND songDifficulty = ? ORDER BY score DESC LIMIT 10";
            sql = con.prepareStatement(sqlStr);
            sql.setInt(1, songId);
            sql.setInt(2, difficulty);
            rs = sql.executeQuery();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * 从远程数据库中返回某首歌的最佳成绩
     */
    public static ResultSet getBestRecords(int songId, int difficulty) {
        PreparedStatement sql;
        ResultSet rs = null;
        try {
            con = DriverManager.getConnection(uri, user, password);
            String sqlStr = "SELECT * FROM seine.all_best_records WHERE songId = ? AND songDifficulty = ? ORDER BY score DESC LIMIT 1";
            sql = con.prepareStatement(sqlStr);
            sql.setInt(1, songId);
            sql.setInt(2, difficulty);
            rs = sql.executeQuery();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }


}
