package crowdtag.controller;

/*
 * 这里是工人系统的视图控制器
 * 工人系统主要有三部分构成：搜索任务，进行标注，工人主页。
 * 你可以直接写入“”或者“home”来进入工人系统
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import crowdtag.hibernate.entity.WorkerEntity;
import crowdtag.hibernate.entity.WorkerPortraitEntity;
import crowdtag.hibernate.result.ResponseBodyInfo;
import crowdtag.model.businesslogic.Task;
import crowdtag.model.businesslogic.Worker;
import crowdtag.model.businesslogic.service.CollectionService;
import crowdtag.model.businesslogic.service.GiftService;
import crowdtag.model.businesslogic.service.TaskService;
import crowdtag.model.businesslogic.service.WorkerService;
import crowdtag.model.statistics.calculate;

@Controller
public class WorkerController {
    @Autowired
	WorkerService workerservice ;
    @Autowired
	CollectionService collectionservice;
    @Autowired
    TaskService taskservice;
    @Autowired
    GiftService giftservice;
    @Autowired
    calculate calculateservice;
    
	@RequestMapping(value = { "/", "/home" })
	public String home() {
		return "home";
	}

	@RequestMapping(value = "/workerregister", method = RequestMethod.GET)
	public String workerregister_get() {
		return "workerregister";
	}

	@RequestMapping(value = "/workerregister", method = RequestMethod.POST)
	public String workerregister_post(@RequestParam(value = "workername") String workername,
			@RequestParam(value = "password") String password, HttpSession session) {
		Worker worker = new Worker();
		worker.setWorkerEntity(new WorkerEntity(workername, password));
		worker.setWorkerPortraitEntity(new WorkerPortraitEntity());
		//System.out.print(worker.getWorkerEntity().getId());
		session.setAttribute("worker", worker);
		return "redirect:workerregister-preference";
	}

	@RequestMapping(value="/workerregister-preference")
	public String workerregister_detail(@RequestParam(value="tag",required=false) String tag,
			HttpSession session,Model model) {
		if(tag==null) {
			model.addAttribute("preferencelist", collectionservice.getAds(-1, 9));
			return "workerregister-preference";
		} 
		Worker worker=(Worker) session.getAttribute("worker");
		WorkerPortraitEntity wpe=new WorkerPortraitEntity();
		wpe.setPreference(tag);
		worker.setWorkerPortraitEntity(wpe);
		ResponseBodyInfo<Worker> res = workerservice.regsiter(worker);
		if (res.equals(null)) {
			session.setAttribute("worker", null);
			return "workerregister";
		}
		session.setAttribute("worker", worker);
		return "workerregister-success";
	}
	
	@RequestMapping(value = "/workerlogin", method = RequestMethod.GET)
	public String workerlogin_get() {
		return "workerlogin";
	}

	@RequestMapping(value = "/workerlogin", method = RequestMethod.POST)
	public String workerlogin_post(@RequestParam(value = "workerid") String workerid,
			@RequestParam(value = "password") String password, HttpSession session) {
		Worker worker = null;
		try {
			worker = workerservice.login(Long.valueOf(workerid).longValue(), password);
		}catch(Exception e) {
			return "workerlogin";
		}
		if (worker == null)
			return "workerlogin";
		session.setAttribute("worker", worker);
		return "redirect:home";
	}

	@RequestMapping(value = "/teaminfo")
	public String teaminfo() {
		return "teaminfo";
	}

	@RequestMapping(value = "/taglesson")
	public String taglesson() {
		return "taglesson";
	}

	@RequestMapping(value="/search")
	public String search(@RequestParam(value="search_key",required=false) String search_key,
			@RequestParam(value="search_types",required=false)ArrayList<String>  search_types,
			@RequestParam(value="search_tags",required=false) ArrayList<String> search_tags,
			@RequestParam(value="order", required=false) String order, Model model,
			HttpSession session){
		System.out.println(search_key);
		System.out.println(search_types!=null?search_types.size():-1);
		System.out.println(search_tags!=null?search_tags.size():-1);
		model.addAttribute("collectionlist",collectionservice.search(search_key,
				search_types, search_tags,order));
		Worker worker=(Worker) session.getAttribute("worker");
		if(worker!=null) {
			model.addAttribute("adlist", collectionservice.getAds(worker.getWorkerEntity().getId(), 10));
		}
		
		return "search";
	}

	@RequestMapping(value="/tag",method=RequestMethod.GET)
	public String tag_get(HttpSession session, @RequestParam(value="collectionid",required=false) Long collectionid,
			Model model, HttpServletResponse response){
		if(collectionid==null)return "redirect:workerlogin";
		//System.out.print("1");
		Worker worker=(Worker) session.getAttribute("worker");
		//System.out.print("2");
		if(worker==null)return "redirect:workerlogin";
		Task nexttask=taskservice.getOptionalTask(worker.getWorkerEntity().getId(),collectionid);
		if(nexttask==null) {
			response.setCharacterEncoding("UTF-8");  
			response.setHeader("Content-type", "text/html;charset=UTF-8");  
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.print("<script>alert('没有可以标注的图片，可能是已经完成或权限不够');"
					+ "window.location.href='search';</script>");
			out.flush();
			return "";
		}
		
		model.addAttribute("task",nexttask); //哪来的worker\collectionid
		return "tag";
	}

	@RequestMapping(value="/tag",method=RequestMethod.POST)
	public String tag_post(HttpSession session, @RequestBody Task task, Model model,
			HttpServletResponse response){
	//	System.out.println("hi,imhere");
		Worker worker=(Worker) session.getAttribute("worker");
		if(worker==null)return "redirect:workerlogin";
		Long imagesid = task.getImageID();
		Long collectionid = taskservice.findCollectionIdByImagesId(imagesid);
		System.out.println("11");
		taskservice.finishtask(task);
		session.setAttribute("worker", workerservice.getWorker(worker.getWorkerEntity().getId()));
		Task nexttask=taskservice.getOptionalTask(worker.getWorkerEntity().getId(),collectionid);
		if(nexttask==null) {
			response.setCharacterEncoding("UTF-8");  
			response.setHeader("Content-type", "text/html;charset=UTF-8");  
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.print("<script>alert('没有可以标注的图片，可能是已经完成或权限不够');"
					+ "window.location.href='search';</script>");
			out.flush();
			return "";
		}
		
		model.addAttribute("task",nexttask); //哪来的worker\collectionid
		return "redirect:tag";
	}

	@RequestMapping(value = "/workerhome")
	public String workerhome(HttpSession session, Model model) {
		Worker worker = (Worker) session.getAttribute("worker");
		if (worker == null)
			return "redirect:workerlogin";
		long tasknumber = taskservice.countFinishedTask(worker.getWorkerEntity().getId());
		model.addAttribute("tasknumber", tasknumber);
		DecimalFormat df=new DecimalFormat("#.00");
		model.addAttribute("credit", df.format(worker.getWorkerPortraitEntity().getCredit()));

		return "workerhome";
	}

	@RequestMapping(value="/workerhome-gift")
	public String workerhome_gift(HttpSession session,Model model){
		Worker worker=(Worker) session.getAttribute("worker");
		if(worker==null)return "redirect:workerlogin";
		long tasknumber = taskservice.countFinishedTask(worker.getWorkerEntity().getId());
		model.addAttribute("tasknumber", tasknumber);
		model.addAttribute("credit", worker.getWorkerPortraitEntity().getCredit());
		model.addAttribute("giftlist",giftservice.findAll());
		System.out.println("\nhere"+giftservice.findAll().size());
		return "workerhome-gift";
	}

	@RequestMapping(value="/workerhome-history")
	public String workerhome_history(HttpSession session,Model model){
		Worker worker=(Worker) session.getAttribute("worker");
		if(worker==null)return "redirect:workerlogin";
		long tasknumber = taskservice.countFinishedTask(worker.getWorkerEntity().getId());
		model.addAttribute("tasknumber", tasknumber);
		model.addAttribute("credit", worker.getWorkerPortraitEntity().getCredit());
		
		model.addAttribute("collectionlist",
				collectionservice.getFinishedCollectionByWorkerId(worker.getWorkerEntity().getId()));
		System.out.println("\nhere"+collectionservice.
				getFinishedCollectionByWorkerId(worker.getWorkerEntity().getId()).size());
		return "workerhome-history";
	}

	@RequestMapping(value="/workerhome-rank")
	public String workerhome_rank(HttpSession session,Model model){
		Worker worker=(Worker) session.getAttribute("worker");
		if(worker==null)return "redirect:workerlogin";
		
		long tasknumber = taskservice.countFinishedTask(worker.getWorkerEntity().getId());
		double credit = worker.getWorkerPortraitEntity().getCredit();
		model.addAttribute("tasknumber", tasknumber);
		model.addAttribute("credit", credit);

		model.addAttribute("ranklist",calculateservice.getRankList());
		return "workerhome-rank";
	}
	
	@RequestMapping(value="/workerlogout")
	public String workerlogout(HttpSession session) {
		session.setAttribute("worker", null);
		return "redirect:home";
	}
	
	
}