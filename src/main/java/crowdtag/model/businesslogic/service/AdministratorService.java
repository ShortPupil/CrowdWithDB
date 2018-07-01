package crowdtag.model.businesslogic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crowdtag.hibernate.entity.AdministratorEntity;
import crowdtag.hibernate.entity.RequesterEntity;
import crowdtag.hibernate.entity.WorkerEntity;
import crowdtag.hibernate.entity.WorkerPortraitEntity;
import crowdtag.hibernate.entity.request.Records;
import crowdtag.hibernate.entity.request.RequestEntity;
import crowdtag.hibernate.entity.request.RequestType;
import crowdtag.hibernate.repository.AdministratorRepository;
import crowdtag.hibernate.repository.RequesterRepository;
import crowdtag.hibernate.repository.WorkerPortraitRepository;
import crowdtag.hibernate.repository.WorkerRepository;
import crowdtag.hibernate.repository.request.RequestRepository;
import crowdtag.hibernate.result.ResponseBodyInfo;
import crowdtag.model.businesslogic.Collection;
import crowdtag.model.businesslogic.Task;
import crowdtag.model.businesslogic.Worker;

@Service
public class AdministratorService{
	@Autowired
	private AdministratorRepository ar;
	@Autowired
	private WorkerRepository workerRepository;
	@Autowired
	private WorkerPortraitRepository workerPortraitRepository;
	@Autowired
	private RequesterRepository requesterRepository;
	@Autowired
	private RequestRepository requestRepository;
	
	public ArrayList<AdministratorEntity> getAllAdministrators(){
		ArrayList<AdministratorEntity> res = new ArrayList<AdministratorEntity>();
		res.addAll(ar.findAll());
		return res;
	}
	
	public AdministratorEntity getAdministratorById (Long id) {
		Optional<AdministratorEntity> ae=ar.findById(id);
		if(ae.isPresent()) return ae.get();
		return null;
	}

	public AdministratorEntity getAdministratorByName (String name) {
		List<AdministratorEntity> ae=ar.findByName(name);
	    for(int i=0 ; i<ae.size() ; i++) {
	    	if(ae.get(i)!=null) 		
			    return ae.get(i);
		}
		return null;
	}
	/**新增管理员 需要注意的是 只能新增比自己权限低的管理员
	 * @param n 姓名
	 * @param pw 密码
	 * @param authority 新增的管理员的权限值
	 * @param theauthority 管理员的权限值
	 * @return 若是错误，包含错误信息errorText，若是正确，包含新增的对象AdministratorEntity*/
	public ResponseBodyInfo<AdministratorEntity> addAdministrator(String n, String pw, int authority, int theauthority){
		ResponseBodyInfo<AdministratorEntity> res = new ResponseBodyInfo<AdministratorEntity>();
		if(authority >= theauthority) {
			res.setErrorCode(1);
			res.setErrorText("权限过低，不能创建");
		}
		AdministratorEntity ad = new AdministratorEntity(n, pw, authority);
		ad = ar.saveAndFlush(ad);
		res.setData(ad);
		return res;
	}
	
	/**删除管理员 需要注意的是 只能删除比自己权限低的管理员
	 * @param administratorId 管理员id
	 * @param authority 进行删除操作的管理员权限
	 * @return 若是错误，包含错误信息errorText
	 * */
	public ResponseBodyInfo<AdministratorEntity> deleteAdministrator(Long administratorId, int authority){
		ResponseBodyInfo<AdministratorEntity> res = new ResponseBodyInfo<AdministratorEntity>();
		if(administratorId.equals(null)) {
			res.setErrorCode(1);
			res.setErrorText("id: null");
		}
		else {
			Optional<AdministratorEntity> ad = ar.findById(administratorId);
			if(!ad.isPresent()) {
				res.setErrorCode(1);
				res.setErrorText("don't have such id");
			}
			else if(ad.get().getAuthority() >= authority) {
				res.setErrorCode(2);
				res.setErrorText("权限过低，不能删除");
			}
			else {
				ar.deleteById(administratorId);
			}
		}
		return res;
	}
	
	public AdministratorEntity register (AdministratorEntity administrator) {
		AdministratorEntity a=ar.saveAndFlush(administrator);
		return a;
	}

	public AdministratorEntity login (long id,String password) {
		Optional<AdministratorEntity> ae=ar.findById(id);
		if(ae.isPresent()) {
			if(ae.get().getPassword().equals(password)) return ae.get();
		}
		return null;
	}
	
	/*以下为管理worker的方法*/
	public Worker addWorker(String name, String password) {
		Worker worker = new Worker();
		WorkerEntity w1 = new WorkerEntity(name, password);
		WorkerPortraitEntity w2 = new WorkerPortraitEntity();
		w1 = workerRepository.saveAndFlush(w1);
		w2 = workerPortraitRepository.saveAndFlush(w2);
		worker.setWorkerEntity(w1);
		worker.setWorkerPortraitEntity(w2);
		return worker;
	}
	public ResponseBodyInfo<Worker> deleteByWorkerId(Long workerId){
		ResponseBodyInfo<Worker> res = new ResponseBodyInfo<Worker>();
		if(workerId.equals(null)) {
			res.setErrorCode(1);
			res.setErrorText("id: null");
		}
		else if(!workerRepository.findById(workerId).isPresent()) {
			res.setErrorCode(2);
			res.setErrorText("no such id");
		}
		else {
			workerRepository.deleteById(workerId);
			workerPortraitRepository.deleteById(workerId);
		}
		return res;
	}
	public ArrayList<Worker> findAllWorker(){
		ArrayList<Worker> res = new ArrayList<Worker>();
		List<WorkerEntity> ws = workerRepository.findAll();
		List<WorkerPortraitEntity> wps = workerPortraitRepository.findAll();
		for(int i=0 ; i<ws.size() ; i++) {
			res.add(new Worker(ws.get(i), wps.get(i)));
		}
		return res;
	}
	/**通过workerid查找他完成的task*/
	public ArrayList<Task> findTaskByWokerId(Long workerId){	
		ArrayList<Task> res = new ArrayList<Task>();
		List<Records> records = requestRepository.findRecordByWorkerId(workerId);
		for(int i=0 ; i<records.size() ; i++) {
			Records r = records.get(i);
			Task t = new Task(workerId, r.getImages().getId(), r.getImages().getPath(),
					r.getImages().getRequest().getType(), r.getImages().getQuestion(),
					r.getImages().getOneContent(), r.getImages().getRequest().getPoint());
			t.setAnswer(r.getAnswers());
			t.setTags(r.getTags());
			t.setTime(r.getTime());
			res.add(t);
		}
		return res;
	}
	
	
	/*以下为管理发起者的方法*/
	public RequesterEntity addRequester(String name, String password, String email, String address, String company) {
		RequesterEntity requester = new RequesterEntity(name, password, email, address, company);
		requester = requesterRepository.saveAndFlush(requester);
		return requester;
	}
	public ResponseBodyInfo<RequesterEntity> deleteByRequesterId(Long requestId){
		ResponseBodyInfo<RequesterEntity> res = new ResponseBodyInfo<RequesterEntity>();
		if(requestId.equals(null)) {
			res.setErrorCode(1);
			res.setErrorText("id: null");
		}
		else if(!requesterRepository.findById(requestId).isPresent()) {
			res.setErrorCode(2);
			res.setErrorText("no such id");
		}
		else {
			requesterRepository.deleteById(requestId);
		}
		return res;
	}
	public ArrayList<RequesterEntity> findAllRequester(){
		ArrayList<RequesterEntity> res = new ArrayList<RequesterEntity>();
		res.addAll(requesterRepository.findAll());
		return res;
	}
	/**通过workerid查找他完成的request*/
	public ArrayList<Collection> findCollectionByRequesterId(Long requesterId){
		List<RequestEntity> rs = requestRepository.findRequestByRequesterId(requesterId);
		ArrayList<Collection> res = new ArrayList<Collection>();
		for(int i=0 ; i<rs.size() ; i++) {	
			res.add(new Collection(rs.get(i)));
		}
		return res;
	}
	
	
	/*以下为管理request的方法*/
	public ArrayList<RequestEntity> findAllRequest(){
		ArrayList<RequestEntity> res = new ArrayList<RequestEntity>();
		res.addAll(requestRepository.findAll());
		return res;
	}
	public ResponseBodyInfo<RequestEntity> deleteByRequestId(Long requestId){
		ResponseBodyInfo<RequestEntity> res = new ResponseBodyInfo<RequestEntity>();
		if(requestId.equals(null)) {
			res.setErrorCode(1);
			res.setErrorText("id: null");
		}
		requestRepository.deleteById(requestId);
		return res;
	}
}
