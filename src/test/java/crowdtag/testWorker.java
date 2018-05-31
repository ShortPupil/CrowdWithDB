package crowdtag;

import static org.junit.Assert.*;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import crowdtag.hibernate.entity.WorkerEntity;
import crowdtag.hibernate.repository.WorkerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testWorker {

    @Autowired
    private WorkerRepository workerRepository;
    
    @Test
    public void testFindByName() throws Exception {
    	assertEquals(workerRepository.findByName("AAA").size(), 0);
    }
    
    @Test
    public void testAdd() throws Exception {
    	WorkerEntity w1 = new WorkerEntity("aaa", "1234");
    	WorkerEntity w2 = new WorkerEntity("bbb", "1234");
    	WorkerEntity w3 = new WorkerEntity("ccc", "1234");

    	workerRepository.saveAndFlush(w1);
    	workerRepository.saveAndFlush(w2);
    	workerRepository.saveAndFlush(w3);
    }

}
