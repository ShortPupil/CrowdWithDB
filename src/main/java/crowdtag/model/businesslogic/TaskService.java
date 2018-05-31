package crowdtag.model.businesslogic;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crowdtag.hibernate.entity.WorkerEntity;
import crowdtag.hibernate.entity.request.Images;
import crowdtag.hibernate.entity.request.Records;
import crowdtag.hibernate.entity.request.RequestEntity;
import crowdtag.hibernate.entity.request.State;
import crowdtag.hibernate.repository.WorkerRepository;
import crowdtag.hibernate.repository.request.RecordsRepository;
import crowdtag.hibernate.repository.request.RequestRepository;
import crowdtag.hibernate.result.ResponseBodyInfo;
@Service
public class TaskService {
	//全局变量
	@Autowired
	RequestRepository requestrepository;

	@Autowired
	RecordsRepository recordsrepository;
	@Autowired
	WorkerRepository workerRepository ;

	/**该worker可以做的*/
	Task getOptionalTask(Long workerId,Long collectionId) {
		if(collectionId.equals(null)) return null;
		
		Optional<RequestEntity> r=requestrepository.findById(collectionId);
		Task t;
		if(r.isPresent()) {
			Images images = requestrepository.findOptionalImagesByWorkerId(r.get(), workerId);
			RequestEntity request = requestrepository.findById(collectionId).get();
			t = new Task(workerId, collectionId, images.getPath(), request.getType(),
					images.getQuestion(), images.getOneContent(), request.getPoint());
			return t;
		}
		
		return null;
	}

	ResponseBodyInfo<Task> finishtask(Task t, Records record){ 
		//worker里面增加point属性和修改point的方法
		//request要修改，状态要修改
		//钟镇鸿写
		ResponseBodyInfo<Task> res = new ResponseBodyInfo<Task>(1,null,t);
		Optional<WorkerEntity> w=workerRepository.findById(t.getworkerId());
		if(!w.isPresent()) {
			res.setErrorCode(0);
			res.setErrorText("worker not exists ");
		}
		else {
			WorkerEntity we=w.get();
			we.setPoint(we.getPoint()+t.getPoint());
			workerRepository.saveAndFlush(we);
			record = recordsrepository.saveAndFlush(record);
			if(record.getImages().getRecords().size() == requestrepository.findStrandardByRecords(record.getId())) {
				record.getImages().setState(State.COMPLETED);
		}
		}
		return res;
	}
}
