package ro.teamnet.zth.api.em;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.junit.Assert;
import org.junit.Test;
import ro.teamnet.zth.api.annotations.Table;
import ro.teamnet.zth.appl.domain.Department;
import ro.teamnet.zth.appl.domain.Employee;
import ro.teamnet.zth.appl.domain.Job;
import ro.teamnet.zth.appl.domain.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aimandis on 7/11/2016.
 */
public class EntityManagerImplTest {

    @Test
    public void testfindByParams()
    {
        EntityManagerImpl ent=new EntityManagerImpl();
        Map<String,Object> mp=new HashMap<String,Object>();
        mp.put("JOB_ID","IT_PROG");

        ArrayList lista= (ArrayList) ent.findByParams(Employee.class,mp);
        System.out.println(lista.size());
        // for(int i=0;i<lista.size();i++)
        // System.out.println(lista.size());
    }


    @Test
    public void testGetNextIdVal(){
        EntityManagerImpl ent = new EntityManagerImpl();
        //System.out.println(ent.getNextIdVal("DEPARTMENTS","DEPARTMENT_ID"));
        Assert.assertTrue("Assert",ent.getNextIdVal("DEPARTMENTS","DEPARTMENT_ID")>0);
    }

    @Test
    public void testFindAll(){
        EntityManagerImpl ent = new EntityManagerImpl();
        Assert.assertTrue(ent.findAll(Location.class) != null);
    }

    @Test
    public void testFindById(){
        EntityManagerImpl ent = new EntityManagerImpl();
        Assert.assertTrue(ent.findById(Department.class, 20L).
                getDepartmentName().
                equals("Marketing"));
    }

    @Test
    public void testInsert(){
        EntityManagerImpl ent = new EntityManagerImpl();
        Department department = new Department();
        department.setDepartmentName("Programmer");
        department.setLocations(1100);

        Assert.assertTrue(((Department)(ent.insert(department))).
                getDepartmentName().equals("Programmer"));
    }
}
