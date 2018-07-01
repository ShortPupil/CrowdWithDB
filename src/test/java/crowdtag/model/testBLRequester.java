package crowdtag.model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import crowdtag.hibernate.entity.RequesterEntity;
import crowdtag.model.businesslogic.service.RequesterService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testBLRequester {

	@Autowired
	RequesterService requester;
	@Test
	public void testregister() {
		requester.register(new RequesterEntity
				("ruo", "1234", "ruo@123.com","address", "nju"));
	}

	
	@Test
	public void testupdatePassword_1() {
		assertEquals(requester.updatePassword((long)1, "12345", "123").getErrorText(), "wrong old password");	
	}
	
	@Test
	public void testupdatePassword_2() {
		assertEquals(requester.updatePassword((long)1, "12345", "1234").getErrorText(), "null");	
	}
}
