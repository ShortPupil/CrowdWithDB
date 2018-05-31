package crowdtag.hibernate.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="worker_portrait")
public class WorkerPortraitEntity extends BaseModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7552622348977882235L;

	/**工人id*/
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**工人准确度*/
	@Column(nullable = false)
	private double accuracy;

	/**工人偏好*/
	@Column(nullable = false)
	private String preference;
	
	/**工人信誉分*/
	@Column(nullable = false)
	private double credit;

	/**工人效率*/
	@Column(nullable = false)
	private double efficiency;
	
	private double point;
	

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
	 * @return the point
	 */
	public double getPoint() {
		return point;
	}

	/**
	 * @param point the point to set
	 */
	public void setPoint(double point) {
		this.point = point;
	}
}
