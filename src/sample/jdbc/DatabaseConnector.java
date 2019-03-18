package sample.jdbc;

import sample.entity.Filmy;
import sample.entity.Miejsca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnector{

    public static final String DB_NAME = "production.db";


    public static final String TABLE_BILETY = "Bilety";
    public static final String COLUMN_BILETY_ID_BILETU = "id_biletu";
    public static final String COLUMN_BILETY_ID_SEANSU = "id_seansu";
    public static final String COLUMN_BILETY_ID_FILMU = "id_filmu";
    public static final String COLUMN_BILETY_ID_SALI = "id_sali";
    public static final String COLUMN_BILETY_ID_MIEJSCA = "id_miejsca";
    public static final String COLUMN_BILETY_ID_TRANSAKCJI = "id_transakcji";
    public static final String COLUMN_BILETY_TYP_BILETU = "typ_biletu";
    public static final int INDEX_BILETY_ID_BILETU = 1;
    public static final int INDEX_BILETY_ID_SEANSU = 2;
    public static final int INDEX_BILETY_ID_FILMU = 3;
    public static final int INDEX_BILETY_ID_SALI = 4;
    public static final int INDEX_BILETY_ID_MIEJSCA = 5;
    public static final int INDEX_BILETY_ID_TRANSAKCJI = 6;
    public static final int INDEX_BILETY_TYP_BILETU = 7;


    public static final String TABLE_FILMY = "Filmy";
    public static final String COLUMN_FILMY_ID_FILMU = "id_filmu";
    public static final String COLUMN_FILMY_CZAS_TRWANIA = "czas_trwania";
    public static final String COLUMN_FILMY_OPIS = "opis";
    public static final String COLUMN_FILMY_TYTUL = "tytul";
    private static final int INDEX_FILMY_ID_FILMU = 1;
    private static final int INDEX_FILMY_TYTUL = 2;
    private static final int INDEX_FILMY_CZAS_TRWANIA = 3;
    private static final int INDEX_FILMY_OPIS = 4;

    public static final String TABLE_MIEJSCA = "Miejsca";
    public static final String COLUMN_MIEJSCA_ID_MIEJSCA = "id_miejsca";
    public static final String COLUMN_MIEJSCA_ID_SALI = "id_sali";
    public static final String COLUMN_MIEJSCA_ID_FILMU = "id_filmu";
    public static final String COLUMN_MIEJSCA_ID_SEANSU = "id_seansu";
    public static final String COLUMN_MIEJSCA_NR_MIEJSCA = "nr_miejsca";
    public static final String COLUMN_MIEJSCA_RZAD = "rzad";
    public static final int INDEX_MIEJSCA_ID_MIEJSCA = 1;
    public static final int INDEX_MIEJSCA_ID_SALI = 2;
    public static final int INDEX_MIEJSCA_ID_FILMU = 3;
    public static final int INDEX_MIEJSCA_ID_SEASNU = 4;
    public static final int INDEX_MIEJSCA_NR_MIEJESA = 5;
    public static final int INDEX_MIEJSCA_RZAD = 6;

    public static final String TABLE_SALE = "Sale";
    public static final String COLUMN_SALE_ID_SALI = "id_sali";
    public static final String COLUMN_SALE_NAZWA_SALI = "nazwa_sali";
    public static final int INDEX_SALE_ID_SALI = 1;
    public static final int INDEX_SALE_NAZWA_SALI = 2;

    public static final String TABLE_SEANSE = "Seanse";
    public static final String COLUMN_SEANSE_ID_SEASNSU = "id_seansu";
    public static final String COLUMN_SEANSE_ID_FILMU = "id_filmu";
    public static final String COLUMN_SEANSE_ID_SALI = "id_sali";
    public static final String COLUMN_SEASNE_DATA_SEANSU = "data_seansu";
    public static final int INDEX_SEANSE_ID_SEANSU = 1;
    public static final int INDEX_SEANSE_ID_FILMU = 2;
    public static final int INDEX_SEANSE_ID_SALI = 3;
    public static final int INDEX_SEANSE_DATA_SEANSU = 4;

    public static final String TABLE_TRANSAKCJE = "Transakcje";
    public static final String COLUMN_TRANSAKCJE_ID_TRANSAKCJI = "id_transakcji";
    public static final String COULMN_TRANSAKCJE_WARTOSC_TRANSAKCJI = "wartosc_transakcji";
    public static final int INDEX_TRANSAKCJE_ID_TRANSAKCJI = 1;
    public static final int INDEX_TRANSAKCJE_WARTOSC_TRANSAKCJI = 2;

    public static final String QUERY_FILMY =
            "SELECT * FROM " + TABLE_FILMY;

    //  public static final String QUERY_ZAJETE_MIEJSCA_W_DANYM_SEANSIE =
    //         "SELECT " + TABLE_MIEJSCA + "." + COLUMN_MIEJSCA_NR_MIEJSCA + "," + TABLE_MIEJSCA + "." + COLUMN_MIEJSCA_RZAD +
    //                 " FROM " + TABLE_MIEJSCA + " WHERE ";


    private Connection connection;

    public DatabaseConnector(){
        System.out.println(getClass().getResource("/resources/static/production.db"));
        try {

            //this.connection = DriverManager.getConnection("jdbc:sqlite::resource:" + getClass().getResource("/resources/static/production.db"));

            this.connection = DriverManager.getConnection("jdbc:sqlite:src/sample/resources/static/" + DB_NAME);

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    public List<Filmy> queryFilmy(){
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY_FILMY)){

            List<Filmy> filmy = new ArrayList<>();

            while (resultSet.next()){

                Filmy film = new Filmy();
                film.setIdFilmu(resultSet.getInt(INDEX_FILMY_ID_FILMU));
                film.setCzasTrwania(resultSet.getString(INDEX_FILMY_CZAS_TRWANIA));
                film.setOpis(resultSet.getString(INDEX_FILMY_OPIS));
                film.setTytul(resultSet.getString(INDEX_FILMY_TYTUL));
                filmy.add(film);

            }
            return filmy;
        } catch (SQLException exe){
            System.out.println("Zapytanie zakonczone niepowodzeniem: " + exe.getMessage());
            return null;
        }

    }

    public void generujMiejsca(){
        try(Statement statement = connection.createStatement()){
            List<Filmy> filmy = new ArrayList<>();

            int i =1;

            String sql="";
            for(int j=1; j<=15 ;j++){
                for(int k=1; k<=10; k++){
                    sql = "INSERT INTO \"main\".\"Miejsca\" (\"id_miejsca\", \"id_sali\", \"id_filmu\", \"id_seansu\", \"nr_miejsca\", \"rzad\") VALUES(" + i + ",1, 3, 1, " + j + " ," + k + ");";
                    System.out.println(sql);
                    i++;

                }
            }
            //statement.executeQuery(sql);

        } catch (SQLException exe){
            System.out.println("Zapytanie zakonczone niepowodzeniem: " + exe.getMessage());
        }
    }


    public Connection getConnection(){
        return this.connection;
    }




}