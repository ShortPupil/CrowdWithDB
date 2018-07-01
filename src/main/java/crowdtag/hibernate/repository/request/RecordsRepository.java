package crowdtag.hibernate.repository.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crowdtag.hibernate.entity.request.Records;

@Repository
public interface RecordsRepository extends JpaRepository<Records, Long>{

}
