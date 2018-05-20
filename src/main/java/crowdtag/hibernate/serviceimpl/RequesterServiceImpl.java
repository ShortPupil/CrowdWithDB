package crowdtag.hibernate.serviceimpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crowdtag.hibernate.entity.RequesterEntity;
import crowdtag.hibernate.repository.RequesterRepository;
import crowdtag.hibernate.service.RequesterService;

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
	 * @param RequesterEntity
	 */
	@Transactional
	public int save(RequesterEntity entity) {
		// TODO Auto-generated method stub
		entity = requesterRepository.saveAndFlush(entity);
		return entity.getId();
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		requesterRepository.deleteById(id);
	}
	
	@Override
	public Optional<RequesterEntity> findById(int id) {
		// TODO Auto-generated method stub
		return requesterRepository.findById(id);
	}
}
