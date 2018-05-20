package crowdtag.hibernate.serviceimpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crowdtag.hibernate.entity.WorkerEntity;
import crowdtag.hibernate.repository.WorkerRepository;
import crowdtag.hibernate.service.WorkerService;

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
	@Transactional
	public int save(WorkerEntity entity) {
		// TODO Auto-generated method stub
		entity = workerRepository.saveAndFlush(entity);
		return entity.getId();
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		workerRepository.deleteById(id);
	}

	@Override
	public Optional<WorkerEntity> findById(int id) {
		// TODO Auto-generated method stub
		return workerRepository.findById(id);
	}

}
