package ro.teamnet.zth.api.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Aimandis on 7/8/2016.
 */
public class DBManager {

    public static final String CONNECTION_STRING = "jdbc:oracle:thin:@" + DBProperties.IP + ":" + DBProperties.PORT;

    private DBManager() throws UnsupportedOperationException {

    }

    private static void registerDriver() {
        try {
            Class.forName(DBProperties.DRIVER_CLASS);
        } catch (Exception e) {
            System.out.println("BD register Driver error.");
        }
    }

    public static Connection getConnection() {
        Connection conn;
        registerDriver();
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING, DBProperties.USER, DBProperties.PASS);
            return conn;
        } catch (Exception e) {
            System.out.println("Error");
            return null;
        }

    }

    public static boolean checkConnection(Connection connection) {
        String SQL = "SELECT 1 FROM DUAL";
        try (Statement statement = connection.createStatement()) {
            return statement.execute(SQL);
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args){
        DBManager.registerDriver();
        DBManager.checkConnection(DBManager.getConnection());
    }
}
