package database;

import java.sql.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TestDatabase {
    static Connection con;
    static String uri = "jdbc:mariadb://47.93.249.243:3306/seine? useSSL=true&characterEncoding=utf-8";
    static String user = "root";
    static String password = "123456";
    public static void main(String[] args) throws SQLException {
        PreparedStatement sql;
        ResultSet countRs;
        AtomicInteger score=new AtomicInteger();
        for(int i=0;i<10000000;i+=100000){
            score.set(i);
            System.out.println(i+" "+RecordController.calculatePotential(1,1,score));
        }


    }
}
