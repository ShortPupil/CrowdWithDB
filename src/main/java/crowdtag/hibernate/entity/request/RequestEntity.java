package crowdtag.hibernate.entity.request;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import crowdtag.hibernate.entity.BaseModel;

@Entity
@Table(name="_request")
public class RequestEntity extends BaseModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -74806146045559123L;
	
    /**发起者的id*/
	@Column(nullable = false)
    private long requesterId;	
	
	@Column(nullable = true)
	private String name;
	
	@Column(nullable = true)
	private String content;
	
    /**需要的人数*/
	@Column(nullable = false)
   	private int standard;  
	
   	/**每张图的积分*/
	@Column(nullable = false)
   	private int point; 
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
    private RequestType type;
    
	@Column(nullable = false)
	private String tags; 
    
    /**该数据集的状态 completed 完成,processing 进行中,canceled 取消（暂停）*/
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
    private State state = State.PROCESSING; 
    
	@OneToMany(fetch=FetchType.EAGER, mappedBy = "request", cascade = CascadeType.ALL)
	@Column(nullable = false)
    private List<Images> images = new ArrayList<Images>();
	
	/**限制效率*/ 
	@Column(nullable = false)
	private double efficiency_limit;
	
	/**限制准确度*/ 
	@Column(nullable = false)
	private double accuracy_limit;
	
    public RequestEntity() {}
    public RequestEntity(long requestId, String name, String content, int standard, int point, RequestType type,
    		String tags, double accuracy_limit, double efficiency_limit) {
    	this.setName(name);
    	this.setRequesterId(requestId);
    	this.setContent(content);
    	this.setStandard(standard);
    	this.setPoint(point);
    	this.setType(type);
    	this.setTags(tags);
    	//this.setImages(i);
    	this.setState(State.PROCESSING);
    	this.setEfficiency_limit(efficiency_limit);
    	this.setAccuracy_limit(accuracy_limit);
    }

    
    public void setContent(String content) {
         this.content = content;
     }
     public String getContent() {
         return content;
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

	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public long getRequesterId() {
		return requesterId;
	}
	public void setRequesterId(long requesterId) {
		this.requesterId = requesterId;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * @return the images
	 */
	public List<Images> getImages() {
		return images;
	}

	/**
	 * @param images the images to set
	 */
	public void setImages(List<Images> images) {
		this.images = images;
	}

	/**
	 * @return the type
	 */
	public RequestType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(RequestType type) {
		this.type = type;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the efficiency_limit
	 */
	public double getEfficiency_limit() {
		return efficiency_limit;
	}
	/**
	 * @param efficiency_limit the efficiency_limit to set
	 */
	public void setEfficiency_limit(double efficiency_limit) {
		this.efficiency_limit = efficiency_limit;
	}
	/**
	 * @return the accuracy_limit
	 */
	public double getAccuracy_limit() {
		return accuracy_limit;
	}
	/**
	 * @param accuracy_limit the accuracy_limit to set
	 */
	public void setAccuracy_limit(double accuracy_limit) {
		this.accuracy_limit = accuracy_limit;
	}

}