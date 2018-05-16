package testHibernate;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import crowdtag.hibernate.entity.RequesterEntity;
import crowdtag.hibernate.entity.WorkerEntity;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
public class testDemo {
	private static SessionFactory sessionFactory;
	private static Session session;
	private static Transaction transaction;

	@Before
	public void init() {
		// 1.创建配置对象
		Configuration config = new Configuration().configure();
		config.addAnnotatedClass(RequesterEntity.class);
		config.addAnnotatedClass(WorkerEntity.class);
		// 2.服务注册对象
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(config .getProperties()).build();
		// 3.创建会话工厂
		sessionFactory = config.buildSessionFactory(serviceRegistry);
		// 4.创建会话
		session = sessionFactory.openSession();
		// 5.打开会话
		transaction = session.beginTransaction();
	}

	@Test
	public void testManyToOne() {
		RequesterEntity customer = new RequesterEntity();
		customer.setName("hls");
		customer.setPassword("1234");
		WorkerEntity order1 = new WorkerEntity();
		order1.setWorkername("order1");
		order1.setPassword("1234");
		WorkerEntity order2 = new WorkerEntity();
		order2.setPassword("1234");
		// 执行save操作
		/**
		 * 先插入Customer在插入Order 3条Insert 先插入1端的数据 再插入多端的数据
		 * 
		 */
		//session.save(customer);
		session.save(order1);
		session.save(order2);
		// 先插入多端的数据 再插入1端的数据 发了三条insert 两条update效率低
		/*
		 * session.save(order1);
		 *  session.save(order2);
		 *   session.save(customer);
		 */
	}
	
	//@Test
	public void testMany2oneGet(){
		//1.若查询多端的对象 默认情况下 只查询多一端的对象 而没有查询关联的 1 的那一端的对象
		Order order=(Order)session.get(Order.class, 1);
		System.out.println(order.getOrderName());
		Customer customer=order.getCustomer();
		System.err.println(customer);
	}
	
	//@Test
	public void testMany2OneGet1(){
		Customer customer=(Customer)session.get(Customer.class, 1);
		System.out.println(customer.getCustomerId());
		System.out.println(customer.getCustomerName());
		System.out.println(customer.getClass());
	}
	//@Test
	public void testMany2OneGet2(){
		Order order=(Order)session.get(Order.class, 1);
		System.out.println(order);
	}
}
