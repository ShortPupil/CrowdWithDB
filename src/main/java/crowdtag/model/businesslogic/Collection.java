package crowdtag.model.businesslogic;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import crowdtag.hibernate.entity.request.Images;
import crowdtag.hibernate.entity.request.RequestEntity;
import crowdtag.hibernate.entity.request.RequestType;
import crowdtag.hibernate.entity.request.State;
import crowdtag.hibernate.repository.request.RequestRepository;

public class Collection {
	
/**requestId 同时也是CollectionId*/
private long id;
/**该数据集的一个图片*/
private Images image;
/**上面的图片路径*/
private String imagepath;

/**其余属性和RequestEntity相同 介绍*/
/**内容*/
private String content;
/**积分*/
private int point;
/***/
private long requesterId;	
/***/
private String name;
/***/
private int standard;  
/***/
private RequestType type;
/***/
private String tags; 
/**状态，已完成、未完成*/
private State state; 
/**完成进度*/
@SuppressWarnings("unused")
private double state_process;
/**限制效率*/ 
private double efficiency_limit;
/**限制准确度*/ 
private double accuracy_limit;

public Collection(RequestEntity request) {
	this.id = request.getId();
	this.image = request.getImages().get(0);
	this.imagepath = request.getImages().get(0).getPath();
	this.content = request.getContent();
	this.point = request.getPoint();
	this.requesterId = request.getRequesterId();
	this.name = request.getName();
	this.standard = request.getStandard();
	this.type = request.getType();
	this.tags = request.getTags();
	this.state = request.getState();
	this.efficiency_limit = request.getEfficiency_limit();
	this.accuracy_limit = request.getAccuracy_limit();
}

public void setContent(String content) {
    this.content = content;
}
public String getContent() {
    return content;
}
public void setImages(Images images) {
    this.image = images;
}
public Images getImages() {
    return image;
}
public long getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getPoint() {
	return point;
}
public void setPoint(int point) {
	this.point = point;
}
public long getRequesterId() {
	return requesterId;
}
public void setRequesterId(long requesterId) {
	this.requesterId = requesterId;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getStandard() {
	return standard;
}
public void setStandard(int standard) {
	this.standard = standard;
}
public RequestType getType() {
	return type;
}
public void setType(RequestType type) {
	this.type = type;
}
public String getTags() {
	return tags;
}
public void setTags(String tags) {
	this.tags = tags;
}
public State getState() {
	return state;
}
public void setState(State state) {
	this.state = state;
}
public double getEfficiency_limit() {
	return efficiency_limit;
}
public void setEfficiency_limit(double efficiency_limit) {
	this.efficiency_limit = efficiency_limit;
}
public double getAccuracy_limit() {
	return accuracy_limit;
}
public void setAccuracy_limit(double accuracy_limit) {
	this.accuracy_limit = accuracy_limit;
}

public String getImagepath() {
	return imagepath;
}

public void setImagepath(String imagepath) {
	this.imagepath = imagepath;
}

public double getState_process() {
	return this.state_process;
}

public void setState_process(double state_process) {
	this.state_process = state_process;
}
}
