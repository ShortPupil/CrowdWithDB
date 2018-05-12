package crowdtag.hibernate.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crowdtag.hibernate.entity.RequesterEntity;
import crowdtag.hibernate.repository.RequesterRepository;

@Service
public class RequesterServiceImpl implements RequesterService {
	@Autowired
	private RequesterRepository requesterRepository;

	/**
	 * findAll
	 * @return
	 */
	public List<RequesterEntity> findAll() {
		return requesterRepository.findAll();
	}

	/**
	 * Save
	 * @param city
	 */
	@Transactional
	public void save(RequesterEntity entity) {
		// TODO Auto-generated method stub
		requesterRepository.save(entity);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		requesterRepository.deleteById(id);
	}

	@Override
	public void delete(RequesterEntity entity) {
		// TODO Auto-generated method stub
		requesterRepository.delete(entity);
	}
}
