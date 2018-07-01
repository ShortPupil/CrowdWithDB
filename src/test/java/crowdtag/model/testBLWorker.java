package crowdtag.model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import crowdtag.hibernate.entity.RequesterEntity;
import crowdtag.model.businesslogic.Worker;
import crowdtag.model.businesslogic.service.RequesterService;
import crowdtag.model.businesslogic.service.WorkerService;

public class testBLWorker{
	@Autowired
	WorkerService worker;
	@Test
	public void testregister() {
		Worker worker1 = new Worker();
		worker.regsiter(worker1);
	}

	
	@Test
	public void testlogin() {
		assertEquals(worker.login((long)177, "123456"), true);	
	}
	
	@Test
	public void testdelete() {
		
	}

}
