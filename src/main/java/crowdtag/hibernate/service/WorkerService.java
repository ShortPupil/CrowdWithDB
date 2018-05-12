package crowdtag.hibernate.service;

import java.util.List;

import crowdtag.hibernate.entity.WorkerEntity;

public interface WorkerService {

	/**
	 * findAll
	 * @return
	 */
	List<WorkerEntity> findAll();
	
	/**
	 * Save
	 * @param city
	 */
	void save(WorkerEntity entity);
	
	void delete(String id);
	
	void delete(WorkerEntity entity);
}
