package testHibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import crowdtag.hibernate.HibernateUtil;
import crowdtag.hibernate.entity.RequesterEntity;
import crowdtag.hibernate.service.RequesterService;

public class testHibernate {

    private static SessionFactory factory;
	private static Session session;
	private static Transaction transaction;
/*
	@Before
	public void init() {
		// 1.创建配置对象
		Configuration config = new Configuration().configure();
		// 2.服务注册对象
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(config.getProperties()).buildServiceRegistry();
		// 3.创建会话工厂
		sessionFactory = config.buildSessionFactory(serviceRegistry);
		// 4.创建会话
		session = sessionFactory.openSession();
		// 5.打开会话
		transaction = session.beginTransaction();
	}
	@After
	public void destory() {
		transaction.commit();
		session.close();
		sessionFactory.close();
	}*/
    
    
    @Before
    public void init() {
        factory = HibernateUtil.getSessionFactory();
        session = factory.openSession();
        transaction = session.beginTransaction();
    }
    
    @Test
    public void test() {
        assertNotNull(factory);
    }

    @Test
	public void testFindAll() {
		Session session = factory.getCurrentSession();
		RequesterEntity re = session.get(RequesterEntity.class, 1);
		assertEquals(re.getName(), "Mike");
	}
    
    @AfterClass
    public static void clean() {
        factory.close();
    }
}