package ro.teamnet.zth.appl.dao;

import ro.teamnet.zth.api.em.EntityManager;
import ro.teamnet.zth.api.em.EntityUtils;
import ro.teamnet.zth.appl.domain.Location;

import java.util.List;
import java.util.Map;

/**
 * Created by Aimandis on 7/11/2016.
 */
public class LocationDao {
    EntityManager entManager;

    public Long getNextIdVal(String columnIdName){
        return entManager.getNextIdVal(EntityUtils.getTableName(Location.class),columnIdName);
    }

    public Location insert(Location entity){
        return (Location)entManager.insert(entity);
    }

    public Location findById(Long id){
        return (Location) entManager.findById(Location.class, id);
    }

    public void delete(Location location){
        entManager.delete(location);
    }

    public List<Location> findAll(){
        return entManager.findAll(Location.class);
    }

    public Location update(Location location){
        return entManager.update(location);
    }

    public List<Location> findByParams( Map<String, Object> params){
        return entManager.findByParams(Location.class, params);
    }

}
