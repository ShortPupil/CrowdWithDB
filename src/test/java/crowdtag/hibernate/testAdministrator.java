package crowdtag.hibernate;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import crowdtag.hibernate.entity.AdministratorEntity;
import crowdtag.hibernate.repository.AdministratorRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testAdministrator {

    @Autowired
    private AdministratorRepository userRepository;

    //@Test
    public void test1() throws Exception {

        // 创建3条记录
    	AdministratorEntity a = new AdministratorEntity();
    	a.setName("AAA");
    	AdministratorEntity b = new AdministratorEntity();
    	b.setName("BBB");
    	AdministratorEntity c = new AdministratorEntity();
    	c.setName("CCC");
    	a.setPassword("1234");
    	b.setPassword("1234");
    	c.setPassword("1234");
        userRepository.save(a);
        userRepository.save(b);
        userRepository.save(c);

        // 测试findAll, 查询所有记录
        Assert.assertEquals(3, userRepository.findAll().size());


        // 测试删除姓名为AAA的User    
        //userRepository.deleteById((long) 0);

        // 测试findAll, 查询所有记录, 验证上面的删除是否成功
      //  Assert.assertEquals(2, userRepository.findAll().size());
    }
    
    @Test
    public void testFindByName() throws Exception {
    	assertEquals(userRepository.findByName("AAA").get(0).getId().longValue(), (long)1);
    }
}
