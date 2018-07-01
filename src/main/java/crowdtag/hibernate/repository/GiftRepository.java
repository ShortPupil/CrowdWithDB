package crowdtag.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crowdtag.hibernate.entity.GiftEntity;

@Repository
public interface GiftRepository extends JpaRepository<GiftEntity, Long>{

}