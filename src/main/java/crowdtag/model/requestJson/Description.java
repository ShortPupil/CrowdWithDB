package crowdtag.model.requestJson;

import java.util.Map;

public class Description {

	/**假如是二、三种标注，就把描述信息放在content里面*/
	private String twoContent;
	/**假如是第一种描述，就把描述信息放在下面的属性里*/
    private Map<String, String> oneContent;
    
	public Map<String, String> getOneContent() {
		return oneContent;
	}
	public void setOneContent(Map<String, String> oneContent) {
		this.oneContent = oneContent;
	}
	public String getTwoContent() {
		return twoContent;
	}
	public void setTwoContent(String twoContent) {
		this.twoContent = twoContent;
	}
}