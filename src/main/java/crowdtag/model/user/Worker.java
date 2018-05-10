package crowdtag.model.user;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="worker")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class Worker {

	/**工人id*/
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	/**工人名称*/
	@Column(nullable = false)
	private String workername;

	/**工人密码*/
	@Column(nullable = false)
	private String password;

	/**工人积分*/
	@Column(nullable = false)
	private int point;

	/**工人标注到的位置*/
	@Column(nullable = false)
	private int position;

	/**关联的id+完成数  example: "1,2"*/
	@Column(nullable = false)
	private String requestlist;
	
	public Worker() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}


	public void setRequestList(ArrayList<String> requestList) {
		String separator = "/";
		for(int i=0; i<requestList.size() ; i++) {
			this.requestlist = this.requestlist + requestList.get(i) + separator;
		}
	}

	public String getRequestlist() {
		// TODO Auto-generated method stub
		return this.getRequestlist();
	}
}
