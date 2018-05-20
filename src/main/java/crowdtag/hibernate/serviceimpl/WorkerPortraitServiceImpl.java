package crowdtag.hibernate.serviceimpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crowdtag.hibernate.entity.WorkerPortraitEntity;
import crowdtag.hibernate.repository.WorkerPortraitRepository;
import crowdtag.hibernate.service.WorkerPortraitService;

@Service
public class WorkerPortraitServiceImpl implements WorkerPortraitService{

	@Autowired
	private WorkerPortraitRepository workerPortraitRepository;
	
	@Override
	public List<WorkerPortraitEntity> findAll() {
		// TODO Auto-generated method stub
		return workerPortraitRepository.findAll();
	}

	@Override
	@Transactional
	public int save(WorkerPortraitEntity entity) {
		// TODO Auto-generated method stub
		entity = workerPortraitRepository.saveAndFlush(entity);
		return entity.getId();
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		workerPortraitRepository.deleteById(id);
	}


	@Override
	public Optional<WorkerPortraitEntity> findById(int id) {
		// TODO Auto-generated method stub
		return workerPortraitRepository.findById(id);
	}

}
