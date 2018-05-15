package crowdtag.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import crowdtag.hibernate.entity.WorkerEntity;

public interface WorkerRepository extends JpaRepository<WorkerEntity, Integer>{

}
