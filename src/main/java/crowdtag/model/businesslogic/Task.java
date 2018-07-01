package crowdtag.model.businesslogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import crowdtag.hibernate.entity.request.Records;
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

//标注结果属性
/**第一种标注的选项答案*/
private int answer;
/**第二三种标注的点答案*/
private List<String> tags= new ArrayList<String>();
/**时间*/
private double time;

public Task() {
}
public Task(long workerID,long imageId, String imagePath, RequestType type,String question,Map<Integer,String> answerMap,int point) {
	this.answerMap=answerMap;
	this.imageID = imageId;
	this.imagePath = imagePath;
	this.question=question;
	this.type=type;
	this.setWorkerID(workerID);
	this.point=point;
}

public int getPoint() {
	return point;
}
public void setPoint(int point) {
	this.point=point;
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

public void setAnswerMap( Map<Integer, String> answerMap) {
    this.answerMap=answerMap;
}
public Map<Integer, String> getAnswerMap() {
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

/**
 * @return the tags
 */
public List<String> getTags() {
	return tags;
}

/**
 * @param tags the tags to set
 */
public void setTags(List<String> tags) {
	this.tags = tags;
}

/**
 * @return the answer
 */
public int getAnswer() {
	return answer;
}

/**
 * @param answer the answer to set
 */
public void setAnswer(int answer) {
	this.answer = answer;
}

/**
 * @return the time
 */
public double getTime() {
	return time;
}

/**
 * @param time the time to set
 */
public void setTime(double time) {
	this.time = time;
}
public long getWorkerID() {
	return workerID;
}
public void setWorkerID(long workerID) {
	this.workerID = workerID;
}

}
