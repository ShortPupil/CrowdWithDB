package crowdtag.hibernate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import crowdtag.hibernate.entity.AdministratorEntity;

public interface AdministratorService {

	/**son
	 * findAll
	 * @return
	 */
	@Autowired(required=true)
	List<AdministratorEntity> findAll();
	
	/**
	 * @return null or T
	 * */
	Optional<AdministratorEntity> findById(int id);
	
	/**
	 * Save
	 * @param city
	 */
	int save(AdministratorEntity entity);
	
	void delete(int id);
}
