package crowdtag.hibernate.entity.request;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import crowdtag.hibernate.entity.BaseModel;

@Entity
@Table(name = "_images")
public class Images extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6502270208309560100L;
	
	@ManyToOne
    private RequestEntity request; //-
	
	@Column(nullable = false)
    private String path; //-
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "images", cascade = CascadeType.ALL)
	@Column(nullable = false)
    private List<Records> records = new ArrayList<Records>(); //-
    
    /**状态：进行中(processing)，已完成(completed),关闭（canceled）**/
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
    private State state = State.PROCESSING;
	
	/**假如是二、三种标注，就把问题放在question里面， 假如是第一种描述也在里面*/
	@Column(nullable = true)
	private String question; //-
	
	/**假如是第一种描述，就把描述信息放在下面的属性里*/
	@ElementCollection(fetch=FetchType.EAGER, 
            targetClass=String.class) //指定集合中元素的类型  
    @CollectionTable(name="onecontent_info") //指定集合生成的表  
    @MapKeyColumn(name="one_id") //指定map的key生成的列
	@Column(nullable = true)
	//@OneToMany(cascade = CascadeType.ALL)
	private Map<Integer, String> oneContent = new HashMap<Integer, String>(); //-
	
	public Images(){}	
	public Images(RequestEntity re,String path,List<Records> records, String question, Map<Integer, String> oneContent ) {
		this.setRequest(re);
		this.setPath(path);
		this.setRecords(records);
		this.setQuestion(question);
		this.setOneContent(oneContent);
		this.setState(State.PROCESSING);
	}
	
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return the request
	 */
	public RequestEntity getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(RequestEntity request) {
		this.request = request;
	}

	/**
	 * @return the oneContent
	 */
	public Map<Integer, String> getOneContent() {
		return oneContent;
	}

	/**
	 * @param oneContent the oneContent to set
	 */
	public void setOneContent(Map<Integer, String> oneContent) {
		this.oneContent = oneContent;
	}

	/**
	 * @return the records
	 */
	public List<Records> getRecords() {
		return records;
	}

	/**
	 * @param records the records to set
	 */
	public void setRecords(List<Records> records) {
		this.records = records;
	}



}