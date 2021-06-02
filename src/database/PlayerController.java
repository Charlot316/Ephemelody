package database;

import database.Entity.Player;

import java.sql.*;

public class PlayerController {

    static Connection con;
    static String uri = "jdbc:mysql://localhost:3306/seine? useSSL=true&characterEncoding=utf-8";
    static String user = "root";
    static String password = "123456";

    /**
     * 从数据库中根据玩家ID返回玩家信息
     *
     * @param playerID 玩家ID
     * @return 查询所得玩家
     */
    public static Player selectPlayerById(String playerID) {
        Player selectedPlayer = null;
        PreparedStatement sql;
        ResultSet rs;
        try {
            con = DriverManager.getConnection(uri, user, password);
            String sqlStr = "SELECT * FROM seine.players WHERE playerID = ?";
            sql = con.prepareStatement(sqlStr);
            sql.setString(1, playerID);
            rs = sql.executeQuery();
            if (rs.next()) {
                selectedPlayer = new Player();
                selectedPlayer.setPlayerID(rs.getString(1));
                selectedPlayer.setPassword(rs.getString(2));
                selectedPlayer.setPotential(rs.getDouble(3));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return selectedPlayer;
    }

    /**
     * 向数据库中插入新玩家
     * @param playerID 玩家ID
     * @param password1 玩家密码
     */
    public static void insertPlayer(String playerID, String password1) {
        PreparedStatement sql;
        ResultSet rs;
        try {
            con = DriverManager.getConnection(uri, user, password);
            String sqlStr = "INSERT INTO seine.players(playerID, password, potential) VALUES(?,?,0) ";
            sql = con.prepareStatement(sqlStr);
            sql.setString(1, playerID);
            sql.setString(2, password1);
            rs = sql.executeQuery();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
