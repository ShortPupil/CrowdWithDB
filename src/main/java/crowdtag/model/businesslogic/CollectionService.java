package crowdtag.model.businesslogic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crowdtag.hibernate.entity.WorkerPortraitEntity;
import crowdtag.hibernate.entity.request.RequestEntity;
import crowdtag.hibernate.repository.WorkerPortraitRepository;
import crowdtag.hibernate.repository.request.RequestRepository;
@Service
public class CollectionService {
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
	public RequestRepository rs ;

	/**collection包含一个任务（图片集）的一个图片（作为封面），该任务的内容，该任务的奖励点数，对应的数据集id（用作跳转到标注任务）
	search_key模糊查询的参数
	search_type查询类型
	search_tag查询内容标签
	order用于排序，可扩充。现有的排序是order=point_positive,按点数正序（大数在前）。order=point_negative,与前面的相反。order默认值为point_positive
	返回包含以上条件的collection*/
	//夏春雨写
	ArrayList<Collection> search(String search_key,ArrayList<String> search_type, ArrayList<String> search_tag,String order){
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
	    		if(l.get(i).getType().equals(search_type.get(j))) {break;}
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
	    	//判断模糊查找
	    	if(search_key!=null) {
	    			for(int m=0;m<l.get(i).getImages().size();m++) {
	    				if(l.get(i).getImages().get(m).getQuestion().contains(search_key)) {
	    		        	c=new Collection(l.get(i).getImages().get(m),l.get(i).getContent(),l.get(i).getPoint(),l.get(i).getId());
	    		        	C.add(c);
	    		        	break;
	    			}
	    			}		
	    			 }
	    	else {
	    		c=new Collection(l.get(i).getImages().get(0),l.get(i).getContent(),l.get(i).getPoint(),l.get(i).getId());
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

	ArrayList<Collection> getAds(Long workerId,int num){
		ArrayList<Collection> C=search(null,null,null,null);
		ArrayList<Collection> C1=new ArrayList<Collection>();
		int count=0;

		Optional<WorkerPortraitEntity> w=wp.findById(workerId);
		if(!w.isPresent()) return null;
		String p=w.get().getPreference();
		for(int i=0;i<C.size();i++) {
			Optional<RequestEntity> r=rs.findById(C.get(i).getId());
			if(!r.isPresent()) return null;
			if(r.get().getTags().equals(p)) {
				C1.add(C.get(i));
				count++;
			}
			if(count==num) {
				
				return C1;
			}
		}
		return null;
		
	}

}
