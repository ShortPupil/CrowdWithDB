package crowdtag.model.businesslogic;

import java.util.Map;
import crowdtag.hibernate.entity.request.RequestType;

//getUnfinishedTask getFinishedTask 确定是一张图片
public class Task {
private long workerID; //给的
private long imageID; //某个worker没完成的imageId，传给一个image类
private String imagePath;
private RequestType type; //
private String question; //
private Map<Integer, String> answerMap;
private int point;
public Task(long workerID,long imageId, String imagePath, RequestType type,String question,Map<Integer,String> answerMap,int point) {
	this.answerMap=answerMap;
	this.imageID = imageId;
	this.imagePath = imagePath;
	this.question=question;
	this.type=type;
	this.workerID=workerID;
	this.point=point;
}

public int getPoint() {
	return point;
}
public void setPoint(int point) {
	this.point=point;
}
public long getworkerId() {
	return workerID;
}
public void setId(long id) {
	this.workerID = id;
}
public void setType(RequestType type) {
    this.type = type;
}
public RequestType getType() {
    return type;
}
public void setQuestion(String question) {
    this.question=question;
}
public String getQuestion() {
    return question;
}

public void setAnswer( Map<Integer, String> answerMap) {
    this.answerMap=answerMap;
}
public Map<Integer, String> getAnswer() {
    return answerMap;
}

/**
 * @return the imageID
 */
public long getImageID() {
	return imageID;
}

/**
 * @param imageID the imageID to set
 */
public void setImageID(long imageID) {
	this.imageID = imageID;
}

/**
 * @return the imagePath
 */
public String getImagePath() {
	return imagePath;
}

/**
 * @param imagePath the imagePath to set
 */
public void setImagePath(String imagePath) {
	this.imagePath = imagePath;
}
}
