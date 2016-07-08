package ro.teamnet.zth.api.em;

import ro.teamnet.zth.api.annotations.Table;
import ro.teamnet.zth.api.database.DBManager;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aimandis on 7/8/2016.
 */
public class EntityManagerImpl implements EntityManager{
    public <T> List<T> findAll(Class<T> entityClass){
        Connection con= DBManager.getConnection();
        List<T> list=new ArrayList<>();
        try {
            Statement st=con.createStatement();
            QueryBuilder queryBuilder=new QueryBuilder();
            queryBuilder.setQueryType(QueryType.SELECT);
            queryBuilder.setTableName(EntityUtils.getTableName(entityClass));
            queryBuilder.addQueryColumns(EntityUtils.getColumns(entityClass));
            String query=queryBuilder.createQuery();
            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
                T t=entityClass.newInstance();
                for(ColumnInfo cf:EntityUtils.getColumns(entityClass)){
                    Object o=rs.getObject(cf.getDbName());
                    Field f=t.getClass().getDeclaredField(cf.getColumnName());
                    f.setAccessible(true);
                    f.set(t,EntityUtils.castFromSqlType(o,cf.getColumnType()));

                }
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
