package crowdtag.hibernate.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="gift")
public class GiftEntity extends BaseModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1399107460523384099L;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private double price;
	
	@Column(nullable = false)
	private int amount;
	
	@Column(nullable = false)
	private String picture_path = "https://songzi-picture.oss-cn-shenzhen.aliyuncs.com/nopic_400x300.gif";

	public GiftEntity() {}
	
	public GiftEntity(String name, double price, int amount) {
		this.setName(name);
		this.setPrice(price);
		this.setAmount(amount);
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	/**
	 * @return the picture_path
	 */
	public String getPicture_path() {
		return picture_path;
	}
	/**
	 * @param picture_path the picture_path to set
	 */
	public void setPicture_path(String picture_path) {
		this.picture_path = picture_path;
	}
}
