package crowdtag.hibernate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import crowdtag.hibernate.entity.AdministratorEntity;

@Repository
public interface AdministratorRepository extends JpaRepository<AdministratorEntity, Long>{
	
	@Query("select a from AdministratorEntity a where a.name = ?1") 
	List<AdministratorEntity> findByName(String name);
}
