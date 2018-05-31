package crowdtag.model.statistics;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.fitting.GaussianCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.springframework.beans.factory.annotation.Autowired;

import crowdtag.hibernate.entity.WorkerPortraitEntity;
import crowdtag.hibernate.entity.request.Images;
import crowdtag.hibernate.entity.request.Records;
import crowdtag.hibernate.repository.request.RequestRepository;
import crowdtag.model.businesslogic.JsonTransformation;
import crowdtag.model.businesslogic.Nodes;
import crowdtag.model.businesslogic.Tags;

public class calculate {
	@Autowired
	public RequestRepository rs;
	//定义worker的偏好
public WorkerPortraitEntity pref(WorkerPortraitEntity w) {

	
	//根据workerid返回给String type zzh
	ArrayList<String> s=rs.findTypeByworkerId(w.getId());
	
	String pre=w.getPreference();
	int count=0;
	
	ArrayList<String> p=new ArrayList<String>();
	ArrayList<Integer> l=new ArrayList<Integer>();
 
	for(int i=0;i<s.size();i++) {
		
		if(s.get(i).equals(pre)) {
			count++;
		}
		//当不是preference的类型的时候
		else {
			//如果含有这个类型，int加一
			 if(p.contains(s.get(i))) {
				 int c=l.get(p.indexOf(s.get(i)));
				 l.set(p.indexOf(s.get(i)), c+1);
				
			}
			 else {
				 p.add(s.get(i));
				 l.add(1);
			 }
		}
	}
	if(count/s.size()<0.7) {
		int max=l.get(0);
		for(int i=1;i<l.size();i++) {
			if(max<l.get(i)) {
				max=l.get(i);
			}
		}
		
		w.setPreference(p.get(l.indexOf(max)));
	}
	return w;
}
	//定义worker的准确度
//zzh
//getImageByWorkerId
//getRecordsByImage
//
public WorkerPortraitEntity accur(WorkerPortraitEntity w) {
	
	List<Images> s =rs.findImageByWorkerId(w.getId());

	int correct=0;
	for(int i=0;i<s.size();i++) {
		
		if(s.get(i).getOneContent()!=null) {
		//仅仅完成第一种分类标注的正确性		
		List<Records> d=s.get(i).getRecords();
		
		int count=0;
		int choose=0;
		int num=0;
		for(int j=0;j<d.size();j++) {
			if(d.get(j).getUser()==w.getId()) {
				choose=d.get(j).getAnswers();
				num=j;
				break;
			}
		}
		for(int n=0;n<d.size();n++) {
			if(n==num) {
				continue;
			}
			if(d.get(n).getAnswers()==choose) {
				count++;
			}
		}
		if(count/d.size()>=0.65) {
			correct++;
		}
		}
		
		else {
			JsonTransformation J=new JsonTransformation();
			//ArrayList<RequestEntity> t=r.getRequestByID(j.getId());
			ArrayList<Images> t=new ArrayList<Images>();
			ArrayList<Double> ix=new ArrayList<Double>(); 
			ArrayList<Double> iy=new ArrayList<Double>(); 
			double x=0;
			double y=0;	
			for(int h=0;h<t.size();h++) {
					Images I=t.get(h);
					List<Records> R = I.getRecords();
					for(int f=0;f<R.size();f++) {
						Records r=R.get(f);
						
							List<String> S=r.getTags();
							for(int k=0;k<S.size();k++) {
								Tags T=J.transferJsonToVO(S.get(k));
								List<Nodes> N=T.getNodes();
								for(int q=0;q<N.size();q++) {
									x=x+N.get(q).getScaleX();
									y=y+N.get(q).getScaleY();
									ix.add(N.get(q).getScaleX());
									iy.add(N.get(q).getScaleY());
								}
							}
						}
					}
			
		
			//获得重心，待用
			//double avex=x/t.size();
			//double avey=y/t.size();
			
			WeightedObservedPoints obs = new WeightedObservedPoints();
			for(int c=0;c<ix.size();c++) {
				obs.add(ix.get(c),iy.get(c));
			}
			
			double[] parameters = GaussianCurveFitter.create().fit(obs.toList());
			//拟合正态分布，获得拟合的参数值
			double omg=1/(Math.sqrt(2*Math.PI)*parameters[0]);
			double miu=parameters[1];
			double low=miu-(omg/(Math.sqrt(ix.size())))*1.96;
			double high=miu+(omg/(Math.sqrt(ix.size())))*1.96;
			 if(low<x&&x<high) {
				 if(parameters[0]*0.8<y&&y<parameters[0]*1.2) {
					 correct++;
				 }
			 }
					
		}
	}
	double accuracy =correct/s.size();
	w.setAccuracy(accuracy);
	return w;
	
}

//效率
//getRecordByWorkerId
public WorkerPortraitEntity effi(WorkerPortraitEntity w) {
	
	List<Records> s=rs.findRecordByWorkerId(w.getId());
	
	double time=0;
	for(int i=0;i<s.size();i++) {
		time=time+s.get(i).getTime();
		
	}
	double efficiency= time/s.size();
	w.setEfficiency(efficiency);
	return w;
}
}
