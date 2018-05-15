package crowdtag.hibernate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crowdtag.hibernate.entity.RequestJsonEntity;
import crowdtag.hibernate.repository.RequestJsonRepository;

@Service
public class RequestJsonServiceImpl implements RequestJsonService{

	@Autowired
	private RequestJsonRepository requestJsonRepository;

	
	@Override
	public List<RequestJsonEntity> findAll() {
		// TODO Auto-generated method stub
		return requestJsonRepository.findAll();
	}

	@Override
	public void save(RequestJsonEntity entity) {
		// TODO Auto-generated method stub
		requestJsonRepository.save(entity);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		requestJsonRepository.deleteById(id);
	}

	@Override
	public void delete(RequestJsonEntity entity) {
		// TODO Auto-generated method stub
		requestJsonRepository.delete(entity);
	}

}
