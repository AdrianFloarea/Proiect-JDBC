package ro.teamnet.zth.api.em;

import ro.teamnet.zth.api.annotations.Id;
import ro.teamnet.zth.api.database.DBManager;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aimandis on 7/8/2016.
 */
public class EntityManagerImpl implements EntityManager {

    public <T> Object insert(Class<T> entity) {
        Connection con = DBManager.getConnection();
        DBManager.checkConnection(con);

        String tableName = EntityUtils.getTableName(entity);

        List<ColumnInfo> columnsList = EntityUtils.getColumns(entity);
        long id = setID(entity, columnsList);


        QueryBuilder qb = new QueryBuilder();
        qb.setTableName(tableName);
        qb.addQueryColumns(columnsList);
        qb.setQueryType(QueryType.INSERT);

        String query = qb.createQuery();

        Statement statement = null;
        try {
            statement = con.createStatement();
            statement.execute(query);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return findByIdMethod(entity,id);
    }

    private <T> long setID(Class<T> entity, List<ColumnInfo> columnsInfo) {
        long id = 0;
        for (ColumnInfo column : columnsInfo) {
            Field field = null;
            try {
                field = entity.getDeclaredField(column.getColumnName());
                field.setAccessible(true);
            } catch (NoSuchFieldException e) {
                System.out.println("NoSuchFieldException");
            }

            if (column.isId()){
                id = getNextIdVal(tableName, column.getColumnName());
                column.setValue(id);
            }
            else {
                column.setValue(field);
            }
        }
        return id;
    }
}
