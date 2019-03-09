package sample.jdbc;

import java.sql.*;

public class TestConnection {

    public static void main(String[] args){

        try{

            String url = "jdbc:sqlite:src/sample/resources/static/music.db";
            Connection conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM artists");

            while (resultSet.next()){
                System.out.println( resultSet.getString("name"));


            }

        } catch (SQLException exc){
            System.out.println("Connection error: " + exc.getMessage());
        }

    }


}
