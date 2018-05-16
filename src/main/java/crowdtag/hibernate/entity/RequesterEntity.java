package crowdtag.hibernate.entity;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="requesters")
public class RequesterEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1886147662007952749L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**发起者姓名*/
	@Column(nullable = false)
	private String name;

	/**发起者密码*/
	@Column(nullable = false)
	private String password;
	
	private String email;
	
	private Blob picture;
	
	private String address;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Blob getPicture() {
		return picture;
	}

	public void setPicture(Blob picture) {
		this.picture = picture;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
