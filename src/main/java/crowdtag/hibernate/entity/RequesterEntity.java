package crowdtag.hibernate.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="requesters")
public class RequesterEntity extends BaseModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1886147662007952749L;

	/**发起者姓名*/
	@Column(nullable = false)
	private String name;

	/**发起者密码*/
	@Column(nullable = false)
	private String password = "1234";
	
	@Column(nullable = true)
	private String email;
	
	@Column(nullable = false)
	private String picture = "https://songzi-picture.oss-cn-shenzhen.aliyuncs.com/nopic_400x300.gif";
	
	@Column(nullable = true)
	private String address;
	
	@Column(nullable = false)
	private String company;
	
	@Column(nullable = false)
	private double account = 0;
	
	public RequesterEntity() {}
	public RequesterEntity(String name, String password, String email, String address, String company) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.address = address;
		this.company = company;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}
	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	public double getAccount() {
		return account;
	}
	public void setAccount(double account) {
		this.account = account;
	}
}
