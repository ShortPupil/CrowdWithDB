package testHibernate;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
 
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
 
import static org.mockito.Mockito.when;
 
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import crowdtag.hibernate.entity.RequesterEntity;
import crowdtag.hibernate.service.RequesterServiceImpl;
 
public class testRequesterService {
 
    @Mock
    RequesterEntity dao;
     
    @InjectMocks
    RequesterServiceImpl requesterService;
     
    @Spy
    List<RequesterEntity> requesters = new ArrayList<RequesterEntity>();
     
    @BeforeClass
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        requesters = getRequesterList();
    }
 
    @Test
    public void findById(){
    	RequesterEntity emp = requesters.get(0);
        when(dao.findById(anyInt())).thenReturn(emp);
        Assert.assertEquals(requesterService.findById(emp.getId()),emp);
    }
 
    @Test
    public void saveEmployee(){
        doNothing().when(dao).save(any(RequesterEntity.class));
        requesterService.save(any(RequesterEntity.class));
        verify(dao, atLeastOnce()).save(any(RequesterEntity.class));
    }
     
    @Test
    public void updateEmployee(){
    	RequesterEntity emp = requesters.get(0);
        when(dao.findById(anyInt())).thenReturn(emp);
        requesterService.save(emp);
        verify(dao, atLeastOnce()).findById(anyInt());
    }
 
    @Test
    public void deleteEmployeeBySsn(){
        doNothing().when(dao).deleteEmployeeBySsn(anyString());
        requesterService.deleteById(anyString());
        verify(dao, atLeastOnce()).deleteEmployeeBySsn(anyString());
    }
     
    @Test
    public void findAllEmployees(){
        when(dao.findAllEmployees()).thenReturn(requesters);
        Assert.assertEquals(requesterService.findAllEmployees(), employees);
    }
     
    @Test
    public void findEmployeeBySsn(){
    	RequesterEntity emp = requesters.get(0);
        when(dao.findEmployeeBySsn(anyString())).thenReturn(emp);
        Assert.assertEquals(requesterService.findEmployeeBySsn(anyString()), emp);
    }
     
     
    public List<RequesterEntity> getRequesterList(){
    	RequesterEntity e1 = new RequesterEntity();
        e1.setId(1);
        e1.setName("Axel");
        e1.setPassword("1234");
         
        RequesterEntity e2 = new RequesterEntity();
        e2.setId(2);
        e2.setName("Jeremy");
        e2.setPassword("1234");
         
        requesters.add(e1);
        requesters.add(e2);
        return requesters;
    }
     
}
