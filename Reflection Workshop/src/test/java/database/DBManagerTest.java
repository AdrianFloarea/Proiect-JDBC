package database;

import org.junit.Assert;
import org.junit.Test;
import ro.teamnet.zth.api.database.DBManager;

import java.sql.Connection;

/**
 * Created by Silvia.Vraciu on 7/8/2016.
 */
public class DBManagerTest {

    @Test
    public void testgetConnection(){
        Assert.assertTrue("Check null connection ",DBManager.getConnection()!=null);
    }
    @Test
    public void testcheckConnection(){
        Connection connection = DBManager.getConnection();
        System.out.println(DBManager.checkConnection(connection));
        Assert.assertEquals("Check connection",DBManager.checkConnection(connection), true );
    }
}
