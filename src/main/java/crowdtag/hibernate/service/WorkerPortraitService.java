package crowdtag.hibernate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import crowdtag.hibernate.entity.AdministratorEntity;
import crowdtag.hibernate.entity.WorkerPortraitEntity;

public interface WorkerPortraitService {

	/**son
	 * findAll
	 * @return
	 */
	@Autowired(required=true)
	List<WorkerPortraitEntity> findAll();
	
	/**
	 * @return null or T
	 * */
	Optional<WorkerPortraitEntity> findById(int id);
	
	/**
	 * Save
	 * @param city
	 */
	int save(WorkerPortraitEntity entity);
	
	void delete(int id);
}
