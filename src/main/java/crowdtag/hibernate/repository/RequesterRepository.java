package crowdtag.hibernate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import crowdtag.hibernate.entity.RequesterEntity;
@Repository
public interface RequesterRepository extends JpaRepository<RequesterEntity, Long>{
	
	@Query("select r from RequesterEntity r where r.name = ?1") 
	List<RequesterEntity> findByName(String name);
	
}
