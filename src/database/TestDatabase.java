package database;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class TestDatabase {
    public static void main(String[] args) throws SQLException {
        AtomicInteger score=new AtomicInteger();
//        for(int i=9800000;i<10000000;i++){
//            score.set(i);
//            System.out.println(RecordController.calculatePotential(1,3,score));
//        }
System.out.println(RecordController.setAndGetPersonPotential("shw"));
    }
}
