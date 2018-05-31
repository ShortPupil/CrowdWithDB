package crowdtag.hibernate.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="administrators")
public class AdministratorEntity extends BaseModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5417697667850312834L;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String password = "1234";

	public AdministratorEntity(String n, String pw) {
		this.name = n;
		this.password = pw;
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
}
