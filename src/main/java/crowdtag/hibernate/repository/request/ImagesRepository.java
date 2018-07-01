package crowdtag.hibernate.repository.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crowdtag.hibernate.entity.request.Images;
@Repository
public interface ImagesRepository extends JpaRepository<Images, Long>{

}
