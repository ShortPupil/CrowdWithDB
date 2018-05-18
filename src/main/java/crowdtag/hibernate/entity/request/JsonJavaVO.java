package crowdtag.hibernate.entity.request;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

public class JsonJavaVO {
	
    public JsonJavaVO(String id, String requesterId, String content, int standard, int point, String type, String state,
			ArrayList<String> imagePath, List<Images> images) {
		this.id = id;
		this.requesterId = requesterId;
		this.content = content;
		this.standard = standard;
		this.point = point;
		this.type = type;
		this.state = state;
		this.imagePath = imagePath;
		this.images = images;
	}
	private String id;
    /**发起者的id*/
    private String requesterId;
    private String content;
    /**需要的人数*/
   	private int standard;
   	/**每张图的积分*/
   	private int point;
    private String type;//可能的值为：AreaTag，ClassTag，FrameTag（区域标注，分类标注，框标注）
    /**该数据集的状态 completed 完成,processing 进行中,canceled 取消（暂停）*/
    private String state;
    private ArrayList<String> imagePath;
    private List<Images> images;
    

    public void setId(String id) {
         this.id = id;
     }
     public String getId() {
         return id;
     }

    public void setContent(String content) {
         this.content = content;
     }
     public String getContent() {
         return content;
     }

    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

    public void setImages(List<Images> images) {
         this.images = images;
     }
     public List<Images> getImages() {
         return images;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

}