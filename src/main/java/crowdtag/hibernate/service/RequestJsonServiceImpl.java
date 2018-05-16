package crowdtag.hibernate.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import crowdtag.hibernate.entity.RequestJsonEntity;
import crowdtag.hibernate.repository.RequestJsonRepository;

public class RequestJsonServiceImpl implements HibernateService<RequestJsonEntity>{
	@Autowired
	private RequestJsonRepository requestJsonRepository;
	@Override
	public List<RequestJsonEntity> findAll() {
		// TODO Auto-generated method stub
		return requestJsonRepository.findAll();
	}

	@Override
	@Transactional
	public int save(RequestJsonEntity entity) {
		// TODO Auto-generated method stub
		entity = requestJsonRepository.saveAndFlush(entity);
		return entity.getId();
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		requestJsonRepository.existsById(id);
	}

	@Override
	public void delete(RequestJsonEntity entity) {
		// TODO Auto-generated method stub
		requestJsonRepository.delete(entity);
	}

	@Override
	public Optional<RequestJsonEntity> findById(int id) {
		// TODO Auto-generated method stub
		return requestJsonRepository.findById(id);
	}

}
