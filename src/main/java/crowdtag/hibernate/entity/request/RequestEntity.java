package crowdtag.hibernate.entity.request;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
    
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "request", cascade = CascadeType.ALL)
	@Column(nullable = false)
    private List<Images> images = new ArrayList<Images>();
	
    public RequestEntity() {}
    public RequestEntity(long requestId, String content, int standard, int point, RequestType type,
    		String tags) {
    	this.setRequesterId(requestId);
    	this.setContent(content);
    	this.setStandard(standard);
    	this.setPoint(point);
    	this.setType(type);
    	this.setTags(tags);
    	//this.setImages(i);
    	this.setState(State.PROCESSING);
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

}