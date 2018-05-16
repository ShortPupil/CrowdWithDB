package crowdtag.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import crowdtag.hibernate.entity.AdministratorEntity;

public interface AdministratorRepository extends JpaRepository<AdministratorEntity, Integer>{

}
