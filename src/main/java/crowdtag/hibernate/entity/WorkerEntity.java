package crowdtag.hibernate.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="workers")
public class WorkerEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8455376381086531402L;

	/**工人id*/
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**工人名称*/
	@Column(nullable = false)
	private String workername;

	/**工人密码*/
	@Column(nullable = false)
	private String password;

	/**工人积分*/
	@Column(nullable = false)
	private int point;
	
	public WorkerEntity() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
}
