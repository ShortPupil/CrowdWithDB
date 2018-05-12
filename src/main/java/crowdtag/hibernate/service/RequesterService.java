package crowdtag.hibernate.service;

import java.util.List;

import crowdtag.hibernate.entity.RequesterEntity;

public interface RequesterService {

	/**
	 * findAll
	 * @return
	 */
	List<RequesterEntity> findAll();
	
	/**
	 * Save
	 * @param city
	 */
	void save(RequesterEntity entity);
	
	void delete(String id);
	
	void delete(RequesterEntity entity);
}
