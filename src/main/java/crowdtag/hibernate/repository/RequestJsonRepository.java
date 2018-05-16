package crowdtag.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import crowdtag.hibernate.entity.RequestJsonEntity;

public interface RequestJsonRepository extends JpaRepository<RequestJsonEntity, Integer>{

}
