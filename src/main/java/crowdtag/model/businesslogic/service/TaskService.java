package crowdtag.model.businesslogic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crowdtag.hibernate.entity.WorkerEntity;
import crowdtag.hibernate.entity.WorkerPortraitEntity;
import crowdtag.hibernate.entity.request.Images;
import crowdtag.hibernate.entity.request.Records;
import crowdtag.hibernate.entity.request.RequestEntity;
import crowdtag.hibernate.entity.request.State;
import crowdtag.hibernate.repository.WorkerPortraitRepository;
import crowdtag.hibernate.repository.WorkerRepository;
import crowdtag.hibernate.repository.request.ImagesRepository;
import crowdtag.hibernate.repository.request.RecordsRepository;
import crowdtag.hibernate.repository.request.RequestRepository;
import crowdtag.hibernate.result.ResponseBodyInfo;
import crowdtag.model.businesslogic.Task;
import crowdtag.model.statistics.calculate;
@Service
public class TaskService {
	//全局变量
	@Autowired
	RequestRepository requestrepository;
	@Autowired
	ImagesRepository imagesrepository;
	@Autowired
	RecordsRepository recordsrepository;
	@Autowired
	WorkerRepository workerRepository;
	@Autowired
	WorkerPortraitRepository workerPortraitRepository;
	@Autowired
	calculate c;
	
	/**该worker可以做的*/
	public Task getOptionalTask(Long workerId,Long collectionId) {
		if(collectionId.equals(null) || workerId.equals(null)) return null;

		Optional<RequestEntity> r=requestrepository.findById(collectionId);
		Optional<WorkerPortraitEntity> wp = workerPortraitRepository.findById(workerId);
		Task t;
		if(r.isPresent() && wp.isPresent()) {
			ArrayList<Images> images = requestrepository.findOptionalImagesByWorkerId(r.get(), workerId.longValue());
			if(images.size()!=0) {
				//假如是没达到限制
				if(r.get().getAccuracy_limit() > wp.get().getAccuracy()*100
						|| r.get().getEfficiency_limit() > wp.get().getEfficiency()) {
					return null;
				}
				Images i = images.get(0);
				Optional<RequestEntity> request = requestrepository.findById(collectionId);
				t = new Task(workerId, i.getId(), i.getPath(), request.get().getType(),
					i.getQuestion(), i.getOneContent(), request.get().getPoint());
				return t;
			}
		}
		
		return null;
	}

	public ResponseBodyInfo<Task> finishtask(Task t){ 
		//worker里面增加point属性和修改point的方法
		//request要修改，状态要修改
		//钟镇鸿写
		Optional<Images> image = imagesrepository.findById(t.getImageID());
		ResponseBodyInfo<Task> res = new ResponseBodyInfo<Task>(1,null,t);
		Optional<WorkerEntity> we = workerRepository.findById(t.getWorkerID());
		if(!we.isPresent()) {
			res.setErrorCode(0);
			res.setErrorText("worker not exists");
		}
		else {
			WorkerEntity worker = we.get();
			Records record = new Records(image.get(), t.getWorkerID(), t.getAnswer(), t.getTags(), t.getTime());
			worker.setPoint(worker.getPoint()+t.getPoint());
			worker = workerRepository.saveAndFlush(worker);		
			record = recordsrepository.saveAndFlush(record);
			
			//image完成了
			if(record.getImages().getRecords().size() >= requestrepository.findStrandardByRecords(record.getId())) {
				Images i = record.getImages();
				i.setState(State.COMPLETED);
				imagesrepository.saveAndFlush(i);
			}
			RequestEntity request = image.get().getRequest();
			//reqeust完成了
			int counter = 0;
			for(Images images : request.getImages()) {
				if(images.getState().equals(State.COMPLETED)) counter++;
				if(counter == request.getImages().size()) {
					request.setState(State.COMPLETED);
					requestrepository.saveAndFlush(request);
				}
			}
		}
		System.out.println(t.getWorkerID());
		Optional<WorkerPortraitEntity> w=workerPortraitRepository.findById(t.getWorkerID());

		if(t.getWorkerID()!=(long)1) {
		c.accur(w.get());
		
		c.cred(w.get(), 0);
		
		c.effi(w.get());
	
		c.pref(w.get());
		
		c.getPointRank(w.get().getId());
		}
		return res;
	}
	
	/**这个人在collection中已经完成的
	 * @param workerid
	 * @param collectionId
	 * */
	public int countFinishedTask(Long workerId) {
		if( workerId.equals(null)) return 0;	
		List<Images> res = requestrepository.findImageByWorkerId(workerId);
		return res.size();
	}
	
	public Long findCollectionIdByImagesId(Long imagesId) {
		if(imagesId.equals(null)) return null;
		Optional<Images> i = imagesrepository.findById(imagesId);
		return i.get().getRequest().getId();
	}
	
	/**通过collectionid获取他下面的task*/
	public ArrayList<Task> findTasksByCollectionId(Long collectionId){
		if(collectionId.equals(null)) return null;
		Optional<RequestEntity> oprequest = requestrepository.findById(collectionId);
		if(!oprequest.isPresent()) return null;
		RequestEntity request = oprequest.get();
		List<Images> images = oprequest.get().getImages();
		String question = images.get(0).getQuestion();
		Map<Integer,String> answerMap = images.get(0).getOneContent();
		int point = request.getPoint();
		
		ArrayList<Records> records = new ArrayList<Records>();
		for(int i=0 ; i<images.size() ; i++) {
			records.addAll(images.get(i).getRecords());
			System.out.println(records.size());
		}
		ArrayList<Task> tasks = new ArrayList<Task>();
		for(int j=0 ; j<records.size() ; j++) {
			Records r = records.get(j);

			Task tem = new Task(r.getUser(), r.getImages().getId(),
					r.getImages().getPath(), request.getType() , 
					question, answerMap, point);
			tem.setAnswer(r.getAnswers());
			tem.setTags(r.getTags());
			tem.setTime(r.getTime());
			tasks.add(tem);
		}
		return tasks;
	}
	
}
