package crowdtag.model.businesslogic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crowdtag.hibernate.entity.AdministratorEntity;
import crowdtag.hibernate.repository.AdministratorRepository;

@Service
public class Administrator {
	private AdministratorEntity administratorEntity;
	public void setAdministratorEntity(AdministratorEntity administratorEntity) {
	    this.administratorEntity = administratorEntity;
	}
	public AdministratorEntity getAdministratorEntity() {
	    return administratorEntity;
	}
	@Autowired
	AdministratorRepository ar;
	
	
Administrator getAdministratorById (long id) {
	Optional<AdministratorEntity> ae=ar.findById(id);
	Administrator a =new Administrator();
	if(ae.isPresent()) {
		a.administratorEntity=ae.get();
		return a;
	}
	else {
		return null;
	}

	
}

Administrator getAdministratorByName (String name) {
	List<AdministratorEntity> ae=ar.findByName(name);
	Administrator a =new Administrator();
    for(int i=0 ; i<ae.size() ; i++) {
    	if(ae.get(i)!=null) a.administratorEntity=ae.get(i);
		return a;
	}
	return null;
}
Administrator register (Administrator administrator) {
	AdministratorEntity a=ar.saveAndFlush(administrator.administratorEntity);
	administrator.administratorEntity.setId(a.getId());
	return administrator;
}

Administrator login (long id,String password) {
	Optional<AdministratorEntity> ae=ar.findById(id);
	Administrator a =new Administrator();
	if(ae.isPresent()) {
		if(ae.get().getPassword().equals(password)) {			
		a.administratorEntity=ae.get();
		return a;
		}
		else {
			return null;
		}
		
		
	}
	else {
		return null;
	}
}

}
