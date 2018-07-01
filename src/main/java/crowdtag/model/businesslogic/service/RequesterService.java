package crowdtag.model.businesslogic.service;

import java.io.File;
import java.nio.file.Files;
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
import crowdtag.hibernate.result.ResponseBodyInfo;
import crowdtag.model.businesslogic.Collection;
import crowdtag.model.businesslogic.aliyunoss.AliyunOSSClientUtil;

@Service
public class RequesterService {

	@Autowired
	private RequesterRepository rr;
	@Autowired
	private RequestRepository re;
	@Autowired
	private RequestRepository reuqestRepository;
	
	//public RequesterEntity requesterEntity;
	
	 public RequesterEntity getRequesterById(long id) {
		Optional<RequesterEntity> re=rr.findById(id);
		if(!re.isPresent()) return null;
		return re.get();
	}
	public RequesterEntity login(long id,String password) {
		Optional<RequesterEntity> re=rr.findById(id);
		if(re.isPresent()){
			if(re.get().getPassword().equals(password)) {
				return re.get();
			}
		}
		return null;
	}
	public RequesterEntity register(RequesterEntity requester) {
		RequesterEntity r =rr.saveAndFlush(requester);//保存并更新
		return r;
	}
	
	public ResponseBodyInfo<String> judgeMoney(long requesterId){
		RequesterEntity requester = rr.findById(requesterId).get();
		ResponseBodyInfo<String> res = new ResponseBodyInfo<String>();
		if(requester.getAccount() < 5) {
			res.setErrorCode(1);
			res.setErrorText("钱不够了");
		}
		return res;
	}
	
	/**
	 * requester新建一个request，需要许多信息
	 * @param requesterId 发起者id
	 * @param name 名称
	 * @param content 内容
	 * @param standard request需要的人数
	 * @param point 该任务的积分
	 * @param type rquest的类型，变量类型为RequestState
	 * @param tags request的标签，单个
	 * @param accuracy_limit  准确度限制
	 * @param efficiency_limit 效率限制
	 * @param zipFile 图片的zip文件流
	 * @param 该request设置的问题
	 * @param oneContent 假如是第一种标注，此为选项
	 * @return 完整的request
	 * */
	public RequestEntity addRequest(long requesterId, String name, String content, int standard, int point, 
			RequestType type, String tags,double accuracy_limit, double efficiency_limit, 
			File zipFile, String question,Map<Integer, String> oneContent) {
		RequestEntity re1;
		ArrayList<Images> images = new ArrayList<Images>();
		re1 = new RequestEntity(requesterId,name,content, standard,point,type, tags, accuracy_limit ,efficiency_limit);
		re1 = reuqestRepository.saveAndFlush(re1);
		
		List<Records> records = new ArrayList<Records>();
		
		OSSClient ossClient2=AliyunOSSClientUtil.getOSSClient();
		File zipFile2 = zipFile;
		ArrayList<File> files2 = AliyunOSSClientUtil.upzipFile(zipFile2,zipFile2.getParent());
		if(files2.size() == 0) {
			System.out.println("压缩文件为空");
			return null;
		}
		ArrayList<String> path = AliyunOSSClientUtil.uploadObject2OSS
				(ossClient2, files2, "songzi-picture", re1.getId().longValue()+"/");  
		
		for(int i=0 ; i<path.size() ; i++) {
			images.add(new Images(re1,path.get(i),records, question, oneContent));
		}
		re1.setImages(images);
		re1 = reuqestRepository.saveAndFlush(re1);
		RequesterEntity requester = rr.findById(requesterId).get();
		requester.setAccount(requester.getAccount()-5);
		requester = rr.saveAndFlush(requester);
		return re1;
	}
	
    public RequestType toRequestType(String name) {
    	if(name.equals("AreaTag")) return RequestType.AREATAG;
    	else if(name.equals("ClassTag")) return RequestType.CLASSTAG;
    	else if(name.equals("FrameTag")) return RequestType.FRAMETAG;
    	return null;
    }
    
    public RequesterEntity updateBasicInfo(RequesterEntity re) {
    	return rr.saveAndFlush(re);
    }
    
    public ResponseBodyInfo<RequesterEntity> updatePassword(Long id, String newpassword, String oldpassword){
    	ResponseBodyInfo<RequesterEntity> res = new ResponseBodyInfo<RequesterEntity>();
    	Optional<RequesterEntity> entity = rr.findById(id);
    	if(!entity.isPresent()) {
    		res.setErrorCode(1);
    		res.setErrorText("cannot find entity by id");
    	}
    	else if(!entity.get().getPassword().equals(oldpassword)) {
    		res.setErrorCode(3);
    		res.setErrorText("wrong old password");
    	}
    	else if(newpassword.equals(oldpassword)) {
    		res.setErrorCode(2);
    		res.setErrorText("same password");
    	}
    	else {
    		RequesterEntity re = entity.get();
    		re.setPassword(newpassword);
    		rr.saveAndFlush(re);
    	}
    	return res;
    }
    
    /**
     * 根据requesterId查找requester发起的任务
     * @param Long requesterId
     * @return ArrayList<RequestEntity>
     * */
    public ArrayList<Collection> findRequestByRequesterId(Long requesterId){
    	ArrayList<RequestEntity> requests = re.findRequestByRequesterId(requesterId);
    	ArrayList<Collection> res = new ArrayList<Collection>();
    	if(requests.size() == 0) return res;
    	for(int i=0 ; i<requests.size() ; i++) {
    		RequestEntity request = requests.get(i);
    		res.add(new Collection(request));
    	}
    	return res;
    }
}
