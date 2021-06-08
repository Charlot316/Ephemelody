package team.seine.ephemelody.selectinterface;

import database.RecordController;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestSelectinterface {
    public static void main(String[] args) throws SQLException {
        ResultSet rs = RecordController.getPersonalBestRecords("shw");
        rs.next();
        System.out.println(rs.getInt("score"));


    }
}
