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

        List<Miejsca> miejsca = connector.znajdzZajeteMiejsca(1);

        for (Miejsca miejsce : miejsca ){
            System.out.println(miejsce);
        }
        try {
            connector.open();
            connector.utworzSeans(4,1,"2019-04-11 22:00");
            connector.close();
        } catch (SQLException exe){
            exe.printStackTrace();
        }

        try {
            connector.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
