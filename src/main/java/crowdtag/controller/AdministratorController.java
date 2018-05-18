package crowdtag.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import crowdtag.hibernate.entity.RequestJsonEntity;
import crowdtag.hibernate.entity.WorkerEntity;
import crowdtag.hibernate.service.HibernateService;
import crowdtag.hibernate.serviceimpl.RequestJsonServiceImpl;
import crowdtag.hibernate.serviceimpl.WorkerServiceImpl;
import crowdtag.service.RequestService;
import crowdtag.service.WorkerService;

@Controller
public class AdministratorController {
	
	HibernateService<WorkerEntity> workerService=new WorkerServiceImpl();
	HibernateService<RequestJsonEntity> requestService=new RequestJsonServiceImpl();
	
	@RequestMapping(value="AdminHome",method=RequestMethod.GET)
	public String home(Model model) {
		model.addAttribute("worker_quantity", workerService.getQuantity());
		model.addAttribute("request_quantity", requestService.getQuantity());
		model.addAttribute("request_unfinished", requestService.getUnfinished());
		model.addAttribute("request_finished", requestService.getFinished());
		return "AdminHome";
	}
}
