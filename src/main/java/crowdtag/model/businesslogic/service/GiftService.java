package crowdtag.model.businesslogic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crowdtag.hibernate.entity.GiftEntity;
import crowdtag.hibernate.repository.GiftRepository;

@Service
public class GiftService {

	@Autowired
	GiftRepository giftrepository;
	
	public List<GiftEntity> findAll() {
		// TODO Auto-generated method stub
		return giftrepository.findAll();
	}

	
}
