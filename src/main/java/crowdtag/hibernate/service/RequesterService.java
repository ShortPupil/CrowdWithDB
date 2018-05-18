package crowdtag.hibernate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import crowdtag.hibernate.entity.RequesterEntity;

public interface RequesterService {

	/**son
	 * findAll
	 * @return
	 */
	@Autowired(required=true)
	List<RequesterEntity> findAll();
	
	/**
	 * @return null or T
	 * */
	Optional<RequesterEntity> findById(int id);
	
	/**
	 * Save
	 * @param city
	 */
	int save(RequesterEntity entity);
	
	void delete(int id);
}
