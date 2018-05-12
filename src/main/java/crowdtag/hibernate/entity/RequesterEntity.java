package crowdtag.hibernate.entity;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="requester")
public class RequesterEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -432980000882717796L;

	/**发起者id 000001*/
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	/**发起者姓名*/
	@Column(nullable = false)
	private String name;

	/**发起者密码*/
	@Column(nullable = false)
	private String password;

	/**发起者发起的request的id*/
	@Column(nullable = false)
	private String requestlist;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRequestlist() {
		return requestlist;
	}

	public void setRequestlist(ArrayList<String> requestList) {
		String separator = "/";
		for(int i=0; i<requestList.size() ; i++) {
			this.requestlist = this.requestlist + requestList.get(i) + separator;
		}
	}
}
