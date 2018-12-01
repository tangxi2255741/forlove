package com.txr.forlove;

import com.txr.forlove.domain.User;
import com.txr.forlove.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ForloveApplicationTests {
    @Resource private UserService userService;

    @Test
    public void saveData() {
        User user = new User();
        user.setName("tangxi");
        user.setPassword("nishizhu.0");
        Date now = new Date();
        user.setCreated(now);
        user.setModified(now);
        user.setStatus(1);
        userService.save(user);
    }

    @Test
    public void testQuery(){
        User user = userService.findByName("tangxi");
        System.out.println(user.toString());
        List<User> userList = userService.findAll();
        System.out.println(userList.toString());
    }

}
