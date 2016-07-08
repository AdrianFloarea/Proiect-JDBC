package ro.teamnet.zth.api.em;

import java.util.List;

/**
 * Created by user on 7/8/2016.
 */
public interface EntityManager {
    public <T> T findById(Class<T> entityClass, Long id);
    public void getNextIdVal(String tableName, String columnIdName);
    public <T> Object insert(T entity);
    public <T> List<T> findAll(Class<T> entityClass);

}
