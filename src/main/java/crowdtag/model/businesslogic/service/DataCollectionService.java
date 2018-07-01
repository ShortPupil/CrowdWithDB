package crowdtag.model.businesslogic.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crowdtag.hibernate.entity.WorkerEntity;
import crowdtag.hibernate.entity.request.Images;
import crowdtag.hibernate.entity.request.Records;
import crowdtag.hibernate.entity.request.RequestEntity;
import crowdtag.hibernate.repository.WorkerRepository;
import crowdtag.hibernate.repository.request.RequestRepository;
import crowdtag.model.businesslogic.DataCollection;

@Service
public class DataCollectionService {
	@Autowired
	public RequestRepository rs;
	@Autowired
	WorkerRepository wr;
    public ArrayList<DataCollection> getDataById(long id){
    	Optional<RequestEntity> r=rs.findById(id);	
    	ArrayList<DataCollection> D=new ArrayList<DataCollection>();
    	List<Images> images=null;
    	if(r.isPresent()) {
    	images=r.get().getImages();
    	}
    	for(int i=0;i<images.size();i++) {
    		Images I=images.get(i);
    		String question=I.getQuestion();
    		String path=I.getPath();
    		Map<Integer, String> oneContent=I.getOneContent();
    		List<Records> R=I.getRecords();
    		for(int j=0;j<R.size();j++) {
    			Optional<WorkerEntity> w=wr.findById(R.get(j).getUser());
    			String name="";
    			if(w.isPresent()) {
    			 name=w.get().getWorkername();
    			}
    			DataCollection d=new DataCollection(R.get(j).getUser(),name,question,oneContent,path,R.get(j).getTags(),R.get(j).getAnswers());
    			D.add(d);
    		}
    	}
    	System.out.println(D.size());
    	
		return D;
    	
    }
    
}
