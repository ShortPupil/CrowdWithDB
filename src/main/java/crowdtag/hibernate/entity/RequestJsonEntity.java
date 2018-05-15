package crowdtag.hibernate.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSON;

@Entity
@Table(name="request_json")
public class RequestJsonEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private JSON request;

	public JSON getRequest() {
		return request;
	}

	public void setRequest(JSON request) {
		this.request = request;
	}
}
