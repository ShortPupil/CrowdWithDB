package crowdtag.hibernate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import crowdtag.hibernate.entity.AdministratorEntity;
import crowdtag.hibernate.entity.RequestJsonEntity;

public interface RequestJsonService {

	/**son
	 * findAll
	 * @return
	 */
	@Autowired(required=true)
	List<RequestJsonEntity> findAll();
	
	/**
	 * @return null or T
	 * */
	Optional<RequestJsonEntity> findById(int id);
	
	/**
	 * Save
	 * @param city
	 */
	int save(RequestJsonEntity entity);
	
	void delete(int id);
}
