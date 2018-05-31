package crowdtag.hibernate.repository.request;

import org.springframework.data.jpa.repository.JpaRepository;

import crowdtag.hibernate.entity.request.Images;

public interface ImagesRepository extends JpaRepository<Images, Long>{

}
