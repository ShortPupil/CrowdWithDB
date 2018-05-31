package crowdtag.hibernate.entity.request;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import crowdtag.hibernate.entity.BaseModel;

@Entity
@Table(name = "_records")
public class Records extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8376456958067453847L;
	
	@ManyToOne
    private Images images;//-
	
	@Column(nullable = false)
    private long userId;//-
	
    /**若是第一种标注 答案在这里*/
	@Column(nullable = true)
    private int answers;//-
	
    /**若是第二种标注 答案在这里*/
	@ElementCollection(fetch=FetchType.LAZY, //加载策略,延迟加载  
            targetClass=String.class) //指定集合中元素的类型  
    @CollectionTable(name="tags_info") //指定集合生成的表  
    @OrderColumn(name="t_id") //指定排序列的名称  
    private List<String> tags= new ArrayList<String>(); //之前的tags和nodes在数据库中用json表示 //-
	
	@Column(nullable = false)
	
    private double time; //-
	public Records(){}
	public Records(Images i, long userId, int a, List<String> t, double time) {
		this.setImages(i);
		this.setUser(userId);
		this.setAnswers(a);
		this.setTags(t);
		this.setTime(time);
	}
	
	
    public void setUser(long user) {
         this.userId = user;
     }
     public long getUser() {
         return userId;
     }
    public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}


	/**
	 * @return the images
	 */
	public Images getImages() {
		return images;
	}

	/**
	 * @param images the images to set
	 */
	public void setImages(Images images) {
		this.images = images;
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

	/**
	 * @return the answers
	 */
	public int getAnswers() {
		return answers;
	}

	/**
	 * @param answers the answers to set
	 */
	public void setAnswers(int answers) {
		this.answers = answers;
	}
}