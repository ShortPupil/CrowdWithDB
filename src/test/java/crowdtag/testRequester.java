package crowdtag;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import crowdtag.hibernate.entity.RequesterEntity;
import crowdtag.hibernate.repository.RequesterRepository;
import crowdtag.hibernate.repository.WorkerRepository;
import crowdtag.model.businesslogic.Requester;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testRequester {

	@Autowired
    private RequesterRepository requesterrepository;
	@Test
	public void testAdd() {
		RequesterEntity r1 = new RequesterEntity("AAA", "1234", "AAA@mail.com", null, "address1");
		RequesterEntity r2 = new RequesterEntity("BBB", "1234", "BBB@mail.com", null, "address2");
		RequesterEntity r3 = new RequesterEntity("CCC", "1234", "CCC@mail.com", null, "address3");
		requesterrepository.save(r1);
		requesterrepository.saveAndFlush(r2);
		requesterrepository.saveAndFlush(r3);
	}

}
