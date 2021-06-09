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
        ResultSet B10,R10;
        double BPotential=0,RPotential=0;
        AtomicInteger score=new AtomicInteger();
        B10=RecordController.getPersonalBestRecords("charlot");
        R10=RecordController.getPersonalRecentRecords("charlot");
        while (B10.next()) {
            BPotential+=B10.getDouble("potential");
            System.out.println(BPotential);
        }
        while(R10.next()){
            System.out.println(R10.getDouble("potential"));
            RPotential+=R10.getDouble("potential");
            System.out.println(BPotential);
        }
//        for(int i=0;i<10000000;i+=100000){
//            score.set(i);
//            System.out.println(i+" "+RecordController.calculatePotential(1,1,score));
//        }


    }
}
