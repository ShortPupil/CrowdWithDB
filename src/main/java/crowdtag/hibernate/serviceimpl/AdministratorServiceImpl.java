package crowdtag.hibernate.serviceimpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import crowdtag.hibernate.entity.AdministratorEntity;
import crowdtag.hibernate.repository.AdministratorRepository;
import crowdtag.hibernate.service.AdministratorService;

public class AdministratorServiceImpl implements AdministratorService{
	
	@Autowired
	private AdministratorRepository administratorRepository;
	
	@Override
	public List<AdministratorEntity> findAll() {
		// TODO Auto-generated method stub
		return administratorRepository.findAll();
	}

	@Override
	public Optional<AdministratorEntity> findById(int id) {
		// TODO Auto-generated method stub
		return administratorRepository.findById(id);
	}

	@Override
	@Transactional
	public int save(AdministratorEntity entity) {
		// TODO Auto-generated method stub	
		entity = administratorRepository.saveAndFlush(entity);
		return entity.getId();
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		administratorRepository.deleteById(id);;
	}

	@Override
	public void delete(AdministratorEntity entity) {
		// TODO Auto-generated method stub
		administratorRepository.delete(entity);
	}

}
