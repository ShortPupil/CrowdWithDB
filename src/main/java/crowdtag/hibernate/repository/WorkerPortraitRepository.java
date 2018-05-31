package crowdtag.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crowdtag.hibernate.entity.WorkerPortraitEntity;

@Repository
public interface WorkerPortraitRepository extends JpaRepository<WorkerPortraitEntity, Long> {

}
