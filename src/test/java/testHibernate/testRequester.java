package testHibernate;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import crowdtag.hibernate.entity.RequesterEntity;
import crowdtag.hibernate.service.HibernateService;
import crowdtag.hibernate.service.RequesterServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class) 
public class testRequester {
	@InjectMocks  
	public HibernateService<RequesterEntity> rs = new RequesterServiceImpl();
	
	@Before  
    public void setUp() {  
        MockitoAnnotations.initMocks(this);    
    }  
  
	@Test
	public void testFindAll() {
		assertEquals(rs.findAll().size(),0);
	} 
}