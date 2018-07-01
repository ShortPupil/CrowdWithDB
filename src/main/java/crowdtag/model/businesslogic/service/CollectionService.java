package crowdtag.model.businesslogic.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crowdtag.hibernate.entity.WorkerPortraitEntity;
import crowdtag.hibernate.entity.request.Images;
import crowdtag.hibernate.entity.request.RequestEntity;
import crowdtag.hibernate.repository.WorkerPortraitRepository;
import crowdtag.hibernate.repository.request.RequestRepository;
import crowdtag.hibernate.result.ResponseBodyInfo;
import crowdtag.model.businesslogic.Collection;
@Service
public class CollectionService{
	
	
	class SortByPointUp implements Comparator<Object> {
	    public int compare(Object o1, Object o2) {
	     Collection s1 =  (Collection)o1;
	     Collection s2 = (Collection) o2;
	    if (s1.getPoint() > s2.getPoint())
	      return 1;
	     return -1;
	    }
	   }

	class SortByPointDown implements Comparator<Object> {
	    public int compare(Object o1, Object o2) {
	     Collection s1 =  (Collection)o1;
	     Collection s2 = (Collection) o2;
	    if (s1.getPoint() > s2.getPoint())
	      return -1;
	     return 1;
	    }
	   }
	//全局变量
	public SortByPointUp sup;
	public SortByPointDown sdown;
	
	@Autowired
	private RequestRepository rs ;

	public Collection changeRequestToCollection(RequestEntity request) {
		return new Collection(request);
	}
	
	/**collection包含一个任务（图片集）的一个图片（作为封面），该任务的内容，该任务的奖励点数，对应的数据集id（用作跳转到标注任务）
	search_key模糊查询的参数
	search_type查询类型充。现有的排序是order=point_positive,按点数正序（大数在前）。
	order=point_negative,与前面的相反。order默认值为point_positive
	返回包含以上条件的
	search_tag查询内容标签
	order用于排序，可扩collection*/
	//夏春雨写
	public ArrayList<Collection> search(String search_key,ArrayList<String> search_type, ArrayList<String> search_tag,String order){
	    ArrayList<Collection> C=new ArrayList<Collection>();
	    //RequestRepository rs;
		
	    List<RequestEntity> l=rs.findAll();
	    boolean quit=false;
	    Collection c;
	    
	    for(int i=0;i<l.size();i++) {
	    	//先判断是否符合type，tag
	    	quit=false;
	    	if(search_type!=null)
	    	for(int j=0;j<search_type.size();j++) {
	    		if(l.get(i).getType().toString().equals(search_type.get(j))) {break;}
	    		if(j==search_type.size()-1) {
	    			quit=true;
	    		}
	    	}
	    	if(quit) {
	    		continue;
	    	}
	    	if(search_tag!=null)
	    	for(int k=0;k<search_tag.size();k++) {
	    		if(l.get(i).getTags().equals(search_tag.get(k))) {break;}
	    		if(k==search_tag.size()-1) {
	    			quit=true;
	    		}
	    	}
	    	if(quit) {
	    		continue;
	    	}
	    	System.out.println("tyep and tag ");
	    	//判断模糊查找
	    	if(search_key!=null) {
	    			for(int m=0;m<l.get(i).getImages().size();m++) {
	    				if(l.get(i).getImages().get(m).getQuestion().contains(search_key)) {
	    		        	c=new Collection(l.get(i));
	    		        	C.add(c);
	    		        	System.out.println("getsearch");
	    		        	break;
	    			}
	    			}		
	    			 }
	    	else {
	    		c=new Collection(l.get(i));
	    		C.add(c);
	    	}
	    }
	    sup = new SortByPointUp();
	    sdown=new SortByPointDown();
	    if(order==null||order.equals("point_positive")) {
	    C.sort(sup);}
	    else {
	    	C.sort(sdown);
	    }
		return C;
		
	}

	/**获得广告，主页，未完成的*/
	//夏春雨写
	@Autowired
	public WorkerPortraitRepository wp;
	
	/**
	 * @param workerid 工人id（Long类型）
	 * @param num
	 * */
	public ArrayList<Collection> getAds(long workerId,int num){
		//System.out.println("ok");
		//ArrayList<Collection> C= search(null,null,null,null);
		//System.out.println("ok?");
		ArrayList<Collection> C=new ArrayList<Collection>();
	    //RequestRepository rs;
		
	    List<RequestEntity> l=rs.findAll();
	   // boolean quit=false;
	    Collection c;
	    System.out.println(l.size());
	    for(int i=0;i<l.size();i++) {
	    	c=new Collection(l.get(i));
	    	System.out.println(l.get(i).getId());
    		C.add(c);
	    }
		ArrayList<Collection> C1=new ArrayList<Collection>();
		int count=0;
		if(workerId==-1) {
			C=search(null,null,null,null);
			for(int i=0;i<num;i++) {
				C1.add(C.get(i));
			}
			return C1;
		}
		else {
		Optional<WorkerPortraitEntity> w=wp.findById(workerId);
		//if(!w.isPresent()) return null;
		
		String p=w.get().getPreference();
		if(p==""||p==null) {
			C=search(null,null,null,null);
			for(int i=0;i<num;i++) {
				C1.add(C.get(i));
			}
			return C1;
		}
		else {
		for(int i=0;i<C.size();i++) {
			Optional<RequestEntity> r=rs.findById(C.get(i).getId());
			//if(!r.isPresent())System.out.println('k');
			if(!r.isPresent()) return null;
			if(r.get().getTags().equals(p)) {
				C1.add(C.get(i));
				count++;
			}
			if(count==num) {
				
				return C1;
			}
		}}}
		
		return C1;
		
	}

	/**通过workerid获得他已完成的collection*/
	public ArrayList<Collection> getFinishedCollectionByWorkerId(Long workerId){
		ArrayList<Collection> res = new ArrayList<Collection>();
		List<RequestEntity> request = rs.findRequestByWorkerId(workerId);
		for(int i=0 ; i<request.size() ; i++) {
			res.add(changeRequestToCollection(request.get(i)));
		}
		return res;
	}
	
	public ResponseBodyInfo<Double> getState_process(Long id) {
		ResponseBodyInfo<Double> res = new ResponseBodyInfo<Double>();
		if(id.equals(null)) {
			res.setErrorCode(1);
			res.setErrorText("id: null");
		}
		else {
			Optional<RequestEntity> r = rs.findById(id);
			if(!r.isPresent()) {
				res.setErrorCode(2);
				res.setErrorText("do not exist id");
			}else {
				RequestEntity request = r.get();
				List<Images> images = rs.findById(id).get().getImages();
				double recordsum = 0;
				for(int i=0 ; i<images.size() ; i++) {
					recordsum = recordsum+images.get(i).getRecords().size();
				}
				double need = request.getStandard()*request.getImages().size();
				res.setData(recordsum/need);
			}
		}		
		return res;
	}
}
