package crowdtag.hibernate.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crowdtag.hibernate.entity.WorkerPortraitEntity;
import crowdtag.hibernate.repository.WorkerPortraitRepository;

@Service
public class WorkerPortraitServiceImpl implements HibernateService<WorkerPortraitEntity>{

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
	public void delete(WorkerPortraitEntity entity) {
		// TODO Auto-generated method stub
		workerPortraitRepository.delete(entity);
	}

	@Override
	public Optional<WorkerPortraitEntity> findById(int id) {
		// TODO Auto-generated method stub
		return workerPortraitRepository.findById(id);
	}

}
