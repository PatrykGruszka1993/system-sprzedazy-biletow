package sample.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector{

    private Connection connection;

    public DatabaseConnector(){
        System.out.println(getClass().getResource("/resources/static/production.db"));
        try {

            this.connection = DriverManager.getConnection("jdbc:sqlite::resource:" + getClass().getResource("/resources/static/production.db"));

            this.connection = DriverManager.getConnection("jdbc:sqlite:src/sample/resources/static/production.db");

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    public Connection getConnection(){
        return this.connection;
    }
}