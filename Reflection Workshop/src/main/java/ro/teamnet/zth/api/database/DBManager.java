package ro.teamnet.zth.api.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by user on 7/8/2016.
 */
public class DBManager {


    public static String CONNECTION_STRING = "jdbc:oracle:thin:@" +
    DBProperties.IP + ":" + DBProperties.PORT;

    public DBManager(){
        throw new UnsupportedOperationException();
    }

    private static void registerDriver() throws ClassNotFoundException {
        Class.forName(DBProperties.DRIVER_CLASS);

    }

    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(CONNECTION_STRING,
                    DBProperties.USER,
                    DBProperties.PASS);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean checkConnection(Connection connection) {
        String SQL = "SELECT 1 FROM DUAL";

        try (Statement stmt = connection.createStatement()){
            return stmt.execute(SQL);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }





}
