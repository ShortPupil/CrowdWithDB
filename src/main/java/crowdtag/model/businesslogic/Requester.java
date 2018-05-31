package crowdtag.model.businesslogic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.oss.OSSClient;

import crowdtag.hibernate.entity.RequesterEntity;
import crowdtag.hibernate.entity.request.Images;
import crowdtag.hibernate.entity.request.Records;
import crowdtag.hibernate.entity.request.RequestEntity;
import crowdtag.hibernate.entity.request.RequestType;
import crowdtag.hibernate.repository.RequesterRepository;
import crowdtag.hibernate.repository.request.RequestRepository;
import crowdtag.model.businesslogic.aliyunoss.AliyunOSSClientUtil;

@Service
public class Requester {
	private RequesterEntity requesterEntity;
	public void setRequesterEntity(RequesterEntity requesterEntity) {
	    this.requesterEntity = requesterEntity;
	}
	public RequesterEntity getRequesterEntity() {
	    return requesterEntity;
	}
	
	@Autowired
	RequesterRepository rr;
	@Autowired
	RequestRepository reuqestRepository;
	
	Requester getRequester(long id) {
		
		Requester r=new Requester();
		Optional<RequesterEntity> re=rr.findById(id);
		if(re.isPresent()) {
			r.requesterEntity=re.get();
			return r;
		}
		else {
			return null;
		}
	}
	Requester login(long id,String password) {
		Requester r=new Requester();
		Optional<RequesterEntity> re=rr.findById(id);
		if(re.isPresent()){
			r.requesterEntity=re.get();
		}
		if(!r.requesterEntity.getPassword().equals(password)) {
			return null;
		}
		else return r;
	}
	Requester register(Requester requester) {
		RequesterEntity r =rr.saveAndFlush(requester.requesterEntity);//保存并更新
		requester.requesterEntity.setId(r.getId());
		return requester;
	}
	
	/**
	 * requester新建一个request，需要许多信息
	 * @param requesterId 发起者id
	 * @param standard request需要的人数
	 * @param point 该任务的积分
	 * @param type rquest的类型，变量类型为RequestState
	 * @param tags request的标签，单个
	 * @param zipFilePath 图片的zip文件
	 * @param targetPath zip文件的目标解压地址
	 * @param 该request设置的问题
	 * @param oneContent 假如是第一种标注，此为选项
	 * @return 完整的request
	 * */
	public RequestEntity addRequest(long requesterId, String content, int standard, int point, 
			RequestType type, String tags, String zipFilePath, String targetPath, String question,
			Map<Integer, String> oneContent) {
		RequestEntity re1;
		ArrayList<Images> images = new ArrayList<Images>();
		re1 = new RequestEntity(requesterId, content, standard,point,type, tags);
		re1 = reuqestRepository.saveAndFlush(re1);
		
		List<Records> records = new ArrayList<Records>();
		
		OSSClient ossClient2=AliyunOSSClientUtil.getOSSClient();
		File zipFile2 = new File(zipFilePath);
		ArrayList<File> files2 = AliyunOSSClientUtil.upzipFile(zipFile2,targetPath);
		ArrayList<String> path = AliyunOSSClientUtil.uploadObject2OSS
				(ossClient2, files2, "songzi-picture", re1.getId().longValue()+"/");  
		
		for(int i=0 ; i<path.size() ; i++) {
			images.add(new Images(re1,path.get(i),records, question, oneContent));
		}
		re1.setImages(images);
		re1 = reuqestRepository.saveAndFlush(re1);
		return re1;
	}
}
