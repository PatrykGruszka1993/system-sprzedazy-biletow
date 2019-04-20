package sample.jdbc;

import sample.entity.Bilety;
import sample.entity.Filmy;
import sample.entity.Miejsca;
import sample.entity.Seanse;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

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
    public static final String COLUMN_TRANSAKCJE_WARTOSC_TRANSAKCJI = "wartosc_transakcji";
    public static final int INDEX_TRANSAKCJE_ID_TRANSAKCJI = 1;
    public static final int INDEX_TRANSAKCJE_WARTOSC_TRANSAKCJI = 2;

    public static final String QUERY_FILMY =
            "SELECT * FROM " + TABLE_FILMY;

    private static final String QUERY_FILMY_NA_DATE =
            "SELECT * FROM " + TABLE_FILMY + " WHERE " +  COLUMN_FILMY_ID_FILMU + " in (SELECT " +
                    COLUMN_SEANSE_ID_FILMU + " FROM " + TABLE_SEANSE + " WHERE  date(" +
                    COLUMN_SEASNE_DATA_SEANSU +") =?);";

    public static final String QUERY_SEANSE_DLA_DANEGO_FILMU =
            "SELECT * FROM " + TABLE_SEANSE + " WHERE " + TABLE_SEANSE + "." + COLUMN_SEANSE_ID_FILMU +
                    "=? AND date(" + COLUMN_SEASNE_DATA_SEANSU + ") =?;";

    public static final String QUERY_ZAJETE_MIEJSCA_W_DANYM_SEANSIE =
           "SELECT * FROM " + TABLE_MIEJSCA + " WHERE " + COLUMN_MIEJSCA_ID_MIEJSCA + " IN (SELECT " +
                   COLUMN_BILETY_ID_MIEJSCA + " FROM " + TABLE_BILETY + " WHERE " + COLUMN_BILETY_ID_SEANSU +
                   " =?);";

    public static final String QUERY_MIEJSCA_W_DANYM_SEANSIE =
            "SELECT * FROM " + TABLE_MIEJSCA + " WHERE " + TABLE_MIEJSCA + "." + COLUMN_MIEJSCA_ID_SEANSU + " =?";

    public static final String UTWORZ_SEANS =
            "INSERT INTO " + TABLE_SEANSE +
                    " (" + COLUMN_SEANSE_ID_FILMU + ", " + COLUMN_SEANSE_ID_SALI + ", " + COLUMN_SEASNE_DATA_SEANSU +
            ") VALUES(?,?,?)";

    public static final String GENERUJ_MIEJSCA =
            "INSERT INTO " + TABLE_MIEJSCA +
                    " (" + COLUMN_MIEJSCA_ID_SALI + ", " + COLUMN_MIEJSCA_ID_FILMU +
                    ", " + COLUMN_MIEJSCA_ID_SEANSU + ", " + COLUMN_MIEJSCA_NR_MIEJSCA + ", " + COLUMN_MIEJSCA_RZAD +
                    ") VALUES(?,?,?,?,?)";

    public static final String UTWORZ_BILET =
            "INSERT INTO " + TABLE_BILETY +
                    " (" + COLUMN_BILETY_ID_SEANSU + ", " + COLUMN_BILETY_ID_FILMU + ", " + COLUMN_BILETY_ID_SALI + ", " +
                    COLUMN_BILETY_ID_MIEJSCA + ", " + COLUMN_BILETY_ID_TRANSAKCJI + ", " + COLUMN_BILETY_TYP_BILETU +
                    ") VALUES(?,?,?,?,?,?);";

    public static final String UTWORZ_TRANSAKCJE =
            "INSERT INTO " + TABLE_TRANSAKCJE + " (" + COLUMN_TRANSAKCJE_WARTOSC_TRANSAKCJI +
                    ") VALUES(?);";

    /*
    public static final String QUERY_ID_MIEJSCA =
            "SELECT " + TABLE_MIEJSCA + "." + COLUMN_MIEJSCA_ID_MIEJSCA + " FROM " + TABLE_MIEJSCA +
                    " WHERE " + TABLE_MIEJSCA + "." + COLUMN_MIEJSCA_ID_SEANSU + " =? AND " +
                    TABLE_MIEJSCA + "." + COLUMN_MIEJSCA_NR_MIEJSCA + "=? AND " +
                    TABLE_MIEJSCA + "." + COLUMN_MIEJSCA_RZAD + "=?;";
*/

    public static final String QUERY_ID_MIEJSCA =
            "SELECT * FROM " + TABLE_MIEJSCA +
                    " WHERE " + TABLE_MIEJSCA + "." + COLUMN_MIEJSCA_ID_SEANSU + " =? AND " +
                    TABLE_MIEJSCA + "." + COLUMN_MIEJSCA_NR_MIEJSCA + "=? AND " +
                    TABLE_MIEJSCA + "." + COLUMN_MIEJSCA_RZAD + "=?;";


    private Connection connection;
    private PreparedStatement queryZajeteMiejsca;
    private PreparedStatement utworzSeans;
    private PreparedStatement generujMiejsca;
    private PreparedStatement utworzTransakcje;
    private PreparedStatement querySeanseDlaDanegoFilmu;
    private PreparedStatement queryIdMiejsca;



    private static DatabaseConnector instance = new DatabaseConnector();


    public boolean open(){
        try{
            utworzSeans = connection.prepareStatement(UTWORZ_SEANS, Statement.RETURN_GENERATED_KEYS);
            generujMiejsca = connection.prepareStatement(GENERUJ_MIEJSCA, Statement.RETURN_GENERATED_KEYS);
            queryZajeteMiejsca = connection.prepareStatement(QUERY_ZAJETE_MIEJSCA_W_DANYM_SEANSIE);
            utworzTransakcje = connection.prepareStatement(UTWORZ_TRANSAKCJE, Statement.RETURN_GENERATED_KEYS);
            querySeanseDlaDanegoFilmu = connection.prepareStatement(QUERY_SEANSE_DLA_DANEGO_FILMU);
            queryIdMiejsca = connection.prepareStatement(QUERY_ID_MIEJSCA, Statement.RETURN_GENERATED_KEYS);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void close(){
        try{
            if(connection != null){
                connection.close();
            }
            if(utworzSeans != null){
                utworzSeans.close();
            }
            if(generujMiejsca != null){
                generujMiejsca.close();
            }
            if(queryZajeteMiejsca != null){
                queryZajeteMiejsca.close();
            }
            if(utworzTransakcje != null){
                utworzTransakcje.close();
            }
            if(querySeanseDlaDanegoFilmu != null){
                querySeanseDlaDanegoFilmu.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public DatabaseConnector(){
        System.out.println(getClass().getResource("/resources/static/production.db"));
        try {
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
            exe.printStackTrace();
            return null;
        }
    }

    public List<Filmy> queryFilmyDlaDaty(Date data){
        PreparedStatement statement = null;
        try{
            statement  = connection.prepareStatement(QUERY_FILMY_NA_DATE);
            statement.setString(1, data.toString());
        }catch (Exception e){
            e.getMessage();
        }

        try(ResultSet resultSet = statement.executeQuery()){
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
            exe.printStackTrace();
            return null;
        }
    }

    public List<Seanse> querySeansDlaDanegoFilmu(Filmy film, Date dataSeansu){
        List<Seanse> seanse = new ArrayList<>();
        try {
            querySeanseDlaDanegoFilmu.setInt(1, film.getIdFilmu());
            querySeanseDlaDanegoFilmu.setString(2, dataSeansu.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try(ResultSet resultSet = querySeanseDlaDanegoFilmu.executeQuery()){
            while(resultSet.next()){
                Seanse seans = new Seanse();

                seans.setIdSeansu(resultSet.getInt(INDEX_SEANSE_ID_SEANSU));
                seans.setIdFilmu(resultSet.getInt(INDEX_SEANSE_ID_FILMU));
                seans.setIdSali(resultSet.getInt(INDEX_SEANSE_ID_SALI));
                String dataString = resultSet.getString(INDEX_SEANSE_DATA_SEANSU);

                SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                try {
                    seans.setDataSeansu(data.parse(dataString));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                seanse.add(seans);
            }
            return seanse;

        } catch (SQLException exe){
            System.out.println("Zapytanie zakonczone niepowodzeniem " + exe.getMessage());
            exe.printStackTrace();
            return null;
        }
    }

  public int utworzSeans(int idFilmu, int idSali, String dataSeansu) throws SQLException{

        utworzSeans.setInt(1,idFilmu);
        utworzSeans.setInt(2, idSali);
        utworzSeans.setString(3, dataSeansu);

        int affected = utworzSeans.executeUpdate();
        if(affected != 1) {
            throw new SQLException("Nie dodano seansu!");
        }
        ResultSet generatedKeys = utworzSeans.getGeneratedKeys();
        if(generatedKeys.next()){
            int idSeansu = generatedKeys.getInt(1);
            generujMiejsca(idSali, idFilmu, idSeansu);
            return idSeansu;
        } else {
            throw new SQLException("Nie mozna było zdobyć idSeansu!");
        }
    }


    private void generujMiejsca(int idSali, int idFilmu, int idSeansu){
        try {
            int i =1;
            for(int j=1; j<=15 ;j++){
                for(int k=1; k<=10; k++){
                    generujMiejsca.setInt(1, idSali);
                    generujMiejsca.setInt(2, idFilmu);
                    generujMiejsca.setInt(3, idSeansu);
                    generujMiejsca.setInt(4, j);
                    generujMiejsca.setInt(5, k);

                    generujMiejsca.executeUpdate();
                    i++;
                }
            }
        } catch (SQLException exe){
            System.out.println("Zapytanie zakonczone niepowodzeniem: " + exe.getMessage());
        }
    }

    public int utworzTransakcje() throws SQLException{
        utworzTransakcje.setInt(1, 0); //ustawienie wartosci transakcji na zero

        int affected = utworzTransakcje.executeUpdate();
        if(affected != 1){
            throw new SQLException("Nie dodano transakcji!");
        }
        ResultSet generatedKeys = utworzTransakcje.getGeneratedKeys();
        if(generatedKeys.next()){
            return generatedKeys.getInt(1);
        } else {
            throw new SQLException("Nie wygenerowano idTransakcji!");
        }
    }

    public List<Miejsca> znajdzZajeteMiejsca(Seanse seans){
        List<Miejsca> miejsca = new ArrayList<>();

        try {
            queryZajeteMiejsca.setInt(1, seans.getIdSeansu());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try(ResultSet resultSet = queryZajeteMiejsca.executeQuery()){
            while(resultSet.next()){
                Miejsca miejsce = new Miejsca();
                miejsce.setIdMiejsca(resultSet.getInt(INDEX_MIEJSCA_ID_MIEJSCA));
                miejsce.setIdSali(resultSet.getInt(INDEX_MIEJSCA_ID_SALI));
                miejsce.setIdFilmu(resultSet.getInt(INDEX_MIEJSCA_ID_FILMU));
                miejsce.setIdSeansu(resultSet.getInt(INDEX_MIEJSCA_ID_SEASNU));
                miejsce.setNrMiejsca(resultSet.getInt(INDEX_MIEJSCA_NR_MIEJESA));
                miejsce.setRzad(resultSet.getInt(INDEX_MIEJSCA_RZAD));

                miejsca.add(miejsce);
            }
            return miejsca;

        } catch (SQLException exe){
            System.out.println("Zapytanie zakonczone niepowodzeniem " + exe.getMessage());
            exe.printStackTrace();
            return null;
        }
    }

    public Miejsca znajdzMiejsce(Seanse seans, int nrMiejsca, int rzad){

        Miejsca miejsce = new Miejsca();

        try {
            queryIdMiejsca.setInt(1, seans.getIdSeansu());
            queryIdMiejsca.setInt(2, nrMiejsca);
            queryIdMiejsca.setInt(3, rzad);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try(ResultSet resultSet = queryIdMiejsca.executeQuery()){
            while(resultSet.next()){
                miejsce.setIdMiejsca(resultSet.getInt(INDEX_MIEJSCA_ID_MIEJSCA));
                miejsce.setIdSali(resultSet.getInt(INDEX_MIEJSCA_ID_SALI));
                miejsce.setIdFilmu(resultSet.getInt(INDEX_MIEJSCA_ID_FILMU));
                miejsce.setIdSeansu(resultSet.getInt(INDEX_MIEJSCA_ID_SEASNU));
                miejsce.setNrMiejsca(resultSet.getInt(INDEX_MIEJSCA_NR_MIEJESA));
                miejsce.setRzad(resultSet.getInt(INDEX_MIEJSCA_RZAD));

            }
            return miejsce;

        } catch (SQLException exe){
            System.out.println("Zapytanie zakonczone niepowodzeniem " + exe.getMessage());
            exe.printStackTrace();
            return null;
        }
    }

    public int idDlaNowejTransakcji(){
        String query = "SELECT MAX(id_transakcji) FROM Transakcje;";
        int noweId = -1;

        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet resultSet = stm.executeQuery();
            noweId = resultSet.getInt(INDEX_TRANSAKCJE_ID_TRANSAKCJI) + 1;
        }catch (SQLException e){
            e.printStackTrace();
        }

        return noweId;
    }

    public int idDlaNowegoBiletu(){
        String query = "SELECT MAX(id_biletu) FROM Bilety;";
        int noweId = -1;

        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet resultSet = stm.executeQuery();
            noweId = resultSet.getInt(INDEX_BILETY_ID_BILETU) + 1;
        }catch (SQLException e){
            e.printStackTrace();
        }

        return noweId;
    }


    public Connection getConnection(){
        return this.connection;
    }

    public static DatabaseConnector getInstance() {
        return instance;
    }

}