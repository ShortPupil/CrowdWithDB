package crowdtag.hibernate.repository.request;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import crowdtag.hibernate.entity.request.Images;
import crowdtag.hibernate.entity.request.Records;
import crowdtag.hibernate.entity.request.RequestEntity;

@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, Long>{

	/**通过request查找完成它的user
	 * @param RequestEntity(可用findById)
	 * @return 包含user ID的list*/
	@Query("select records.userId "
			+ "from Records records "
			+ "where records.images in "
			+ "(select i from Images i where i.request = ?1)") 
	List<Long> findWorkerIdByRequestId(RequestEntity request);
	
	/**通过workerid查找他完成的request
	 * @param  long userId
	 * @return 包含RequestEntity的list*/
	@Query("select r "
			+ "from RequestEntity r "
			+ "where r in "
			+ "(select i.request from Images i where i in "
			+ "(select records.images from Records records where records.userId = ?1))")
	List<RequestEntity> findRequestByWorkerId(Long userId);
	
	/**通过workerid查找他完成的image
	 * @param  long userId
	 * @return 包含Images的list*/
	@Query("select i "
			+ "from Images i "
			+ "where i in "
			+ "(select r.images from Records r where r.userId = ?1)")
	List<Images> findImageByWorkerId(Long userId);
	
	/**通过工人id获得他完成的records*/
	@Query("select r from Records r where r.userId = ?1")
	List<Records> findRecordByWorkerId(long workerId);
	
	
	
	/**通过userid查找他可完成的image
	 * @param long workerID
	 * @return images*/
	@Query("select i "
			+ "from Images i "
			+ "where i.request = ?1 "
			+ "and i not in "
			+ "(select r.images from Records r where r.userId=?2)")
	Images findOptionalImagesByWorkerId(RequestEntity request, long userId);

	/**通过workerId查找他完成过的类型*/
	@Query("select r.tags "
			+ "from RequestEntity r "
			+ "where r in "
			+ "(select i.request from Images i where i in "
			+ "(select re.images from Records re where re.userId=?1))")
	ArrayList<String> findTypeByworkerId(Long workerId);

	/**通过recordsid查找他对应的request需要的数量*/
	@Query("select re.standard "
			+ "from RequestEntity re "
			+ "where re in "
			+ "(select i.request from Images i where i in"
			+ "(select r.images from Records r where r.id = ?1))")
	int findStrandardByRecords(Long recordId);

}
