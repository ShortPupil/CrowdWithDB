package crowdtag.model.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.math3.fitting.GaussianCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crowdtag.hibernate.entity.WorkerEntity;
import crowdtag.hibernate.entity.WorkerPortraitEntity;
import crowdtag.hibernate.entity.request.Images;
import crowdtag.hibernate.entity.request.Records;
import crowdtag.hibernate.repository.WorkerPortraitRepository;
import crowdtag.hibernate.repository.WorkerRepository;
import crowdtag.hibernate.repository.request.RequestRepository;
import crowdtag.model.businesslogic.JsonTransformation;
import crowdtag.model.businesslogic.Nodes;
import crowdtag.model.businesslogic.Tags;
@Service
public class calculate{
	@Autowired
	RequestRepository rs;
	@Autowired
	WorkerPortraitRepository wpp;
	@Autowired
	WorkerRepository we;
	//定义worker的偏好
	
	//返回专家的ID
public long getProf() {
	return (long)1;
}
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
		p.remove(l.indexOf(max));
		l.remove(l.indexOf(max));
		p.add(pre);
		l.add(count);
		
	}
	
		Map<String,Integer> m = new HashMap<String,Integer>();
		for(int i=0;i<l.size();i++) {
			m.put(p.get(i), l.get(i));
		}
		
		w.setVice_preference(m);
		w=wpp.saveAndFlush(w);
	return w;
}
	//定义worker的准确度
//zzh
//getImageByWorkerId
//getRecordsByImage
//
public WorkerPortraitEntity accur(WorkerPortraitEntity w) {
	System.out.println(w.getId().longValue());
	List<Images> s =rs.findImageByWorkerId(w.getId());

	int correct=0;
	for(int i=0;i<s.size();i++) {
		
		if(s.get(i).getOneContent()!=null) {
		//仅仅完成第一种分类标注的正确性		
		List<Records> d=s.get(i).getRecords();
		
		double count=0;
		int choose=0;
		int num=0;
		for(int j=0;j<d.size();j++) {
			if(d.get(j).getUser()==w.getId()) {
				choose=d.get(j).getAnswers();
				num=j;
				break;
			}
		}
		//判断是否有专家
		int rightchoose=0;
		for(int j=0;j<d.size();j++) {
			if(d.get(j).getUser()==getProf()) {
				rightchoose=d.get(j).getAnswers();
				System.out.println("专家的选择："+rightchoose+"  "+s.get(i).getId());
				break;
			}
		}
		if(rightchoose!=0) {
			if(choose==rightchoose) {
				correct++;
			}
		}
		else {
		for(int n=0;n<d.size();n++) {
			if(n==num) {
				continue;
			}
			if(d.get(n).getAnswers()==choose) {
				Optional<WorkerPortraitEntity> wp=wpp.findById(d.get(n).getId());
				double credit=0;
				if(wp.isPresent()) {
					credit=wp.get().getCredit();
				}
				count=count+1*credit/100;
			    System.out.println(count);
			}
		}
		if(count/d.size()>=0.005) {
			correct++;
		}
		}
		}
		
		else {
			JsonTransformation J=new JsonTransformation();
			List<Images> t=rs.findImageByWorkerId(w.getId());
			ArrayList<Double> ix=new ArrayList<Double>(); 
			ArrayList<Double> iy=new ArrayList<Double>(); 
			double x=0;
			double y=0;	
			int count=0;
			WeightedObservedPoints obs = new WeightedObservedPoints();
			
			for(int h=0;h<t.size();h++) {
					Images I=t.get(h);
					List<Records> R = I.getRecords();
					for(int f=0;f<R.size();f++) {
						Records r=R.get(f);
							List<String> S=r.getTags();
							Optional<WorkerPortraitEntity> wp = wpp.findById(r.getUser());
							if(wp.isPresent())
							for(int k=0;k<S.size()*wp.get().getCredit()/100;k++) {
								Tags T=J.transferJsonToVO(S.get(k));
								List<Nodes> N=T.getNodes();
								for(int q=0;q<N.size();q++) {
									if(r.getUser()==w.getId()) {
										x=x+N.get(q).getScaleX();
										y=y+N.get(q).getScaleY();
										count++;
									}
									ix.add(N.get(q).getScaleX());
									iy.add(N.get(q).getScaleY());
								}
								
								
							}
						}
					for(int c=0;c<ix.size();c++) {
						obs.add(ix.get(c),iy.get(c));
					}
					x=x/count;
					y=y/count;
					double[] parameters = GaussianCurveFitter.create().fit(obs.toList());
					//拟合正态分布，获得拟合的参数值
					double omg=1/(Math.sqrt(2*Math.PI)*parameters[0]);
					double miu=parameters[1];
					double low=miu-(omg/(Math.sqrt(ix.size())))*1.96;
					
					double high=miu+(omg/(Math.sqrt(ix.size())))*1.96;
					 if(low*0.8<x&&x<high*1.2) {
						 if(parameters[0]*0.8<y&&y<parameters[0]*1.2) {
							 correct++;
						 }
					 }
					}
			
		
			//获得重心，待用
			
					
		}
	}
	System.out.println(correct);
	double accuracy =(correct*100/s.size())*0.01 ;
	System.out.println(accuracy);
	w.setAccuracy(accuracy);
	w=wpp.saveAndFlush(w);
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
	efficiency=(1/efficiency)*100;
	w.setEfficiency(efficiency);
	w=wpp.saveAndFlush(w);
	return w;
}

public int getPointRank(long id) {
	List<WorkerEntity> w=we.findAll();
	Optional<WorkerEntity> worker=we.findById(id);
	int point=0;
	if(worker.isPresent()) {
		point=worker.get().getPoint();
	}
	int rank=1;
	for(int i=0;i<w.size();i++) {
		if(w.get(i).getId()==id) {
			continue;
		}
		if(point<w.get(i).getPoint()) {
			rank++;
		}
	}
	WorkerEntity workerentity=worker.get();
	workerentity.setRank(rank);
	workerentity=we.saveAndFlush(workerentity);
	return rank;
}
//获得效率排名
public int getEffiRank(long id) {
	List<WorkerPortraitEntity> w=wpp.findAll();
	Optional<WorkerPortraitEntity> worker=wpp.findById(id);
	double effic=0;
	if(worker.isPresent()) {
		effic=worker.get().getEfficiency();
	}
	int rank=1;
	for(int i=0;i<w.size();i++) {
		if(w.get(i).getId()==id) {
			continue;
		}
		if(effic>w.get(i).getEfficiency()) {
			rank++;
		}
	}
	return rank;
}
//获得准确性排名
public int getAccuRank(long id) {
	List<WorkerPortraitEntity> w=wpp.findAll();
	Optional<WorkerPortraitEntity> worker=wpp.findById(id);
	double accu=0;
	if(worker.isPresent()) {
		accu=worker.get().getAccuracy();
	}
	int rank=1;
	for(int i=0;i<w.size();i++) {
		if(w.get(i).getId()==id) {
			continue;
		}
		if(accu<w.get(i).getAccuracy()) {
			rank++;
		}
	}
	return rank;
}
//计算信誉分
public WorkerPortraitEntity cred(WorkerPortraitEntity w,double evaluate) {
	if(evaluate!=0) {
		double c=w.getCredit();
		
		c=(c+evaluate)/2;
		w.setCredit(c);
		w=wpp.saveAndFlush(w);
		return w;
		
	}
	else {
	double c=w.getCredit();
	int total=wpp.findAll().size();
	c=(c+70*(total-getEffiRank(w.getId())+1)/total+30*(total-getAccuRank(w.getId())+1)/total)/2;
	w.setCredit(c);
	w=wpp.saveAndFlush(w);
	
	return w;
	}
}
//获得PointRankList
public ArrayList<Long> getRankList(){
	
	ArrayList<Long> s=new ArrayList<Long>();
	List<WorkerEntity> w=we.findAll();
	for(int i=0;i<w.size();i++) {
		getPointRank(w.get(i).getId());
	}
	for(int i=0;i<w.size();i++) {
		int small=0;
		if(w.get(small).getRank()>w.get(i).getRank()) {
			small=i;
		}
		s.add(w.get(small).getId());
		w.remove(small);
		
	}
	return s;
}
}
