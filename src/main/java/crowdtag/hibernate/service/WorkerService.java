package crowdtag.hibernate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import crowdtag.hibernate.entity.AdministratorEntity;
import crowdtag.hibernate.entity.WorkerEntity;

public interface WorkerService {

	/**son
	 * findAll
	 * @return
	 */
	@Autowired(required=true)
	List<WorkerEntity> findAll();
	
	/**
	 * @return null or T
	 * */
	Optional<WorkerEntity> findById(int id);
	
	/**
	 * Save
	 * @param city
	 */
	int save(WorkerEntity entity);
	
	void delete(int id);
}
