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
//        for(int i=9800000;i<10000000;i++){
//            score.set(i);
//            System.out.println(RecordController.calculatePotential(1,3,score));
//        }
    System.out.println(RecordController.setAndGetPersonPotential("charlot"));


    }
}
