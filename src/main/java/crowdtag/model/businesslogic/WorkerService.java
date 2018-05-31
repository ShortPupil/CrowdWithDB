package crowdtag.model.businesslogic;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crowdtag.hibernate.entity.WorkerEntity;
import crowdtag.hibernate.entity.WorkerPortraitEntity;
import crowdtag.hibernate.repository.WorkerPortraitRepository;
import crowdtag.hibernate.repository.WorkerRepository;
@Service
public class WorkerService {
	@Autowired
	WorkerRepository wr;
	@Autowired
	WorkerPortraitRepository wpp;
	Worker login (long id,String password){
		Worker r=new Worker();
		Optional<WorkerEntity> w1=wr.findById(id);
		Optional<WorkerPortraitEntity> w2=wpp.findById(id);
		
		if(w1.isPresent()) {
			r.setWorkerEntity(w1.get());
		}
		if(w2.isPresent()) {
			r.setWorkerPortraitEntity(w2.get());
		}
		if(w1.get().getPassword().equals(password)) {
		return r;
		}
		else {
			return null;
		}
	}
	Worker regsiter(Worker worker) {

		WorkerEntity w=wr.saveAndFlush(worker.getWorkerEntity());
		WorkerPortraitEntity wp =wpp.saveAndFlush(worker.getWorkerPortraitEntity());
		
		worker.getWorkerEntity().setId(w.getId());
		worker.getWorkerPortraitEntity().setId(wp.getId());
		return worker;
	}
	Worker getWorker(long id) {
		Worker r=new Worker();

		Optional<WorkerEntity> w1=wr.findById(id);
		Optional<WorkerPortraitEntity> w2=wpp.findById(id);
		
		if(w1.isPresent()) {
			r.setWorkerEntity(w1.get());
		}
		if(w2.isPresent()) {
			r.setWorkerPortraitEntity(w2.get());
		}
		return r;
	}
}
