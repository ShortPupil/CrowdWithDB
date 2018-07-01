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
	
	/**权限，0为最高，1、2、3..渐小*/
	@Column(nullable = false)
	private int authority;

	public AdministratorEntity() {}
	
	public AdministratorEntity(String n, String pw, int authority) {
		this.name = n;
		this.password = pw;
		this.setAuthority(authority);
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

	public int getAuthority() {
		return authority;
	}

	public void setAuthority(int authority) {
		this.authority = authority;
	}
}
