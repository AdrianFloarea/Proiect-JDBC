package ro.teamnet.zth.api.em;

import ro.teamnet.zth.api.annotations.Column;
import ro.teamnet.zth.api.database.DBManager;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 7/8/2016.
 */
public class EntityManagerImpl implements EntityManager{
    @Override
    public <T> T findById(Class<T> entityClass, Long id) {
        //-	create a connection to DB;

        Connection conn = DBManager.getConnection();

        //-	get table name, columns and fields by annotations using the methods from EntityUtils class;

        String tableName = EntityUtils.getTableName(entityClass);
        List<ColumnInfo> columns = EntityUtils.getColumns(entityClass);
       // List<Field> fields = EntityUtils.getFieldsByAnnotations(entityClass, );

        //-	create a Condition object in which you have to set column name and the value of the id;

        Condition condition = new Condition(tableName, id);

        //	create a QueryBuilder object  in which you have to set the name of the table, columns, query type and conditions;
        QueryBuilder qb = new QueryBuilder();
        qb.setTableName(tableName);
        qb.addQueryColumns(columns);
        qb.setQueryType(QueryType.SELECT);
        qb.addCondition(condition);

        //-	call createQuery() method from QueryBuilder.java;
        qb.createQuery();

        //-	create a resultSet object using Statement
        // and execute the query obtained above;

        //ResultSet rs =

        return null;
    }

    @Override
    public void getNextIdVal(String tableName, String columnIdName) {

    }

    @Override
    public <T> Object insert(T entity) {
        return null;
    }

    @Override
    public <T> List<T> findAll(Class<T> entityClass) {
        return null;
    }
}
