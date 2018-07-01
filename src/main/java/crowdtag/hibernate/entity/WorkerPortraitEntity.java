package crowdtag.hibernate.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

@Entity
@Table(name="worker_portrait")
public class WorkerPortraitEntity extends BaseModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7552622348977882235L;

	

	/**工人准确度*/
	@Column(nullable = false)
	private double accuracy = 0;

	/**工人偏好*/
	@Column(nullable = false)
	private String preference = "";
	
	/**工人副偏好*/
	@ElementCollection(fetch=FetchType.LAZY, //加载策略,延迟加载  
            targetClass=Integer.class) //指定集合中元素的类型  
    @CollectionTable(name="vice_preference_info") //指定集合生成的表  
    @MapKeyColumn(name="vice_preference_name") //指定map的key生成的列
	@Column(nullable = true)
	//@OneToMany(cascade = CascadeType.ALL)
	private Map<String, Integer> vice_preference = new HashMap<String, Integer>(); 
	
	/**工人质量分*/
	@Column(nullable = false)
	private double credit = 60;

	/**工人效率*/
	@Column(nullable = false)
	private double efficiency = 0;
	
	public WorkerPortraitEntity() {}

	public WorkerPortraitEntity(String preference) {
		this.preference = preference;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public String getPreference() {
		return preference;
	}

	public void setPreference(String preference) {
		this.preference = preference;
	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	public double getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(double efficiency) {
		this.efficiency = efficiency;
	}

	/**
	 * @return the vice_preference
	 */
	public Map<String, Integer> getVice_preference() {
		return vice_preference;
	}

	/**
	 * @param vice_preference the vice_preference to set
	 */
	public void setVice_preference(Map<String, Integer> vice_preference) {
		this.vice_preference = vice_preference;
	}


}
