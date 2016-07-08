package ro.teamnet.zth.api.em;

import java.util.List;

/**
 * Created by Aimandis on 7/8/2016.
 */
public interface EntityManager {
     public <T> List<T> findAll(Class<T> entityClass);
}
