package crowdtag.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import crowdtag.hibernate.entity.RequesterEntity;
import crowdtag.hibernate.entity.WorkerEntity;
import crowdtag.hibernate.entity.WorkerPortraitEntity;
import crowdtag.hibernate.entity.request.RequestEntity;
import crowdtag.hibernate.entity.request.RequestType;
import crowdtag.hibernate.entity.request.State;
import crowdtag.hibernate.repository.WorkerPortraitRepository;
import crowdtag.hibernate.result.ResponseBodyInfo;
import crowdtag.model.businesslogic.Collection;
import crowdtag.model.businesslogic.DataCollection;
import crowdtag.model.businesslogic.Task;
import crowdtag.model.businesslogic.Worker;
import crowdtag.model.businesslogic.service.CollectionService;
import crowdtag.model.businesslogic.service.DataCollectionService;
import crowdtag.model.businesslogic.service.GiftService;
import crowdtag.model.businesslogic.service.RequesterService;
import crowdtag.model.businesslogic.service.TaskService;
import crowdtag.model.businesslogic.service.WorkerService;
import crowdtag.model.statistics.calculate;

@Controller
public class RequesterController {
	@Autowired
	CollectionService collectionService;
	@Autowired
	GiftService giftService;
	@Autowired
	RequesterService requesterService;
	@Autowired
	TaskService taskService;
	@Autowired
	WorkerService workerService;
	@Autowired
	DataCollectionService dataCollectionService;
	
	@Autowired
	calculate Calculate;
	
	public String requesterID;
	public long worker_id;
	
	/**
	 * 任务发起者注册
	 * 
	 * 
	 */
	@RequestMapping(value="/RequesterRegister",method=RequestMethod.GET)
	public String toRequesterRegister() {
		return "RequesterRegister";
	}
	
	@RequestMapping(value="/RequesterRegister",method=RequestMethod.POST)
	public ModelAndView requesterRegister
	(@RequestParam(value="name",required=false) String name,
	 @RequestParam(value="password",required=false) String password,
	 @RequestParam(value="email",required=false) String email,
	 @RequestParam(value="company",required=false) String company,
	 @RequestParam(value="address",required=false) String address) {
		RequesterEntity newRequesterEntity=new RequesterEntity(name,password,email,address,company);
		System.out.println(newRequesterEntity.getName());
		
		RequesterEntity requesterEntity=requesterService.register(newRequesterEntity);
		System.out.println(newRequesterEntity.getId());
		String id=requesterEntity.getId()+"";
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/RequesterLogin");
		modelAndView.addObject("id", id);
		modelAndView.addObject("error","nothing");
		return modelAndView;
	}
	
	/**
	 * 任务发起者登录
	 * 
	 * 
	 */
	@RequestMapping(value="/RequesterLogin",method=RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/RequesterLogin");
		return modelAndView;
	}
	@RequestMapping(value="/RequesterLogin",method=RequestMethod.POST)
	public ModelAndView loginFromRegister(
			@RequestParam(value="id",required=false) String ID,
			@RequestParam(value="password",required=false) String password) {
		long id=Long.parseLong(ID);
		RequesterEntity requesterEntity=requesterService.login(id, password);
		ModelAndView modelAndView=new ModelAndView();
		if(requesterEntity!=null) {
			modelAndView.setViewName("/RequesterHome");
			modelAndView.addObject("id", ID);
			requesterID=ID;
			return modelAndView;
		}
		else {
			modelAndView.setViewName("/RequesterLogin");
			modelAndView.addObject("id",ID);
			modelAndView.addObject("error","error");
			return modelAndView;
		}
		
	}
	
	/**
	 * 
	 * 
	 * 任务发起者主页
	 */
	@RequestMapping(value="/RequesterHome",method=RequestMethod.GET)
	public ModelAndView toRequesterHome() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/RequesterHome");
		modelAndView.addObject("id",requesterID);
		
		return modelAndView;
	}
	
	/**
	 * 
	 * 
	 * 任务创建导航
	 */
	@RequestMapping(value="/RequestCreate",method=RequestMethod.GET)
	public ModelAndView requestCreateGuide() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("id",requesterID);
		modelAndView.setViewName("/RequestCreate");
		
		return modelAndView;
	}
	
	/**
	 * 
	 * 
	 * 分类标注任务创建
	 */
	@RequestMapping(value="/RequestCreateClassTag",method=RequestMethod.GET)
	public ModelAndView toRequestCreateClassTag() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("id",requesterID);
		modelAndView.setViewName("/RequestCreateClassTag");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/RequestCreateClassTag",method=RequestMethod.POST)
	public ModelAndView requestCreateClassTag(
			@RequestParam(value="requesterID",required=false) String requesterID,
			@RequestParam(value="requestType",required=false) String requestType,
			@RequestParam(value="requestName",required=false) String requestName,
			@RequestParam(value="requestDescription",required=false) String requestDescription,
			@RequestParam(value="requestKind",required=false) String requestKind,
			@RequestParam(value="requestPeople",required=false) int requestPeople,
			@RequestParam(value="requestPoint",required=false) int requestPoint,
			@RequestParam(value="efficiencyLimit",required=false) String efficiencyLimit,
			@RequestParam(value="accuracyLimit",required=false) String accuracyLimit,
			@RequestParam(value="requestQuestion",required=false) String requestQuestion,
			@RequestParam(value="requestChoice1",required=false) String requestChoice1,
			@RequestParam(value="requestChoice2",required=false) String requestChoice2,
			@RequestParam(value="requestChoice3",required=false) String requestChoice3,
			@RequestParam(value="requestChoice4",required=false) String requestChoice4,
			@RequestParam(value="requestChoice5",required=false) String requestChoice5,
			@RequestParam(value="requestChoice6",required=false) String requestChoice6,
			@RequestParam(value="requestPicture",required=false) MultipartFile mFile) throws IOException {
		ModelAndView modelAndView=new ModelAndView();
		long requesterId=Long.parseLong(requesterID);
		double efficiency_limit=Double.parseDouble(efficiencyLimit);
		double accuracy_limit=Double.parseDouble(accuracyLimit);
		
		File zipFile=null;
		InputStream ins=mFile.getInputStream();
		zipFile=new File(mFile.getOriginalFilename());
		inputStreamToFile(ins,zipFile);
		
		Map<Integer,String> oneContent=new HashMap<Integer,String>();
		oneContent.put(1, requestChoice1);
		oneContent.put(2, requestChoice2);
		
		ArrayList<String> questionList=new ArrayList<String>();
		questionList.add(requestChoice3);
		questionList.add(requestChoice4);
		questionList.add(requestChoice5);
		questionList.add(requestChoice6);
		int index=3;
		for(int i=0;i<questionList.size();i++) {
			if(!questionList.get(i).equals("")) {
				oneContent.put(index, questionList.get(i));
				index++;
			}
		}
		
		
		RequestType type = requesterService.toRequestType(requestType);
		RequestEntity requestEntity=requesterService.addRequest(
				requesterId, requestName, 
				requestDescription, requestPeople, 
				requestPoint, type, requestKind, 
				accuracy_limit, efficiency_limit, 
				zipFile, requestQuestion, oneContent);
				
		
		modelAndView.setViewName("/RequestCreateClassTag");
		modelAndView.addObject("id",requesterID);
		return modelAndView;
	}
	
	/**
	 * 
	 * 
	 * 标框标注任务创建
	 */
	@RequestMapping(value="/RequestCreateFrameTag",method=RequestMethod.GET)
	public ModelAndView toRequestCreateFrameTag() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("id",requesterID);
		modelAndView.setViewName("/RequestCreateFrameTag");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/RequestCreateFrameTag",method=RequestMethod.POST)
	public ModelAndView requestCreateFrameTag(
			@RequestParam(value="requesterID",required=false) String requesterID,
			@RequestParam(value="requestType",required=false) String requestType,
			@RequestParam(value="requestName",required=false) String requestName,
			@RequestParam(value="requestDescription",required=false) String requestDescription,
			@RequestParam(value="requestKind",required=false) String requestKind,
			@RequestParam(value="requestPeople",required=false) int requestPeople,
			@RequestParam(value="requestPoint",required=false) int requestPoint,
			@RequestParam(value="efficiencyLimit",required=false) String efficiencyLimit,
			@RequestParam(value="accuracyLimit",required=false) String accuracyLimit,
			@RequestParam(value="requestQuestion",required=false) String requestQuestion,
			@RequestParam(value="requestPicture",required=false) MultipartFile mFile
			) throws IOException{
		
		ModelAndView modelAndView = new ModelAndView();
		long requesterId=Long.parseLong(requesterID);
		double efficiency_limit=Double.parseDouble(efficiencyLimit);
		double accuracy_limit=Double.parseDouble(accuracyLimit);
		
		File zipFile=null;
		InputStream ins=mFile.getInputStream();
		zipFile=new File(mFile.getOriginalFilename());
		inputStreamToFile(ins,zipFile);
		
		Map<Integer,String> oneContent=new HashMap<Integer,String>();
		RequestType type = requesterService.toRequestType(requestType);
		RequestEntity requestEntity=requesterService.addRequest(
				requesterId, requestName, 
				requestDescription, requestPeople, 
				requestPoint, type, requestKind, 
				accuracy_limit, efficiency_limit, 
				zipFile, requestQuestion, oneContent);
				
		modelAndView.setViewName("/RequestCreateFrameTag");
		modelAndView.addObject("id",requesterID);
		return modelAndView;
	}
	
	/**
	 * 
	 * 
	 * 区域标注任务创建
	 */
	@RequestMapping(value="/RequestCreateAreaTag",method=RequestMethod.GET)
	public ModelAndView toRequestCreateTag() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("id",requesterID);
		modelAndView.setViewName("/RequestCreateAreaTag");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/RequestCreateAreaTag",method=RequestMethod.POST)
	public ModelAndView requestCreateAreaTag(
			@RequestParam(value="requesterID",required=false) String requesterID,
			@RequestParam(value="requestType",required=false) String requestType,
			@RequestParam(value="requestName",required=false) String requestName,
			@RequestParam(value="requestDescription",required=false) String requestDescription,
			@RequestParam(value="requestKind",required=false) String requestKind,
			@RequestParam(value="requestPeople",required=false) int requestPeople,
			@RequestParam(value="requestPoint",required=false) int requestPoint,
			@RequestParam(value="efficiencyLimit",required=false) String efficiencyLimit,
			@RequestParam(value="accuracyLimit",required=false) String accuracyLimit,
			@RequestParam(value="requestQuestion",required=false) String requestQuestion,
			@RequestParam(value="requestPicture",required=false)  MultipartFile mFile
			) throws IOException{
		ModelAndView modelAndView = new ModelAndView();
		long requesterId=Long.parseLong(requesterID);
		double efficiency_limit=Double.parseDouble(efficiencyLimit);
		double accuracy_limit=Double.parseDouble(accuracyLimit);
		
		File zipFile=null;
		InputStream ins=mFile.getInputStream();
		zipFile=new File(mFile.getOriginalFilename());
		inputStreamToFile(ins,zipFile);
		
		Map<Integer,String> oneContent=new HashMap<Integer,String>();
		RequestType type = requesterService.toRequestType(requestType);
		RequestEntity requestEntity=requesterService.addRequest(
				requesterId, requestName, 
				requestDescription, requestPeople, 
				requestPoint, type, requestKind, 
				accuracy_limit, efficiency_limit, 
				zipFile, requestQuestion, oneContent);
		
		modelAndView.setViewName("/RequestCreateAreaTag");
		modelAndView.addObject("id",requesterID);
		return modelAndView;
	}
	
	/**
	 * 
	 * 
	 * 修改基本资料
	 */
	@RequestMapping(value="/RequesterCenter",method=RequestMethod.GET)
	public ModelAndView toRequesterCenter() {
		ModelAndView modelAndView = new ModelAndView();
		long requesterId=Long.parseLong(requesterID);
		RequesterEntity requester=requesterService.getRequesterById(requesterId);
		
		System.out.println(requester.getEmail());
		modelAndView.addObject("id", requesterID);
		modelAndView.addObject("name",requester.getName());
		modelAndView.addObject("email",requester.getEmail());
		modelAndView.addObject("company",requester.getCompany());
		modelAndView.addObject("address",requester.getAddress());
		
		
		modelAndView.setViewName("/RequesterCenter");
		return modelAndView;
	}
	
	@RequestMapping(value="/RequesterCenter",method=RequestMethod.POST)
	public ModelAndView requesterCenter(
			@RequestParam(value="requesterID") String requester_ID,
			@RequestParam(value="email") String email,
			@RequestParam(value="company") String company,
			@RequestParam(value="address") String address) {
		ModelAndView modelAndView = new ModelAndView();
		long requesterId=Long.parseLong(requesterID);
		RequesterEntity requester=requesterService.getRequesterById(requesterId);
		
		requester.setEmail(email);
		requester.setCompany(company);
		requester.setAddress(address);
		
		RequesterEntity updateRequester=requesterService.updateBasicInfo(requester);
		
		modelAndView.addObject("id", requesterID);
		modelAndView.addObject("name",updateRequester.getName());
		modelAndView.addObject("email",updateRequester.getEmail());
		System.out.println(updateRequester.getEmail());
		modelAndView.addObject("company",updateRequester.getCompany());
		modelAndView.addObject("address",updateRequester.getAddress());
		
		modelAndView.setViewName("/RequesterCenter");
		return modelAndView;
	}
	
	/**
	 * 
	 * 
	 * 修改密码
	 */
	@RequestMapping(value="/RevisePassword",method=RequestMethod.GET)
	public ModelAndView toRevisePassword() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("id",requesterID);
		modelAndView.addObject("error","");
		modelAndView.setViewName("/RevisePassword");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/RevisePassword",method=RequestMethod.POST)
	public ModelAndView revisePassword(
			@RequestParam(value="oldPassword",required=false) String oldPassword,
			@RequestParam(value="newPassword",required=false) String newPassword) {
		ModelAndView modelAndView = new ModelAndView();
		Long requesterId=Long.parseLong(requesterID);
		ResponseBodyInfo<RequesterEntity> res=requesterService.updatePassword(
				requesterId, newPassword, oldPassword);
		int errorCode=res.getErrorCode();
		String error="success";
		if(errorCode==2)
			error="新密码与旧密码一致！请重新输入！";
		else if(errorCode==3)
			error="旧密码输入错误！请重新输入！";
		System.out.println(error);
		modelAndView.setViewName("/RevisePassword");
		modelAndView.addObject("error",error);
		modelAndView.addObject("id",requesterID);
		return modelAndView;
	}
	
	/**
	 * 
	 * 
	 * 任务管理
	 */
	@RequestMapping(value="/RequestManage",method=RequestMethod.GET)
	public ModelAndView requestManage() {
		ModelAndView modelAndView = new ModelAndView();
		long requesterId=Long.valueOf(requesterID);
		ArrayList<Collection> collectionList=requesterService.findRequestByRequesterId(requesterId);
		for(int i=0;i<collectionList.size();i++) {
			Long id=collectionList.get(i).getId();
			ResponseBodyInfo<Double> res=collectionService.getState_process(id);
			double processState=res.getData();
			collectionList.get(i).setState_process(processState*100);
		}
		modelAndView.addObject("id", requesterID);
		modelAndView.addObject("collectionlist",collectionList);
		modelAndView.setViewName("/RequestManage");
		return modelAndView;
	}
	
	/**
	 * 
	 * 
	 * 正在进行中任务
	 */
	@RequestMapping(value="/RequestManageING",method=RequestMethod.GET)
	public ModelAndView requestManageING() {
		ModelAndView modelAndView = new ModelAndView();
		long requesterId=Long.parseLong(requesterID);
		ArrayList<Collection> collectionList=requesterService.findRequestByRequesterId(requesterId);
		ArrayList<Collection> collectionList_ing=new ArrayList<Collection>();
		for(int i=0;i<collectionList.size();i++) {
			Long id=collectionList.get(i).getId();
			ResponseBodyInfo<Double> res=collectionService.getState_process(id);
			double processState=res.getData();
			collectionList.get(i).setState_process(processState*100);
			if(collectionList.get(i).getState()==State.PROCESSING)
				collectionList_ing.add(collectionList.get(i));
		}
		modelAndView.addObject("id",requesterID);
		modelAndView.addObject("collectionlist",collectionList_ing);
		modelAndView.setViewName("/RequestManageING");
		return modelAndView;
	}
	
	/**
	 * 
	 * 
	 * 已完成任务
	 */
	@RequestMapping(value="/RequestManageED",method=RequestMethod.GET)
	public ModelAndView requestManageED() {
		ModelAndView modelAndView = new ModelAndView();
		Long requesterId=Long.parseLong(requesterID);
		ArrayList<Collection> collectionList=requesterService.findRequestByRequesterId(requesterId);
		ArrayList<Collection> collectionList_ed=new ArrayList<Collection>();
		for(int i=0;i<collectionList.size();i++) {
			Long id=collectionList.get(i).getId();
			ResponseBodyInfo<Double> res=collectionService.getState_process(id);
			double processState=res.getData();
			collectionList.get(i).setState_process(processState*100);
			if(collectionList.get(i).getState()==State.COMPLETED)
				collectionList_ed.add(collectionList.get(i));
		}
		
		modelAndView.addObject("collectionlist",collectionList_ed);
		modelAndView.addObject("id",requesterID);
		modelAndView.setViewName("/RequestManageED");
		return modelAndView;
	}
	
	/**
	 * 
	 * 
	 * 分类标注任务详情
	 */
	@RequestMapping(value="/RequestDetailClassTag",method=RequestMethod.GET)
	public ModelAndView requestDetailClassTag(
			@RequestParam(value="collectionid",required=false) String collectionID,
			@RequestParam(value="processstate",required=false) double process_state) {
		ModelAndView modelAndView = new ModelAndView();
		Long collectionId=Long.parseLong(collectionID);
		ArrayList<DataCollection> dataCollectionList=dataCollectionService.getDataById(collectionId);
		
		for(int i=0;i<dataCollectionList.size();i++) {
			String answer=dataCollectionList.get(i).getOneContent().get(dataCollectionList.get(i).getAnswers());
	        dataCollectionList.get(i).setAnswer(answer);	
		}
		
		modelAndView.addObject("processState",process_state);
		modelAndView.addObject("id",requesterID);
		modelAndView.addObject("dataCollectionList",dataCollectionList);
		
		modelAndView.setViewName("/RequestDetailClassTag");
		return modelAndView;
	}
	
	/**
	 * 
	 * 
	 * 标框标注任务详情
	 */
	@RequestMapping(value="/RequestDetailFrameTag",method=RequestMethod.GET)
	public ModelAndView requestDetailFrameTag(
			@RequestParam(value="collectionid",required=false) String collectionID,
			@RequestParam(value="processstate",required=false) double process_state) {
		ModelAndView modelAndView = new ModelAndView();
		Long collectionId=Long.parseLong(collectionID);
		ArrayList<DataCollection> dataCollectionList=dataCollectionService.getDataById(collectionId);
		modelAndView.addObject("dataCollectionList",dataCollectionList);
		System.out.println(dataCollectionList.size());
		DataCollection dataCollection = dataCollectionList.get(2);
		System.out.println("路径是："+dataCollection.getPath());
		System.out.println("工人姓名是："+dataCollection.getWorkername());
		System.out.println("工人编号是："+dataCollection.getId());
		modelAndView.addObject("processState",process_state);
		modelAndView.addObject("id",requesterID);
		modelAndView.setViewName("/RequestDetailFrameTag");
		return modelAndView;
	}
	
	/**
	 * 
	 * 
	 * 区域标注任务详情
	 */
	@RequestMapping(value="/RequestDetailAreaTag",method=RequestMethod.GET)
	public ModelAndView requestDetailAreaTag(
			@RequestParam(value="collectionid",required=false) String collectionID,
			@RequestParam(value="processstate",required=false) double process_state) {
		ModelAndView modelAndView = new ModelAndView();
		Long collectionId=Long.parseLong(collectionID);
		ArrayList<DataCollection> dataCollectionList=dataCollectionService.getDataById(collectionId);
		
		System.out.println(dataCollectionList.get(0).getPath());
		modelAndView.addObject("dataCollectionList",dataCollectionList);
		modelAndView.addObject("processState",process_state);
		modelAndView.addObject("id",requesterID);
		modelAndView.setViewName("/RequestDetailAreaTag");
		return modelAndView;
	}
	
	/**
	 * 
	 * 
	 * 工人信息
	 */
	@RequestMapping(value="/WorkerDetail",method=RequestMethod.GET)
	public ModelAndView workerDetail_get(
			@RequestParam(value="workerid",required=false) String workerID) {
		//workerID="146";
		ModelAndView modelAndView = new ModelAndView();
		Long workerId=Long.parseLong(workerID);
		worker_id=workerId;
		Worker worker=workerService.getWorker(workerId);
		WorkerEntity workerEntity=worker.getWorkerEntity();
		WorkerPortraitEntity workerPortraitEntity=worker.getWorkerPortraitEntity();
		
		DecimalFormat df=new DecimalFormat("#.00");
		
		modelAndView.addObject("workerid",workerId);
		modelAndView.addObject("workername",workerEntity.getWorkername());
		modelAndView.addObject("rank",workerEntity.getRank());
		modelAndView.addObject("speed",df.format(workerPortraitEntity.getEfficiency()));
		modelAndView.addObject("accuracy",workerPortraitEntity.getAccuracy()*100);
		modelAndView.addObject("credit",workerPortraitEntity.getCredit());
		modelAndView.addObject("id",requesterID);
		modelAndView.setViewName("/WorkerDetail");
		return modelAndView;
	}
	
	@RequestMapping(value="/WorkerDetail",method=RequestMethod.POST)
	public ModelAndView workerDetail_post(
			@RequestParam(value="rating",required=false) String rating) {
		if(rating!=null) {
		Worker worker=workerService.getWorker(worker_id);
		WorkerEntity workerEntity=worker.getWorkerEntity();
		WorkerPortraitEntity workerPortraitEntity=worker.getWorkerPortraitEntity();
		double comment=Double.parseDouble(rating);
		workerPortraitEntity=Calculate.cred(workerPortraitEntity, comment/5*100);
		System.out.println(comment/5*100);
		
		DecimalFormat df=new DecimalFormat("#.00");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("id",requesterID);
		modelAndView.addObject("workerid",worker_id);
		modelAndView.addObject("workername",workerEntity.getWorkername());
		modelAndView.addObject("rank",workerEntity.getRank());
		modelAndView.addObject("speed",df.format(workerPortraitEntity.getEfficiency()));
		modelAndView.addObject("accuracy",workerPortraitEntity.getAccuracy()*100);
		modelAndView.addObject("credit",workerPortraitEntity.getCredit());
		modelAndView.setViewName("/WorkerDetail");
		return modelAndView;
		}
		else {
			Worker worker=workerService.getWorker(worker_id);
			WorkerEntity workerEntity=worker.getWorkerEntity();
			WorkerPortraitEntity workerPortraitEntity=worker.getWorkerPortraitEntity();
			
			DecimalFormat df=new DecimalFormat("#.00");
			
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("id",requesterID);
			modelAndView.addObject("workerid",worker_id);
			modelAndView.addObject("workername",workerEntity.getWorkername());
			modelAndView.addObject("rank",workerEntity.getRank());
			modelAndView.addObject("speed",df.format(workerPortraitEntity.getEfficiency()));
			modelAndView.addObject("accuracy",workerPortraitEntity.getAccuracy()*100);
			modelAndView.addObject("credit",workerPortraitEntity.getCredit());
			modelAndView.setViewName("/WorkerDetail");
			return modelAndView;
		}
	}
	
    /**
     * 
     * 
     * 文件类型转化的中间方法
     */
	public void inputStreamToFile(InputStream ins,File file) {  
        try {  
         OutputStream os = new FileOutputStream(file);  
         int bytesRead = 0;  
         byte[] buffer = new byte[8192];  
         while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {  
          os.write(buffer, 0, bytesRead);  
         }  
         os.close();  
         ins.close();  
        } catch (Exception e) {  
         e.printStackTrace();  
        }  
       }  
	
	
}