package ro.teamnet.zth.appl.dao;

import ro.teamnet.zth.api.em.EntityManager;
import ro.teamnet.zth.api.em.EntityUtils;
import ro.teamnet.zth.appl.domain.Department;
import ro.teamnet.zth.appl.domain.Location;

import java.util.List;
import java.util.Map;

/**
 * Created by Aimandis on 7/11/2016.
 */
public class DepartmentDao {   EntityManager entManager;

    public Long getNextIdVal(String columnIdName){
        return entManager.getNextIdVal(EntityUtils.getTableName(Department.class),columnIdName);
    }

    public Department insert(Department entity){
        return (Department)entManager.insert(entity);
    }

    public Department findById(Long id){
        return (Department) entManager.findById(Department.class, id);
    }

    public void delete(Department department){
        entManager.delete(department);
    }

    public List<Department> findAll(){
        return entManager.findAll(Department.class);
    }

    public Department update(Department department){
        return entManager.update(department);
    }

    public List<Department> findByParams(Map<String, Object> params){
        return entManager.findByParams(ro.teamnet.zth.appl.domain.Department.class, params);
    }
}
