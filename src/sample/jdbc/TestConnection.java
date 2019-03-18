package sample.jdbc;

import sample.entity.Filmy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class TestConnection {

    public static void main(String[] args) {

        DatabaseConnector connector = new DatabaseConnector();

        List<Filmy> filmy = connector.queryFilmy();

        for(Filmy film : filmy){
            System.out.println(film);
        }

        connector.generujMiejsca();

        try {
            connector.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
