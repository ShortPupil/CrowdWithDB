package crowdtag.hibernate.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="workers")
public class WorkerEntity extends BaseModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8455376381086531402L;

	/**工人名称*/
	@Column(nullable = false)
	private String workername;

	/**工人密码*/
	@Column(nullable = false)
	private String password = "1234";

	/**工人积分*/
	@Column(nullable = false)
	private int point = 0;
	
	/**工人排名*/
	@Column(nullable = false)
	private int rank = 0;
	
	@Column(nullable = false)
	private String picture = "https://songzi-picture.oss-cn-shenzhen.aliyuncs.com/picture/p%284%29.jpg";
	
	public WorkerEntity() {}
	
	public WorkerEntity(String name, String password) {
		// TODO Auto-generated constructor stub
		this.workername = name;
		this.password = password;
		this.point = 0;
		this.rank = 0;
	}

	public String getWorkername() {
		return workername;
	}

	public void setWorkername(String workername) {
		this.workername = workername;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getRequestlist() {
		// TODO Auto-generated method stub
		return this.getRequestlist();
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * @return the picture
	 */
	public String getPicture() {
		return picture;
	}

	/**
	 * @param picture the picture to set
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}
}
