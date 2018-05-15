package crowdtag.hibernate.service;

import java.util.List;

import crowdtag.hibernate.entity.RequestJsonEntity;

public interface RequestJsonService {

	/**
	 * findAll
	 * @return
	 */
	List<RequestJsonEntity> findAll();
	
	/**
	 * Save
	 * @param city
	 */
	void save(RequestJsonEntity entity);
	
	void delete(int id);
	
	void delete(RequestJsonEntity entity);
}
