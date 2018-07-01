package crowdtag.model;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import crowdtag.model.businesslogic.service.MapService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testBLMap {

	@Autowired
	MapService map;
	
	@Test
	public void test() {
		Map<String,Integer> res = map.countWorkerByTime();
		System.out.println(res.keySet()+"\n");
		System.out.println(res.keySet().toString()+"\n");
	}

	@Test
	public void testcountUnfinishedByTime() {
		Map<String,Integer> res = map.getAllNeedRecordByTime();
		System.out.println(res.keySet());
		System.out.println(res.values());
	}
}
