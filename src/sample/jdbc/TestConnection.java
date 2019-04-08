package sample.jdbc;

import sample.entity.Filmy;

import java.sql.SQLException;
import java.util.List;

public class TestConnection {

    public static void main(String[] args) {

        DatabaseConnector connector = new DatabaseConnector();

        try {
            connector.open();
            System.out.println("->>>>>>");
            List<Filmy> filmy = connector.queryFilmy();
            for(Filmy film : filmy){
                System.out.println(film);
            }
            connector.close();
        } catch (Exception exe){
            exe.printStackTrace();
        }

    }

}
