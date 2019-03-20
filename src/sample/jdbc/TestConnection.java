package sample.jdbc;



import sample.entity.Filmy;
import sample.entity.Miejsca;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TestConnection {

    public static void main(String[] args) {

        DatabaseConnector connector = new DatabaseConnector();

        try {
            connector.open();
            connector.utworzTransakcje();
            connector.close();
        } catch (SQLException exe){
            exe.printStackTrace();
        }

    }

}
