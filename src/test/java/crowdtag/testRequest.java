package crowdtag;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.aliyun.oss.OSSClient;

import crowdtag.hibernate.entity.request.Images;
import crowdtag.hibernate.entity.request.Records;
import crowdtag.hibernate.entity.request.RequestEntity;
import crowdtag.hibernate.entity.request.RequestType;
import crowdtag.hibernate.repository.request.RequestRepository;
import crowdtag.model.businesslogic.Requester;
import crowdtag.model.businesslogic.aliyunoss.AliyunOSSClientUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testRequest {
	
	AliyunOSSClientUtil a;
	@Autowired
	RequestRepository re;
	//@Test
	public void testAdd1() {
		RequestEntity re1;
		ArrayList<Images> images = new ArrayList<Images>();
		re1 = new RequestEntity(1, "一些人", 10,10,RequestType.CLASSTAG, "people");
		re1 = re.saveAndFlush(re1);
		ArrayList<Records> records = new ArrayList<Records>();
		Map<Integer, String> oneContent = new HashMap<Integer, String>();
		oneContent.put(1, "<18");
		oneContent.put(2, "18-30");
		oneContent.put(3, "30-50");
		oneContent.put(4, ">50");
		
		OSSClient ossClient2=AliyunOSSClientUtil.getOSSClient();
		File zipFile2 = new File("/home/songzi/se_file/1.zip");
		ArrayList<File> files2 = AliyunOSSClientUtil.upzipFile(zipFile2,"/home/songzi/se_file/1");
		ArrayList<String> path = AliyunOSSClientUtil.uploadObject2OSS(ossClient2, files2, "songzi-picture", re1.getId().toString()+"/");  
		images.add(new Images(re1,path.get(0),records, "年龄", oneContent));
		images.add(new Images(re1,path.get(1),records, "年龄", oneContent));
		images.add(new Images(re1,path.get(2),records, "年龄", oneContent));
		images.add(new Images(re1,path.get(3),records, "年龄", oneContent));
		images.add(new Images(re1,path.get(4),records, "年龄", oneContent));
		images.add(new Images(re1,path.get(5),records, "年龄", oneContent));
		images.add(new Images(re1,path.get(6),records, "年龄", oneContent));
		images.add(new Images(re1,path.get(7),records, "年龄", oneContent));
		images.add(new Images(re1,path.get(8),records, "年龄", oneContent));
		images.add(new Images(re1,path.get(9),records, "年龄", oneContent));
		images.add(new Images(re1,path.get(10),records, "年龄", oneContent));
		images.add(new Images(re1,path.get(11),records, "年龄", oneContent));
		
		re1.setImages(images);
		re1 = re.saveAndFlush(re1);
	}

	//@Test
	public void testAdd2() {
		RequestEntity re1;
		ArrayList<Images> images = new ArrayList<Images>();
		re1 = new RequestEntity(2, "一些食物", 10,10,RequestType.FRAMETAG, "food");
		re1 = re.saveAndFlush(re1);	
		ArrayList<Records> records = new ArrayList<Records>();
		Map<Integer, String> oneContent = new HashMap<Integer, String>();
		oneContent.put(0, "nothing");
		OSSClient ossClient2=AliyunOSSClientUtil.getOSSClient();
		File zipFile2 = new File("/home/songzi/se_file/2.zip");
		ArrayList<File> files2 = AliyunOSSClientUtil.upzipFile(zipFile2,"/home/songzi/se_file/2");
		ArrayList<String> path = AliyunOSSClientUtil.uploadObject2OSS
				(ossClient2, files2, "songzi-picture", re1.getId().toString()+"/");  
		for(int i=0 ; i<path.size() ; i++) {
			images.add(new Images(re1,path.get(i),records, "圈出图中的食物", oneContent));
		}
		re1.setImages(images);
		re.saveAndFlush(re1);
	}
	
	@Autowired
	Requester r;
	@Test
	public void testAdd3() {

		Map<Integer, String> oneContent = new HashMap<Integer, String>();
		oneContent.put(1, "自行车");
		oneContent.put(2, "摩托车");
		oneContent.put(3, "巴士");
		oneContent.put(4, "火车");
		oneContent.put(5, "其他");
		
		RequestEntity re1 = new RequestEntity();
		re1 = r.addRequest(1, "交通工具的图片", 10, 5, RequestType.CLASSTAG, "vehicle", "/home/songzi/se_file/3.zip", "/home/songzi/se_file/3", "请选择图中主要的车的类型", oneContent);
		//assertEquals(re.getId().equals((long)11), true);
	}
	
	//@Test
	public void testDelete() {
		re.deleteById((long)7);
	}
	@Test
	public void test2() {
		assertEquals(re.findWorkerIdByRequestId(re.findById((long) 4).get()).size(), 2);
	}
	
	@Test
	public void test3() {
		assertEquals(re.findRequestByWorkerId((long) 1).get(0).getId().intValue(), 1);
	}
	
	@Test
	public void test4() {
		assertEquals( re.findImageByWorkerId((long) 1).get(0).getId().intValue(), 1);
	}
	
	@Test
	public void testFindOptionalImagesByWorkerId() {
		Optional<RequestEntity> req = re.findById((long) 4);
		Images i = re.findOptionalImagesByWorkerId(req.get(), 2);
		assertEquals(i.getId().equals((long)7), true);
	}
	
	@Test
	public void testFindTypeByworkerId() {
		ArrayList<String> res = re.findTypeByworkerId((long)2);
		assertEquals(res.get(0), "man");
		assertEquals(res.get(1), "food");
	}
	
	@Test
	public void testFindStrandardByRecords() {
		assertEquals(re.findStrandardByRecords((long) 7),10);
	}
	
	@Test
	public void testFindRecordByWorkerId() {
		assertEquals(re.findRecordByWorkerId(2).get(0).getId().equals((long)2), true);
		assertEquals(re.findRecordByWorkerId(2).get(1).getId().equals((long)7), true);
	}
}
