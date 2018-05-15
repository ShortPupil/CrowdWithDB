package crowdtag.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import crowdtag.hibernate.entity.RequesterEntity;

public interface RequesterRepository extends JpaRepository<RequesterEntity, Integer>{

	
}
