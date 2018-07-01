package crowdtag.model.businesslogic;

import java.util.List;
import java.util.Map;


public class DataCollection {
	

private String workername;
private String question;
private Map<Integer, String> oneContent;
private String path;
private List<String> tags;
private int answers;
private Long id;
private String answer;

public String getWorkername() {
	return workername;
}
public void setWorkername(String workername) {
	this.workername = workername;
}
public String getQuestion() {
	return question;
}
public void setQuestion(String question) {
	this.question = question;
}
public Map<Integer, String> getOneContent() {
	return oneContent;
}
public void setOneContent(Map<Integer, String> oneContent) {
	this.oneContent = oneContent;
}
public DataCollection(Long id,String workername,String question,Map<Integer, String> oneContent,String path,List<String> tags,int answers) {
	this.id=id;
	this.oneContent=oneContent;
	this.question=question;
	this.path=path;
	this.tags=tags;
	this.answers=answers;
	this.workername=workername;
}
public int getAnswers() {
	return answers;
}
public void setAnswers(int answers) {
	this.answers = answers;
}
public List<String> getTags() {
	return tags;
}
public void setTags(List<String> tags) {
	this.tags = tags;
}
public String getPath() {
	return path;
}
public void setPath(String path) {
	this.path = path;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getAnswer() {
	return answer;
}
public void setAnswer(String answer) {
	this.answer = answer;
}



}



