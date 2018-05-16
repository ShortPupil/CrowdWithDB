package crowdtag.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import crowdtag.hibernate.entity.WorkerPortraitEntity;

public interface WorkerPortraitRepository extends JpaRepository<WorkerPortraitEntity, Integer> {

}
