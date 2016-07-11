package ro.teamnet.zth.api.em;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.junit.Assert;
import org.junit.Test;
import ro.teamnet.zth.api.annotations.Table;
import ro.teamnet.zth.appl.domain.Department;
import ro.teamnet.zth.appl.domain.Employee;
import ro.teamnet.zth.appl.domain.Job;
import ro.teamnet.zth.appl.domain.Location;

/**
 * Created by Aimandis on 7/11/2016.
 */
public class EntityManagerImplTest {

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
        Assert.assertTrue(ent.findById(Department.class, 10L).getDepartmentName().equals("Administration"));
    }

    @Test
    public void testInsert(){
        EntityManagerImpl ent = new EntityManagerImpl();
        Department department = new Department();
        department.setDepartmentName("Programmer");
        department.setLocations(40);

        Assert.assertTrue(((Department)(ent.insert(department))).
                getDepartmentName().equals("Programmer"));
    }

}
