package crowdtag.model.businesslogic.service;

import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crowdtag.hibernate.entity.RequesterEntity;
import crowdtag.hibernate.entity.WorkerEntity;
import crowdtag.hibernate.entity.WorkerPortraitEntity;
import crowdtag.hibernate.entity.request.Records;
import crowdtag.hibernate.entity.request.RequestEntity;
import crowdtag.hibernate.repository.RequesterRepository;
import crowdtag.hibernate.repository.WorkerPortraitRepository;
import crowdtag.hibernate.repository.WorkerRepository;
import crowdtag.hibernate.repository.request.RecordsRepository;
import crowdtag.hibernate.repository.request.RequestRepository;

@Service
public class MapService {

	@Autowired
	WorkerRepository workerrepository;
	@Autowired
	WorkerPortraitRepository workerportraitrepository;
	@Autowired
	RequesterRepository requesterrepository;
	@Autowired
	RecordsRepository recordsrepository;
	@Autowired
	RequestRepository requestrepository;
	
	/**时间和注册的工人数*/
	public Map<String,Integer> countWorkerByTime(){
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMMdd");
		Map<String,Integer> res = new LinkedHashMap<String,Integer>();
		List<WorkerEntity> workers = workerrepository.findAll();
		for(WorkerEntity w : workers) {
			String date = w.getCreatedAt().format(format);
			if(!res.containsKey(date)) {
				res.put(date, 1);
			}else {	
				res.put(date, res.get(date)+1);
			}
		}
		return res;
	}
	
	/**时间和注册的发起者数*/
	public Map<String,Integer> countRequesterByTime(){
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMMdd");
		Map<String,Integer> res = new LinkedHashMap<String,Integer>();
		List<RequesterEntity> requesters = requesterrepository.findAll();
		for(RequesterEntity r : requesters) {
			String date = r.getCreatedAt().format(format);
			if(!res.containsKey(date)) {
				res.put(date, 1);
			}else {	
				res.put(date, res.get(date)+1);
			}
		}
		return res;
	}

	/**时间和已经完成的标注数量*/
	public Map<String,Integer> countRecordsByTime(){
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMMdd");
		Map<String,Integer> res = new LinkedHashMap<String,Integer>();
		List<Records> records = recordsrepository.findAll();
		int num_2 = 0;
		for(Records r : records) {
			String date = r.getCreatedAt().format(format);
			num_2++;
			res.put(date, num_2);
		}
		return res;
	}
	/**时间和所有任务需要的标注数量*/
	public Map<String, Integer> getAllNeedRecordByTime(){
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMMdd");
		Map<String,Integer> res = new LinkedHashMap<String,Integer>();
		List<Records> records = recordsrepository.findAll();
		List<RequestEntity> requests = requestrepository.findAll();
		for(Records r : records) {
			int sum = 0;
			ChronoZonedDateTime<?> t = r.getCreatedAt();
			for(RequestEntity request : requests) {
				if(request.getCreatedAt().isBefore(t)) {
					sum = sum+request.getImages().size()*request.getStandard();
				}
			}
			res.put(r.getCreatedAt().format(format), sum);
		}
		return res;
	}
	
	/**所有工人的属性分布*/
	public ArrayList<Map<String, Integer>> getAllWorkerPortrait(){
		ArrayList<Map<String, Integer>> res = new ArrayList<Map<String, Integer>>();
		Map<String,Integer> acc = new LinkedHashMap<String,Integer>();
		acc.put("优秀 0.8-1", 0);
		acc.put("良好 0.6-0.8", 0);
		acc.put("一般 0.4-0.6", 0);
		acc.put("较差 0.2-0.4", 0);
		acc.put("差 0-0.2", 0);
		Map<String,Integer> eff = new LinkedHashMap<String,Integer>();
		eff.put("优秀 40-50", 0);
		eff.put("良好 30-40", 0);
		eff.put("一般 20-30", 0);
		eff.put("较差 10-20", 0);
		eff.put("差 0-10", 0);
		Map<String,Integer> cre = new LinkedHashMap<String,Integer>();
		cre.put("优秀 80-100", 0);
		cre.put("良好 60-80", 0);
		cre.put("一般 40-60", 0);
		cre.put("较差 20-40", 0);
		cre.put("差 0-20", 0);
		List<WorkerPortraitEntity> workers = workerportraitrepository.findAll();
		for(WorkerPortraitEntity w : workers) {
			if(w.getAccuracy()>=0.8) acc.put("优秀 0.8-1", acc.get("优秀 0.8-1")+1);
			if(w.getAccuracy()>=0.6 && w.getAccuracy()<0.8) acc.put("良好 0.6-0.8", acc.get("良好 0.6-0.8")+1);
			if(w.getAccuracy()>=0.4 && w.getAccuracy()<0.6) acc.put("一般 0.4-0.6", acc.get("一般 0.4-0.6")+1);
			if(w.getAccuracy()>=0.2 && w.getAccuracy()<0.4) acc.put("较差 0.2-0.4", acc.get("较差 0.2-0.4")+1);
			if(w.getAccuracy()>=0 && w.getAccuracy()<0.2) acc.put("差 0-0.2", acc.get("差 0-0.2")+1);
			
			if(w.getEfficiency()>=40) eff.put("优秀 40-50", eff.get("优秀 40-50")+1);
			if(w.getEfficiency()>=30 && w.getEfficiency()<40) eff.put("良好 30-40", eff.get("良好 30-40")+1);
			if(w.getEfficiency()>=20 && w.getEfficiency()<30) eff.put("一般 20-30", eff.get("一般 20-30")+1);
			if(w.getEfficiency()>=10 && w.getEfficiency()<20) eff.put("较差 10-20", eff.get("较差 10-20")+1);
			if(w.getEfficiency()>=0 && w.getEfficiency()<10) eff.put("差 0-10", eff.get("差 0-10")+1);
			
			if(w.getCredit()>=80) cre.put("优秀 80-100", cre.get("优秀 80-100")+1);
			if(w.getCredit()>=60 && w.getCredit()<80) cre.put("良好 60-80", cre.get("良好 60-80")+1);
			if(w.getCredit()>=40 && w.getCredit()<60) cre.put("一般 40-60", cre.get("一般 40-60")+1);
			if(w.getCredit()>=20 && w.getCredit()<40) cre.put("较差 20-40", cre.get("较差 20-40")+1);
			if(w.getCredit()>=0 && w.getCredit()<20) cre.put("差 0-20", cre.get("差 0-20")+1);
		}
		res.add(acc);
		res.add(eff);
		res.add(cre);
		return res;
	}
}
