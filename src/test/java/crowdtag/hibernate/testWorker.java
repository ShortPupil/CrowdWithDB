package crowdtag.hibernate;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import crowdtag.hibernate.entity.WorkerEntity;
import crowdtag.hibernate.entity.WorkerPortraitEntity;
import crowdtag.hibernate.repository.WorkerPortraitRepository;
import crowdtag.hibernate.repository.WorkerRepository;
import crowdtag.model.businesslogic.service.WorkerService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testWorker {

	@Autowired
	private WorkerService ws;
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private WorkerPortraitRepository wr;
    
   // @Test
    public void testFindByName() throws Exception {
    }
    
    //@Test
    public void testFindById() throws Exception {
    	//assertEquals(workerRepository.findById((long)1).get().getRank(), 0);
    }
    
    //@Test
    //@Transactional
    public void testAdd() throws Exception {
    	WorkerEntity w1 = new WorkerEntity("谢", "1234");
    	WorkerEntity w2 = new WorkerEntity("熊", "1234");
    	WorkerEntity w3 = new WorkerEntity("夏", "1234");
    	WorkerEntity w4 = new WorkerEntity("钟", "1234");
    	//甲、乙、丙、丁、戊、己、庚、辛、壬、癸
    	WorkerEntity w5 = new WorkerEntity("甲", "1234");
    	WorkerEntity w6 = new WorkerEntity("乙", "1234");
    	WorkerEntity w7 = new WorkerEntity("丙", "1234");
    	WorkerEntity w8 = new WorkerEntity("丁", "1234");
    	WorkerEntity w9 = new WorkerEntity("戊", "1234");
    	WorkerEntity w10 = new WorkerEntity("己", "1234");
    	WorkerEntity w11 = new WorkerEntity("庚", "1234");
    	WorkerEntity w12 = new WorkerEntity("辛", "1234");
    	WorkerEntity w13 = new WorkerEntity("壬", "1234");
    	WorkerEntity w14 = new WorkerEntity("癸", "1234");
    	//WorkerPortraitEntity p1 = new WorkerPortraitEntity();
    	
    	
    	WorkerPortraitEntity p1 = new WorkerPortraitEntity();
    	p1.setPreference("food");
    	Map<String, Integer> v = new HashMap<String, Integer>();
    	v.put("cat", 1);
		p1.setVice_preference(v);
    	WorkerPortraitEntity p2 = new WorkerPortraitEntity();
    	p2.setPreference("cat");
    	WorkerPortraitEntity p3 = new WorkerPortraitEntity();
    	WorkerPortraitEntity p4 = new WorkerPortraitEntity();
    	WorkerPortraitEntity p5 = new WorkerPortraitEntity();
    	WorkerPortraitEntity p6 = new WorkerPortraitEntity();
    	WorkerPortraitEntity p7 = new WorkerPortraitEntity();
    	WorkerPortraitEntity p8 = new WorkerPortraitEntity();
    	WorkerPortraitEntity p9 = new WorkerPortraitEntity();
    	WorkerPortraitEntity p10 = new WorkerPortraitEntity();
    	WorkerPortraitEntity p11= new WorkerPortraitEntity();
    	WorkerPortraitEntity p12= new WorkerPortraitEntity();
    	WorkerPortraitEntity p13= new WorkerPortraitEntity();
    	WorkerPortraitEntity p14= new WorkerPortraitEntity();
    	workerRepository.saveAndFlush(w1);
    	workerRepository.saveAndFlush(w2);
    	workerRepository.saveAndFlush(w3);
    	workerRepository.saveAndFlush(w4);
    	workerRepository.saveAndFlush(w5);
    	workerRepository.saveAndFlush(w6);
    	workerRepository.saveAndFlush(w7);
    	workerRepository.saveAndFlush(w8);
    	workerRepository.saveAndFlush(w9);
    	workerRepository.saveAndFlush(w10);
    	workerRepository.saveAndFlush(w11);
    	workerRepository.saveAndFlush(w12);
    	workerRepository.saveAndFlush(w13);
    	workerRepository.saveAndFlush(w14);
    	
    	wr.saveAndFlush(p1);
    	wr.saveAndFlush(p2);
    	wr.saveAndFlush(p3);
    	wr.saveAndFlush(p4);
    	wr.saveAndFlush(p5);
    	wr.saveAndFlush(p6);
    	wr.saveAndFlush(p7);
    	wr.saveAndFlush(p8);
    	wr.saveAndFlush(p9);
    	wr.saveAndFlush(p10);
    	wr.saveAndFlush(p11);
    	wr.saveAndFlush(p12);
    	wr.saveAndFlush(p13);
    	wr.saveAndFlush(p14);
    }

    //@Test
    public void testdelete() throws Exception{
    	/*delete(151);delete(152);delete(155);delete(156);delete(157);
    	delete(158);delete(159);delete(160);delete(161);delete(162);
    	delete(163);delete(164);delete(165);delete(166);delete(168);
    	delete(171);delete(172);delete(173);delete(174);delete(175);
    	delete(163);delete(164);delete(165);delete(166);delete(168);
    	*/
    	for(long i=171 ; i<=224 ; i++) {
    		delete(i);
    	}
    }
    
    public void delete(long id){
    	workerRepository.deleteById(id);
    	wr.deleteById(id);
    }
}
