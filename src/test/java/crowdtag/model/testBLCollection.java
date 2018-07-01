package crowdtag.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import crowdtag.hibernate.entity.request.RequestEntity;
import crowdtag.hibernate.repository.request.RequestRepository;
import crowdtag.hibernate.result.ResponseBodyInfo;
import crowdtag.model.businesslogic.Collection;
import crowdtag.model.businesslogic.service.CollectionService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testBLCollection {

	@Autowired
	CollectionService collection;
	@Autowired
	RequestRepository re;
	
	//@Test
	public void testgetAds() {
		ArrayList<Collection> res = collection.getAds((long)2, 10);
		System.out.println(res);
		assertEquals(res.size(), 0);
	}

	@Test
	public void testsearch() {
		ArrayList<String> a = new ArrayList<String>();
		ArrayList<String> b = new ArrayList<String>();
		b.add("AreaTag");
		/*a.add("food");
		a.add("cats");*/
		ArrayList<Collection> res = collection.search(null, b, a, null);
		System.out.println();
		System.out.println(res.get(0).getId());
		assertEquals(res.size(),2);
	}
	
	@Test
	public void testsearch2() {
		ArrayList<String> a = new ArrayList<String>();
		ArrayList<String> b = new ArrayList<String>();
		b.add("FrameTag");
		/*a.add("food");
		a.add("cats");*/
		ArrayList<Collection> res = collection.search("", b, null, null);
		System.out.println();
		System.out.println(res.get(0).getId());
		assertEquals(res.size(),7);
	}
	
	@Test
	public void testsearch3() {
		ArrayList<String> a = new ArrayList<String>();
		ArrayList<String> b = new ArrayList<String>();
		//b.add("FrameTag");
		a.add("food");
		a.add("cats");
		ArrayList<Collection> res = collection.search(null, b, a, null);
		System.out.println();
		System.out.println(res.get(0).getId());
		assertEquals(res.size(),2);
	}
	
	@Test
	public void testgetState_process() {
		ResponseBodyInfo<Double> res = collection.getState_process((long)1);
		assertEquals(res.getData(),0.73, 0.01);
	}
	
	@Test
	public void test() {
		ArrayList<Collection> c = collection.getFinishedCollectionByWorkerId((long)177);
		assertEquals(c.size() , 4);
	}
}
