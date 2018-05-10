package crowdtag.model.request;

import java.util.ArrayList;

/**创建任务时使用*/
public class RequestModel {

	/**创建任务类型，第一种：ClassTag，第二种：FrameTag，第三种：AreaTag*/
	private String type;
	
	/**任务名称*/
	private String name;

	/**faqizheid*/
	private String requesterId;
	
	/**对任务的描述*/
	private String description;

	/**任务中的图片路径*/
	private ArrayList<String> imagePath;

	/**需要的人数**/
	private int standard;

	/**每张图片的积分**/
	private int point;

	/**constructor*/
	public RequestModel(String t, String n, String rid, String des, ArrayList<String> ip, int st, int p) {
		this.type = t;
		this.name = n;
		this.requesterId = rid;
		this.description = des;
		this.imagePath = ip;
		this.standard = st;
		this.point = p;
	}
	
	public RequestModel() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStandard() {
		return standard;
	}

	public void setStandard(int standard) {
		this.standard = standard;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<String> getImagePath() {
		return imagePath;
	}

	public void setImagePath(ArrayList<String> imagePath) {
		this.imagePath = imagePath;
	}

	public String getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(String requesterId) {
		this.requesterId = requesterId;
	}

}
