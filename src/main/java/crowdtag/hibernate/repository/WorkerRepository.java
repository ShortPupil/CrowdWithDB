package crowdtag.hibernate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import crowdtag.hibernate.entity.WorkerEntity;
@Repository
public interface WorkerRepository extends JpaRepository<WorkerEntity, Long>{

	@Query("select u from WorkerEntity u where u.workername = ?1") 
	List<WorkerEntity> findByName(String name);
}
