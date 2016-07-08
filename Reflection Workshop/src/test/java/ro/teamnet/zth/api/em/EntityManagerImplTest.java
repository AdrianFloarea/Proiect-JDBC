package ro.teamnet.zth.api.em;

import org.junit.Assert;
import org.junit.Test;
import ro.teamnet.zth.appl.domain.Employee;

/**
 * Created by user on 7/8/2016.
 */
public class EntityManagerImplTest {

    @Test
    public void testgetNextIdVal()
    {
       EntityManagerImpl ent=new EntityManagerImpl();
        Long res=ent.getNextIdVal("EMPLOYEES","EMPLOYEE_ID");
        Assert.assertTrue(res==207);



    }
}
