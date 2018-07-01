package crowdtag.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import crowdtag.hibernate.result.ResponseBodyInfo;
import crowdtag.model.businesslogic.Task;
import crowdtag.model.businesslogic.service.TaskService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testBLTask {

	@Autowired
	TaskService task;
	
	//@Test
	public void testTask() {
		Task one = task.getOptionalTask((long)1, (long)1);
		one.setAnswer(1);
		one.setTime(20);
		one.setTags(new ArrayList<String>());

		ResponseBodyInfo<Task> res = task.finishtask(one);
		assertEquals(res.getErrorText(), "...");
	}
	
	//@Test
	public void testTask2() {
		Task one = task.getOptionalTask((long)2, (long)2);
		one.setAnswer(2);
		one.setTime(20);
		one.setTags(new ArrayList<String>());
			
		task.finishtask(one);
		assertEquals(one.getImagePath(), "https://songzi-picture.oss-cn-shenzhen.aliyuncs.com/2/000000000785.jpg");
	}
	
	@Test
	public void testfinish19_1() {
		Task one = task.getOptionalTask((long)177, (long)19);
		one.setAnswer(2);
		one.setTime(20);
		one.setTags(new ArrayList<String>());
			
		task.finishtask(one);
		assertEquals(one.getImagePath(), "https://songzi-picture.oss-cn-shenzhen.aliyuncs.com/2/000000000785.jpg");
	}
	
	@Test
	public void testfindTasksByCollectionId() {
		ArrayList<Task> tasks = new ArrayList<Task>();
		tasks = task.findTasksByCollectionId((long)1);
		assertEquals(tasks.size(), 272);
	}
}
