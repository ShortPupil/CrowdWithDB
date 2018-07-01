package crowdtag.controller;
import java.util.ArrayList;
import java.util.Map;

/*
 * 杩欓噷鏄鐞嗗憳绯荤粺鐨勮鍥炬帶鍒跺櫒
 * 濡傛灉浣犳兂杩涘叆绠＄悊鍛樼郴缁熻杈撳叆adminhome杩涘叆锛屽綋鐒讹紝濡傛灉浣犳病鏈夌櫥褰曞氨浼氳烦鍒扮櫥闄嗙晫闈dminlogin浜�
 * 鍙互浣跨敤鐨勮处鍙稩D涓�1锛屽瘑鐮佸搴斾负1234
 * 褰撶劧涓嶈兘闅忔剰娉ㄥ唽浜嗭紝涓嶈繃鍙互娣诲姞鏉冮檺姣斾綘浣庣殑绠＄悊鍛樸��
 * 绠＄悊鍛樼郴缁熷寘鎷伐浜轰俊鎭鐞嗭紝鍙戣捣鑰呬俊鎭鐞嗭紝鏍囨敞鏁版嵁闆嗙鐞嗭紝绠＄悊鍛樼鐞嗭紝
 * 浣犵敋鑷冲彲浠ユ寜鐓у湪宸ヤ汉淇℃伅绠＄悊涓緷鎹储寮曞湪鏍囨敞鏁版嵁闆嗙鐞嗕腑鏌ュ埌浠栫殑淇℃伅锛岄櫎姝や箣澶栬繕鏈夊埆鐨勫姛鑳�
 * 璇曚竴璇曞惂
 */
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import crowdtag.hibernate.entity.AdministratorEntity;
import crowdtag.model.businesslogic.service.AdministratorService;
import crowdtag.model.businesslogic.service.MapService;
import crowdtag.model.businesslogic.service.TaskService;

@Controller
public class AdminController {

	@Autowired
	AdministratorService adminservice;
	
	@Autowired
	TaskService taskservice;
	
	@Autowired
	MapService mapservice;

	@RequestMapping(value = "adminhome")
	String adminhome(HttpSession session,Model model) {
		//for test
		//AdministratorEntity admin1=new AdministratorEntity();
		//admin1.setAuthority(1);
		//session.setAttribute("admin", admin1);
		
		AdministratorEntity admin=(AdministratorEntity) session.getAttribute("admin");
		if(admin==null)return "redirect:adminlogin";
		Map<String, Integer> map_worker=mapservice.countWorkerByTime();
		Map<String, Integer> map_requester=mapservice.countRequesterByTime();
		Map<String, Integer> map_alltask=mapservice.countRecordsByTime();
		Map<String, Integer> map_needtask=mapservice.getAllNeedRecordByTime();
		ArrayList<Map<String, Integer>> list_map=mapservice.getAllWorkerPortrait();
		Map<String,Integer> map_accuracy=list_map.get(0);
		Map<String,Integer> map_efficiency=list_map.get(1);
		Map<String,Integer> map_credit=list_map.get(2);
		
		model.addAttribute("worker_time",map_worker.keySet());
		model.addAttribute("worker_count",map_worker.values());
		model.addAttribute("requester_time", map_requester.keySet());
		model.addAttribute("requester_count", map_requester.values());
		model.addAttribute("alltask_time",map_alltask.keySet());
		model.addAttribute("alltask_count",map_alltask.values());
		model.addAttribute("needtask_time",map_needtask.keySet());
		model.addAttribute("needtask_count",map_needtask.values());
		model.addAttribute("accuracy", map_accuracy);
		model.addAttribute("efficiency", map_efficiency);
		model.addAttribute("credit", map_credit);
		model.addAttribute("accuracy_condition",map_accuracy.keySet());
		model.addAttribute("accuracy_count",map_accuracy.values());
		model.addAttribute("efficiency_condition",map_efficiency.keySet());
		model.addAttribute("efficiency_count",map_efficiency.values());
		model.addAttribute("credit_condition",map_credit.keySet());
		model.addAttribute("credit_count",map_credit.values());
		
		return "adminhome";
	}

	
	@RequestMapping(value = "adminhome-requester")
	String adminhome_requester(HttpSession session,@RequestParam(value="order",required=false)String order,
			@RequestParam(value="id",required=false) Long id,Model model) {
		AdministratorEntity admin=(AdministratorEntity) session.getAttribute("admin");
		if(admin==null)return "redirect:adminlogin";
		if(order!=null&&order.equals("delete")) {
			adminservice.deleteByRequesterId(id);
		}
		model.addAttribute("requesterlist",adminservice.findAllRequester());
		return "adminhome-requester";
	}

	@RequestMapping(value = "adminhome-tag_collection")
	String adminhome_tag_collection(HttpSession session,
			@RequestParam(value="order",required=false)String order,
			@RequestParam(value="id",required=false) Long id,Model model) {
		AdministratorEntity admin=(AdministratorEntity) session.getAttribute("admin");
		if(admin==null)return "redirect:adminlogin";
	//	System.out.println("       "+order!=null?order:""+"         ");
		if(order!=null&&order.equals("delete")) {
			//System.out.println("here!!!!");
			adminservice.deleteByRequestId(id);
		}
		if(order!=null&&order.equals("search_requester")&&id!=null) {
			model.addAttribute("requestlist", adminservice.findCollectionByRequesterId(id));
		}else {
			model.addAttribute("requestlist",adminservice.findAllRequest());
		}
		return "adminhome-tag_collection";
	}

	@RequestMapping(value = "adminhome-tag_task")
	String adminhome_tag_task(HttpSession session,
			@RequestParam(value="order",required=false)String order,
			@RequestParam(value="id",required=false) Long id,Model model) {
		AdministratorEntity admin=(AdministratorEntity) session.getAttribute("admin");
		if(admin==null||order==null||(!order.equals("search_worker")&&!order.equals("search_request"))||id==null)
			return "redirect:adminlogin";
		if(order!=null&&order.equals("search_worker")) {
			model.addAttribute("tasklist",adminservice.findTaskByWokerId(id));
		}
		if(order!=null&&order.equals("search_request")) {
			//System.out.println("\nwoshi "+id);
			//System.out.println(taskservice.findTasksByCollectionId(id).get(0).getType().getRequestType());
			model.addAttribute("tasklist",taskservice.findTasksByCollectionId(id));
		}
		return "adminhome-tag_task";
	}

	@RequestMapping(value = "adminhome-worker")
	String adminhome_worker(HttpSession session,
			@RequestParam(value="order",required=false)String order,
			@RequestParam(value="id",required=false) Long id,Model model) {
		AdministratorEntity admin=(AdministratorEntity) session.getAttribute("admin");
		if(admin==null)return "redirect:adminlogin";
		if(order!=null&&order.equals("delete")) {
			adminservice.deleteByWorkerId(id);
		}
		model.addAttribute("workerlist",adminservice.findAllWorker());
		return "adminhome-worker";
	}

	@RequestMapping(value = "adminhome-admin")
	String adminhome_admin(HttpSession session,
			@RequestParam(value="order",required=false)String order,
			@RequestParam(value="id",required=false) Long id,
			@RequestParam(value="name",required=false)String name,
			@RequestParam(value="password",required=false)String password,
			@RequestParam(value="authority",required=false)Integer authority,
			Model model) {
		AdministratorEntity admin=(AdministratorEntity) session.getAttribute("admin");
		if(admin==null)return "redirect:adminlogin";
		if(order!=null&&order.equals("delete")&&id!=null) {
			adminservice.deleteAdministrator(id, admin.getAuthority());
		}
		if(order!=null&&order.equals("add")&&name!=null&&password!=null&&authority!=null) {
			System.out.println("     add!!!!!!   \n\n");
			adminservice.addAdministrator(name, password, authority, admin.getAuthority());
		}	
		model.addAttribute("adminlist",adminservice.getAllAdministrators());
		return "adminhome-admin";
	}

	@RequestMapping(value = "adminlogin", method = RequestMethod.GET)
	String adminlogin_get() {
		return "adminlogin";
	}

	@RequestMapping(value = "adminlogin", method = RequestMethod.POST)
	String adminlogin_post(@RequestParam(value = "id") String id,
			@RequestParam(value = "password") String password,HttpSession session) {
		AdministratorEntity admin=null;
		try{
			admin=adminservice.login(Long.valueOf(id), password);
		}catch(Exception e){
			return "adminlogin";
		}
		if(admin==null)return "adminlogin";
		session.setAttribute("admin", admin);
		return "redirect:adminhome";
	}

	@RequestMapping(value="/adminlogout")
	public String adminlogout(HttpSession session) {
		session.setAttribute("admin", null);
		return "redirect:adminlogin";
	}
}