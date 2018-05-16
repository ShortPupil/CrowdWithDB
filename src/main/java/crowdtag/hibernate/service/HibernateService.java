package crowdtag.hibernate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

public interface HibernateService<T> {

	/**son
	 * findAll
	 * @return
	 */
	@Autowired(required=true)
	List<T> findAll();
	
	/**
	 * @return null or T
	 * */
	Optional<T> findById(int id);
	
	/**
	 * Save
	 * @param city
	 */
	int save(T entity);
	
	void delete(int id);
	
	void delete(T entity);
}
