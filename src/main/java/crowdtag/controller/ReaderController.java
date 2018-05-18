package crowdtag.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import com.google.gson.Gson;

import crowdtag.model.Result;
import crowdtag.model.requestJson.Images;
import crowdtag.model.requestJson.JsonJavaVO;
import crowdtag.model.temporary.ImagesAndCollectionId;
import crowdtag.model.user.Worker;
import crowdtag.service.JsonTransformation;
import crowdtag.service.ReaderService;
import crowdtag.service.RequestService;
import crowdtag.service.WorkerService;

@Controller
@SessionAttributes(value = "worker")
public class ReaderController {

	WorkerService workerService = new WorkerService();
	ReaderService readerService = new ReaderService();
	RequestService requestService = new RequestService();

	@RequestMapping(value = "/WorkerLogin", method = RequestMethod.GET)
	public String toWorkerLogin(Model model) {
		model.addAttribute("worker", new Worker("", "", 0));
		return "WorkerLogin";
	}

	@RequestMapping(value = "/WorkerLogin", method = RequestMethod.POST)
	public String workerLogin(@ModelAttribute(value = "worker") Worker worker, Model model) {
		Result r = workerService.login(worker.getId(), worker.getPassword());
		worker=workerService.getWorkerByID(worker.getId());
		model.addAttribute("worker", worker);
		if (!r.isright) {
			return "redirect:/WorkerLogin";
		}
		return "redirect:/Reader";
	}

	/**
	 * Exit 登出 未定
	 * 
	 * @return
	 */
	@RequestMapping(value = "logout")
	public String logout(SessionStatus status) {
		status.setComplete();
		return "redirect:/Reader";
	}

	@RequestMapping(value = "WorkerRegister", method = RequestMethod.GET)
	public String toWorkerRegister(Model model) {
		model.addAttribute(new Worker("", "", 0));
		return "WorkerRegister";
	}

	@RequestMapping(value = "WorkerRegister", method = RequestMethod.POST)
	public String workerRegister(@ModelAttribute(value = "worker") Worker worker, Model model) {
		Result r = workerService.register(worker);
		if (!r.isright) {
			return "WorkerRegister";
		}
		worker.setId(r.error);
		model.addAttribute("worker", worker);
		return "redirect:/Reader";
	}

	@RequestMapping(value = { "Reader", "/" })
	public String reader(HttpSession session, @RequestParam(value = "images_post", required = false) String images_post,
			@RequestParam(value = "jsonjavaid_post", required = false) String jsonjavaid_post, Model model) {
		// 关于用户身份
		if (session.getAttribute("worker") == null) {
			session.setAttribute("worker", new Worker());
		}
		Worker worker = (Worker) session.getAttribute("worker");

		// 关于小说阅读
		worker.setPosition(worker.getPosition() + 1);
		model.addAttribute("worker", worker);
		model.addAttribute("content", readerService.getChapter(worker.getPosition()));

		// 关于标注任务提交的处理
		if (images_post != null && jsonjavaid_post != null) {
			System.out.println(images_post);
			requestService.finishOne(jsonjavaid_post, images_post, worker.getId());
			workerService.addPoint(worker.getId(), 1);
			worker.setPoint(worker.getPoint() + 1);
			model.addAttribute("worker", worker);
		}

		// 关于标注任务的发布
		if (worker.getId() != "youke") {
			//System.out.println(worker.getId());
			ImagesAndCollectionId<Images, String> ImagesAndId = requestService.getOne(worker.getId());

			if (ImagesAndId != null) {
				Images images = ImagesAndId.getImages();
				String collectionId = ImagesAndId.getId();
				JsonJavaVO j = transformation.transferJsonToVOById(collectionId);
				model.addAttribute("type", j.getType());
				Gson gson = new Gson();
				String imagesContent = gson.toJson(images);
				//System.out.println(imagesContent);
				model.addAttribute("images", imagesContent);
				model.addAttribute("jsonjavaid", ImagesAndId.getId());
			}

		}

		return "Reader";

	}

	@RequestMapping("/ContactUs")
	String ContactUs() {
		return "ContactUs";
	}

	@RequestMapping("/WorkerInfo")
	String WorkerInfo(@ModelAttribute("worker") Worker worker) {
		worker.setRank(workerService.getRank(worker.getId()));
		worker.setRequestList(workerService.getRequest(worker.getId()));
		return "WorkerInfo";

	}

}
