package crowdtag.hibernate;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import crowdtag.hibernate.entity.RequesterEntity;
import crowdtag.hibernate.repository.RequesterRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testRequester {

	@Autowired
    private RequesterRepository requesterrepository;
	//@Test
	public void testAdd() {
		RequesterEntity r1 = new RequesterEntity("AAA", "1234", "AAA@mail.com",  "address1", "nju");
		RequesterEntity r2 = new RequesterEntity("BBB", "1234", "BBB@mail.com",  "address2", "nju");
		RequesterEntity r3 = new RequesterEntity("CCC", "1234", "CCC@mail.com",  "address3", "nju");
		requesterrepository.save(r1);
		requesterrepository.saveAndFlush(r2);
		requesterrepository.saveAndFlush(r3);
	}

	//@Test
	public void testFindByName() {
		assertEquals(requesterrepository.findByName("AAA").get(0).getId().longValue(), (long)1);
		requesterrepository.deleteById((long)1);
		requesterrepository.deleteById((long)2);
		requesterrepository.deleteById((long)3);
	}
}
