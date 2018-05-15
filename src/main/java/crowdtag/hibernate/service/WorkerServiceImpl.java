package crowdtag.hibernate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crowdtag.hibernate.entity.WorkerEntity;
import crowdtag.hibernate.repository.WorkerRepository;

@Service
public class WorkerServiceImpl implements WorkerService{

	@Autowired
	private WorkerRepository workerRepository;
	@Override
	public List<WorkerEntity> findAll() {
		// TODO Auto-generated method stub
		return workerRepository.findAll();
	}

	@Override
	public void save(WorkerEntity entity) {
		// TODO Auto-generated method stub
		workerRepository.save(entity);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		workerRepository.deleteById(id);
	}

	@Override
	public void delete(WorkerEntity entity) {
		// TODO Auto-generated method stub
		workerRepository.delete(entity);
	}

}
