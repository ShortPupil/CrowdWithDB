package crowdtag.hibernate;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import crowdtag.hibernate.repository.request.ImagesRepository;
import crowdtag.hibernate.repository.request.RecordsRepository;
import crowdtag.hibernate.repository.request.RequestRepository;
import crowdtag.model.businesslogic.aliyunoss.AliyunOSSClientUtil;
import crowdtag.model.businesslogic.service.RequesterService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testRequest {
	
	AliyunOSSClientUtil a;
	@Autowired
	RequestRepository re;
	@Autowired
	RecordsRepository record;
	@Autowired
	ImagesRepository images;
	@Autowired
	RequesterService r;
	//@Test
	public void testAdd1() {
		RequestEntity re1;
		ArrayList<Images> images = new ArrayList<Images>();
		re1 = new RequestEntity(1, "人","一些人", 10,10,RequestType.CLASSTAG, "people", 0, 0);
		re1 = re.saveAndFlush(re1);
		ArrayList<Records> records = new ArrayList<Records>();
		Map<Integer, String> oneContent = new HashMap<Integer, String>();
		oneContent.put(1, "<18");
		oneContent.put(2, "18-30");
		oneContent.put(3, "30-50");
		oneContent.put(4, ">50");
		File zipfile = new File("/home/songzi/se_file/1");
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
	/*public void testAdd2() {
		RequestEntity re1;
		ArrayList<Images> images = new ArrayList<Images>();
		re1 = new RequestEntity(2, "食物","一些食物", 10,10,RequestType.FRAMETAG, "food", 0, 0);
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
	RequesterService r;
	//@Test
	public void testAdd3_1() {

		Map<Integer, String> oneContent = new HashMap<Integer, String>();
		oneContent.put(1, "<18");
		oneContent.put(2, "18-30");
		oneContent.put(3, "30-50");
		oneContent.put(4, ">50");
		
		RequestEntity re1 = new RequestEntity();
		re1 = r.addRequest(1, "人","人的图片", 10, 5, RequestType.CLASSTAG, 
				"man",0 , 0, "/home/songzi/se_file/man.zip", 
				"猜猜图中人物的年龄", oneContent);
		assertEquals(re1.getRequesterId(), (long)1);
		//assertEquals(re.getId().equals((long)11), true);
	}
	
	//@Test
	public void testAdd3_1_2() {

		Map<Integer, String> oneContent = new HashMap<Integer, String>();
		oneContent.put(1, "<18");
		oneContent.put(2, "18-20");
		oneContent.put(2, "20-30");
		oneContent.put(3, "30-50");
		oneContent.put(4, ">50");
		
		RequestEntity re1 = new RequestEntity();
		re1 = r.addRequest(2, "人","一些人的图片", 5, 5, RequestType.CLASSTAG, 
				"man",0,0, "/home/songzi/se_file/man_2.zip", 
				"猜猜图中人物的年龄", oneContent);
		assertEquals(re1.getRequesterId(), (long)1);
		//assertEquals(re.getId().equals((long)11), true);
	}
	
	//@Test
	public void testAdd3_2() {

		Map<Integer, String> oneContent = new HashMap<Integer, String>();
		oneContent.put(1, "自行车");
		oneContent.put(2, "摩托车");
		oneContent.put(3, "巴士");
		oneContent.put(4, "火车");
		oneContent.put(5, "其他");
		
		RequestEntity re1 = new RequestEntity();
		re1 = r.addRequest(2, "交通工具","交通工具的图片", 10, 10, RequestType.CLASSTAG, 
				"vehicle", 0,0,"/home/songzi/se_file/ve.zip", 
				"请选择图中主要的车的类型", oneContent);
		assertEquals(re1.getRequesterId(), (long)1);
		//assertEquals(re.getId().equals((long)11), true);
	}
	
	//@Test
	public void testAdd3_3() {
		Map<Integer, String> oneContent = new HashMap<Integer, String>();
		RequestEntity re1 = new RequestEntity();
		re1 = r.addRequest(3, "猫","猫的图片", 5, 5, RequestType.FRAMETAG, 
				"cat",0,0, "/home/songzi/se_file/cat.zip", 
				"请圈出图中的猫", oneContent);
		assertEquals(re1.getRequesterId(), (long)1);
		//assertEquals(re.getId().equals((long)11), true);
	}
	
	//@Test
	public void testAdd3_4() {

		Map<Integer, String> oneContent = new HashMap<Integer, String>();
		
		RequestEntity re1 = new RequestEntity();
		re1 = r.addRequest(2, "食物","食物的图片", 10, 20, RequestType.AREATAG, 
				"food", 0,0,"/home/songzi/se_file/food.zip", 
				"请选择图中的食物", oneContent);
		assertEquals(re1.getRequesterId(), (long)1);
		//assertEquals(re.getId().equals((long)11), true);
	}
	
	//@Test
	public void testAdd3_5() {

		Map<Integer, String> oneContent = new HashMap<Integer, String>();
		
		RequestEntity re1 = new RequestEntity();
		re1 = r.addRequest(3, "橘子","橘子的图片", 10, 20, RequestType.FRAMETAG, 
				"food", 0,0,"/home/songzi/se_file/orange.zip", 
				"请选择图中的橘子", oneContent);
		assertEquals(re1.getRequesterId(), (long)1);
		//assertEquals(re.getId().equals((long)11), true);
	}
	
	//@Test
	public void testdelete() {
		//ArrayList<RequestEntity> request = new ArrayList<RequestEntity>();
		re.deleteById((long)16);
		re.deleteById((long)17);
		re.deleteById((long)18);
	}
	
	//@Test
	public void testAdd3_7() {
		Map<Integer, String> oneContent = new HashMap<Integer, String>();
		
		RequestEntity re1 = new RequestEntity();
		re1 = r.addRequest(1, "人","一些人的图片", 10, 20, RequestType.FRAMETAG, 
				"man", 0,0,"/home/songzi/se_file/man_3.zip", 
				"请圈出图片中的男性", oneContent);
		assertEquals(re1.getRequesterId(), (long)1);
	}
	
	
	
	//@Test
	public void testAdd3_8() {
		Map<Integer, String> oneContent = new HashMap<Integer, String>();
		
		RequestEntity re1 = new RequestEntity();
		re1 = r.addRequest(2, "显示屏","该数据集为一些包含显示屏的图片", 20, 10, RequestType.FRAMETAG, 
				"screen", 0,0,"/home/songzi/se_file/screen.zip", 
				"请圈出图片中的显示屏", oneContent);
		assertEquals(re1.getRequesterId(), (long)2);
	}
	
	//@Test
	public void testAdd3_9() {
		Map<Integer, String> oneContent = new HashMap<Integer, String>();
		oneContent.put(1, "未受伤");
		oneContent.put(1, "轻微");
		oneContent.put(1, "中等");
		oneContent.put(1, "较重");
		oneContent.put(1, "危及生命");
		RequestEntity re1 = new RequestEntity();
		re1 = r.addRequest(1, "受伤","该数据集为一些包含受伤的人的图片，需要判断受伤程度", 20, 10, RequestType.CLASSTAG, 
				"hurt", 70,0,"/home/songzi/se_file/hurt.zip", 
				"判断图中人的受伤程度", oneContent);
		assertEquals(re1.getRequesterId(), (long)1);
	}
	
	//@Test
	public void testAdd3_10() {
		//蒙古人种（黄色人种）、高加索人种（白色人种）、尼格罗人种（黑色人种）
		Map<Integer, String> oneContent = new HashMap<Integer, String>();
		oneContent.put(1, "蒙古人种（黄色人种）");
		oneContent.put(2, "高加索人种（白色人种）");
		oneContent.put(3, "尼格罗人种（黑色人种）");
		RequestEntity re1 = new RequestEntity();
		re1 = r.addRequest(3, "人","该数据集为一些人的图片", 20, 10, RequestType.CLASSTAG, 
				"race", 60,0,"/home/songzi/se_file/race.zip",
				"判断图中人的的人种", oneContent);
		assertEquals(re1.getRequesterId(), (long)3);
	}
	
	//@Test
	public void testAdd3_11() {
		Map<Integer, String> oneContent = new HashMap<Integer, String>();
		
		RequestEntity re1 = new RequestEntity();
		re1 = r.addRequest(2, "路牌","该数据集为一些包含路牌的图片", 20, 10, RequestType.FRAMETAG, 
				"guidepost", 70,0,"/home/songzi/se_file/guidepost.zip",
				"请框出图中的路牌", oneContent);
		assertEquals(re1.getRequesterId(), (long)2);
	}

	@Test
	public void testDelete() {
		re.deleteById((long)27);	
	}
	
	@Test
		public void testAdd3_12() {
			Map<Integer, String> oneContent = new HashMap<Integer, String>();
			
			RequestEntity re1 = new RequestEntity();
			File file = new File("/home/songzi/se_file/map.zip");
			re1 = r.addRequest(4, "地图","该数据集为一些包含地图的图片", 20, 10, RequestType.FRAMETAG, 
					"guidepost", 60,0,file,
					"请框出地图中南京的位置", oneContent);
			assertEquals(re1.getRequesterId(), (long)4);
		}
	
	@Test
	public void testAdd3_13() {
		Map<Integer, String> oneContent = new HashMap<Integer, String>();
		
		RequestEntity re1 = new RequestEntity();
		File file = new File("/home/songzi/se_file/map_2.zip");
		re1 = r.addRequest(4, "地图","该数据集为一些包含地图的图片", 20, 10, RequestType.AREATAG, 
				"guidepost", 40,0,file,
				"请框出地图中江西的位置", oneContent);
		assertEquals(re1.getRequesterId(), (long)4);
	}
	*/
	//jinmaogou.zip
	
	//@Test
	public void testAdd3_14() {
		Map<Integer, String> oneContent = new HashMap<Integer, String>();
		
		RequestEntity re1 = new RequestEntity();
		File file = new File("/home/songzi/se_file/jinmaogou.zip");
		re1 = r.addRequest(1, "金毛狗","一些包含金毛狗的图片", 15, 10, RequestType.FRAMETAG, 
				"dog", 0,0,file,
				"请框出图中的金毛狗", oneContent);
		assertEquals(re1.getRequesterId(), (long)1);
	}
	
	//@Test
	public void testdelete() {
		re.deleteById((long)31);
	}
	
	@Test
	public void testFindWorkerIdByRequestId() {
		assertEquals(re.findWorkerIdByRequestId(re.getOne((long) 8)).size(), 0);
	}
	
	@Test
	public void testfindRequestByWorkerId() {
		assertEquals(re.findRequestByWorkerId((long) 148).size(), 4);
	}
	
	@Test
	public void testfindImageByWorkerId() {
		assertEquals( re.findImageByWorkerId((long) 148).size(), 17);
	}
	
	@Test
	public void testFindOptionalImagesByWorkerId() {
		RequestEntity req = re.getOne((long) 8);
		ArrayList<Images> i = re.findOptionalImagesByWorkerId(req, 2);
		assertEquals(i.get(0).getId().equals((long)12), true);
	}
	
	@Test
	public void testFindTypeByworkerId() {
		ArrayList<String> res = re.findTypeByworkerId((long)2);
		assertEquals(res.size(),0);
		//assertEquals(res.get(1), "food");
	}
	
	@Test
	public void testFindStrandardByRecords() {
		assertEquals(re.findStrandardByRecords((long) 7),10);
	}
	
	@Test
	public void testFindRecordByWorkerId() {
		assertEquals(re.findRecordByWorkerId((long)148).size(),1);
		//assertEquals(re.findRecordByWorkerId((long)2).get(0).getId().equals((long)7), true);
	}
	
	@Test
	public void testfindRequestByRequesterId() {
		assertEquals(re.findRequestByRequesterId((long)1).size(), 0);
	}
	
	@Test
	public void testDeleteRecord() {
		record.deleteById((long)102);
		record.deleteById((long)103);
		record.deleteById((long)104);
		record.deleteById((long)106);
		record.deleteById((long)108);
		record.deleteById((long)109);
		/*for(long i=49 ; i<=53 ; i++) {
			record.deleteById(i);
		}
		for(long i=55 ; i<=62 ; i++) {
			record.deleteById(i);
		}
		for(long i=65 ; i<=69 ; i++) {
			record.deleteById(i);
		}
		for(long i=83 ; i<=101 ; i++) {
			record.deleteById(i);
		}*/
	}
}
