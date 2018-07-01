package crowdtag.model;

import static org.junit.Assert.*;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import crowdtag.hibernate.entity.AdministratorEntity;
import crowdtag.model.businesslogic.service.AdministratorService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testBLAdministrator {

	@Autowired
	AdministratorService administratorService;
	
	@Test
	public void testGetAdministratorById() {
		assertEquals(administratorService.getAdministratorById((long)1).getName(), "AAA");
	}

	@Test
	public void testGetAdministratorByName() {
		assertEquals(administratorService.getAdministratorByName("new").getId().longValue(), (long)7);
	}
	
	@Test
	public void testlogin() {
		assertEquals(administratorService.login(1, "1234").getPassword(),"1234");
	}
	
	@Test
	@Transactional
	public void testregister() {
		AdministratorEntity administrator = new AdministratorEntity("new", "1234", 0);
		administratorService.register(administrator);
	}
	
	/*以下为测试管理worker的*/
	@Test
	@Transactional
	public void testaddWorkerAndFindAllWorker() {
		administratorService.addWorker("addNew", "1234");
		int size = administratorService.findAllWorker().size();
		assertEquals(size, 140);
		assertEquals(administratorService.findAllWorker().get(size-1).getWorkerEntity().getWorkername(), "addNew");
	}
	@Test
	@Transactional
	public void testdeleteworker() {
		administratorService.deleteByWorkerId((long)139);
		int size = administratorService.findAllWorker().size();
		assertEquals(size, 138);
		assertEquals(administratorService.findAllWorker().get(size-1).getWorkerEntity().getWorkername(), "");
	}
	@Test
	public void testfindRequestByWokerId() {
		assertEquals(administratorService.findTaskByWokerId((long)2).size(),3);
	}
	
	/**以下为测试管理requester的*/
	@Test
	@Transactional
	public void testAddRequesterAndFindAllRequester() {
		administratorService.addRequester("newAdd", "1234", "123@mail.com", "address", "nju");
		int size = administratorService.findAllRequester().size();
		assertEquals(size, 22);
		assertEquals(administratorService.findAllRequester().get(size-1).getName(), "newAdd");
	}
	
	@Test
	@Transactional
	public void testdeleteRequester() {
		administratorService.deleteByRequesterId((long)21);
		int size = administratorService.findAllRequester().size();
		assertEquals(size, 20);
		assertEquals(administratorService.findAllRequester().get(size-1).getName(), "xiong");
	}
	
	@Test
	public void testfindRequestByRequesterId() {
		assertEquals(administratorService.findCollectionByRequesterId((long) 1).size(),2);
		
	}
	
	@Test
	public void testfindAllRequest() {
		assertEquals(administratorService.findAllRequest().size(),11);
	}
}
