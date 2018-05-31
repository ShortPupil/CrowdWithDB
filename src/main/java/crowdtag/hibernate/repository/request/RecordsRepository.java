package crowdtag.hibernate.repository.request;

import org.springframework.data.jpa.repository.JpaRepository;

import crowdtag.hibernate.entity.request.Records;

public interface RecordsRepository extends JpaRepository<Records, Long>{

}
