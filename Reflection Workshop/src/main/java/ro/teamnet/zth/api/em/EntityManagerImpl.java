package ro.teamnet.zth.api.em;

import com.sun.xml.internal.bind.v2.model.core.ID;
import javafx.scene.control.Tab;
import ro.teamnet.zth.api.annotations.Column;
import ro.teamnet.zth.api.annotations.Table;
import ro.teamnet.zth.api.database.DBManager;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ro.teamnet.zth.api.annotations.Id;
import ro.teamnet.zth.api.database.DBManager;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 7/8/2016.
 */
public class EntityManagerImpl implements EntityManager {

    public Long getNextIdVal(String tableName, String columnIdName) {


        try {
            Connection c = DBManager.getConnection();
            DBManager.checkConnection(c);
            Statement st = c.createStatement();
            ResultSet r = st.executeQuery("SELECT max(" + columnIdName + ") FROM " + tableName);

            if (r.next()) {
                System.out.println(r);
                Long id_value = (Long)r.getLong(1);
                id_value++;
                System.out.println(id_value);
                return id_value;
            }

        } catch (SQLException e) {
            System.out.println("Probleme la crearea statementului");
            e.printStackTrace();

        }
        return 0L;

    }


    @Override
    public <T> Object insert(T entity) {
        Connection con = DBManager.getConnection();
        DBManager.checkConnection(con);

        String tableName = EntityUtils.getTableName(entity.getClass());

        List<ColumnInfo> columnsList = EntityUtils.getColumns(entity.getClass());
        Long id = 0L;
        for (ColumnInfo column : columnsList) {
            if (column.isId()) {
                id = getNextIdVal(tableName, column.getDbName());
                //System.out.println(id);
                column.setValue(id);
                break;
            }
        }

        QueryBuilder qb = new QueryBuilder();
        qb.setTableName(tableName);
        qb.addQueryColumns(columnsList);
        qb.setQueryType(QueryType.INSERT);


        String query = qb.createQuery();

        Statement statement = null;
        try {
            statement = con.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return findById(entity.getClass(), id);
    }

    @Override
    public <T> T findById(Class<T> entityClass, Long id) {
        //-	create a connection to DB;

        Connection conn = DBManager.getConnection();

        //-	get table name, columns and fields by annotations using the methods from EntityUtils class;

        String tableName = EntityUtils.getTableName(entityClass);
        List<ColumnInfo> columns = EntityUtils.getColumns(entityClass);
        ColumnInfo ciId = new ColumnInfo();
        for (ColumnInfo column : columns) {
            if (column.isId())
                ciId = column;

        }
        List<Field> fields = EntityUtils.getFieldsByAnnotations(entityClass, Id.class);

        //-	create a Condition object in which you have to set column name and the value of the id;

        Condition condition = new Condition(ciId.getDbName(), id);

        //	create a QueryBuilder object  in which you have to set the name of the table, columns, query type and conditions;
        QueryBuilder qb = new QueryBuilder();
        qb.setTableName(tableName);
        qb.addQueryColumns(columns);
        qb.setQueryType(QueryType.SELECT);
        qb.addCondition(condition);

        //-	call createQuery() method from QueryBuilder.java;
        String query = qb.createQuery();

        //-	create a resultSet object using Statement
        // and execute the query obtained above;

        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {

                T instance = entityClass.newInstance();

                for (ColumnInfo column : columns) {
                    Field f = instance.getClass().getDeclaredField(column.getColumnName());
                    f.setAccessible(true);
                    f.set(instance,
                            EntityUtils.castFromSqlType(resultSet.getObject(column.getDbName()),
                                    column.getColumnType()));
                }
                return instance;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Object entity) {
        Connection conn;
        conn = DBManager.getConnection();
        //entity.getClass().getField()
    }

    @Override
    public <T> List<T> findAll(Class<T> entityClass) {
        Connection con = DBManager.getConnection();
        List<T> list = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            QueryBuilder queryBuilder = new QueryBuilder();
            queryBuilder.setQueryType(QueryType.SELECT);
            queryBuilder.setTableName(EntityUtils.getTableName(entityClass));
            queryBuilder.addQueryColumns(EntityUtils.getColumns(entityClass));
            String query = queryBuilder.createQuery();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                T t = entityClass.newInstance();
                for (ColumnInfo cf : EntityUtils.getColumns(entityClass)) {
                    Object o = rs.getObject(cf.getDbName());
                    Field f = t.getClass().getDeclaredField(cf.getColumnName());
                    f.setAccessible(true);
                    f.set(t, EntityUtils.castFromSqlType(o, cf.getColumnType()));

                }
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
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

            if (column.isId()) {
                id = getNextIdVal(EntityUtils.getTableName(entity), column.getColumnName());
                column.setValue(id);
            } else {
                column.setValue(field);
            }
        }
        return id;
    }


}
