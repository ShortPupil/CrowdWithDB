package crowdtag.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import crowdtag.model.Result;
import crowdtag.model.request.RequestModel;
import crowdtag.model.request.TempModel;
import crowdtag.model.requestJson.JsonJavaVO;
import crowdtag.model.user.Requester;
import crowdtag.model.user.TempWorker;
import crowdtag.model.request.RequestModel;
import crowdtag.model.request.TempModel;
import crowdtag.model.requestJson.JsonJavaVO;

import crowdtag.service.JsonTransformation;
import crowdtag.service.RequestService;
import crowdtag.service.RequesterService;

@Controller
public class RequesterController {
	
	RequestService requestService=new RequestService();
	RequesterService requesterService=new RequesterService();
	JsonTransformation jsonTransformation=new JsonTransformation();
	
	public String requester_id;
	
	/**
	 * Requester注册
	 * 
	 * @return
	 */
	@RequestMapping(value="/RequesterRegister",method=RequestMethod.GET)
	public String toRequesterRegister() {
		return "RequesterRegister";
	}
	
	@RequestMapping(value="/RequesterRegister",method=RequestMethod.POST)
	public String requesterRegister(@RequestParam(value="name",required=false) String name,
			@RequestParam(value="password",required=false) String password) {
		Requester requester=new Requester();
		requester.setName(name);
		requester.setPassword(password);
		Result result=requesterService.register(requester);
		if(result.isright)
			return "RequesterLogin";
		else 
			return "RequesterRegister";
	}
	
	/**
	 * Requester登录
	 * 
	 * @return
	 */
	@RequestMapping(value="/RequesterLogin",method=RequestMethod.GET)
	public String toRequesterLogin() {
		return "RequesterLogin";
	}
	
	@RequestMapping(value="/RequesterLogin",method=RequestMethod.POST)
	public ModelAndView RequesterLogin(@RequestParam(value="id",required=false) String id,
			@RequestParam(value="password",required=false) String password) {
		ModelAndView model=new ModelAndView();
		Result rs=requesterService.login(id, password);
		if(rs.isright) {
			model.addObject("id",id);
			model.setViewName("/RequesterHome");
			requester_id=id;
			return model;
		}
		else {
			model.setViewName("/RequesterLogin");
			return model;
		}
	}
	
	/**
	 * 创建一个项目
	 * 
	 * @return
	 */
	@RequestMapping(value="/RequestCreate",method=RequestMethod.GET)
	public String toCreateRequest() {
		return "RequestCreate";
	}
	
	@RequestMapping(value="/RequestCreate",method=RequestMethod.POST)
	public ModelAndView createRequest(@RequestParam("name") String name,
			@RequestParam("type") String type,
			@RequestParam("description") String description,
			@RequestParam("path") String path,
			@RequestParam("point") int point,
			@RequestParam("standard") int standard,
			@RequestParam("requesterID") String requesterID,
			@RequestParam("question") String question) {	
		
		ArrayList<String> imagePath=new ArrayList<String>();
		String[] pathArray=path.split("<>");
		for(int i=0;i<pathArray.length;i++) {
			imagePath.add(pathArray[i]);
		}
		
		ArrayList<String> info=new ArrayList<String>();
		if(type.equals("ClassTag")) {
			String[] infoArray=question.split(",");
			for(int i=0;i<infoArray.length;i++) {
				info.add(infoArray[i]);
			}
		}
		else
			info=null;
			
		RequestModel requestModel=new RequestModel(type,name,requesterID,
				description,imagePath,standard,point,info);
		requestService.create(requestModel);

		
		ModelAndView model=new ModelAndView();
		model.setViewName("/RequestCreate");
		model.addObject("requesterID",requesterID);
		return model;
	}
	
	/**
	 * 查看任务状况
	 * 
	 * @return
	 */
	
	@RequestMapping(value="/RequesterHome",method=RequestMethod.GET)
	public String toRequesterHome() {
		return "RequesterHome";
	}
	
	@RequestMapping(value="/RequesterHome",method=RequestMethod.POST)
	public ModelAndView requestManage() {
		ArrayList<String> requestList=new ArrayList<String>();
		requestList=requesterService.getAllRequestByRequesterId(requester_id);

		ArrayList<TempModel> list=new ArrayList<TempModel>();
		
		for(int i=0;i<(requestList.size());i++) {
			JsonJavaVO json=jsonTransformation.transferJsonToVOById(
					requestList.get(i));
			System.out.println(requestList.get(i));
			String type=json.getType();
			String state=json.getState();
			
			TempModel temp=new TempModel(type,requestList.get(i),state);
			list.add(temp);
		}
		
		
		/*ArrayList<TempModel> testList=new ArrayList<TempModel>();
		TempModel testModel=new TempModel("test1","test2","test3");
		
		testList.add(testModel);
		*/
		
		ModelAndView model=new ModelAndView();
		model.setViewName("/RequestManage");
		model.addObject("list",list);
		
		//model.addObject("testList",testList);
		model.addObject("id",requester_id);
		System.out.println(list.size());
		return model;
	}
	
	/**
	 * 查看某一个任务的详情
	 * 
	 * @return
	 */
	
	@RequestMapping(value="/RequestManage",method=RequestMethod.GET)
	public String toRequestManage() {
		return "RequestManage";
	}
	
	@RequestMapping(value="/RequestManage",method=RequestMethod.POST)
	public ModelAndView requestDetail(@RequestParam("name") String name,
			@RequestParam("requesterName") String requesterName) {
		ModelAndView model=new ModelAndView();
		model.setViewName("/RequestDetail");
		
		Map<String,Integer> map=requestService.getWorkeridWhoDidTheRequest(name);
		ArrayList<TempWorker> workerList=new ArrayList<TempWorker>();
		for(Map.Entry<String,Integer> entry:map.entrySet()) {
			TempWorker worker=new TempWorker(entry.getKey(),entry.getValue());
			workerList.add(worker);
		}
		
		double a=(double)requestService.getTagQuantity(name);
		double b=(double)requestService.getFinishedTagQuantity(name);
		System.out.println(a);
		System.out.println(b);
		
		double rate=b*100/a;
		System.out.println(rate);
		model.addObject("process",rate);
		model.addObject("requesterName",requesterName);
		model.addObject("requestId",name);
		model.addObject("workerList",workerList);
		return model;
	}
}

