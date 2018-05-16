package testHibernate;

import static org.junit.Assert.*;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;

import crowdtag.hibernate.entity.RequesterEntity;
import crowdtag.hibernate.service.RequesterService;
import crowdtag.hibernate.service.RequesterServiceImpl;

public class testRequesterService {
	
	private static SessionFactory sf = null;
	@BeforeClass
	public static void beforeClass() {
        try {
            sf = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
	
	@Test
	public void testFindAll() {
		Session session = sf.getCurrentSession();
		assertEquals(((RequesterService) session).findAll().size(), 4);
	}

	/*@Test
	public void testSave() {
		RequesterEntity entity = new RequesterEntity();
		entity.setName("Zero");
		entity.setPassword("1234");
		rs.save(entity);
		assertEquals(rs.findAll().size(), 5);
	}
	
	@Test
	public void testDelete() {
		rs.delete(5);
		assertEquals(rs.findAll().size() , 4);
	}*/
}
