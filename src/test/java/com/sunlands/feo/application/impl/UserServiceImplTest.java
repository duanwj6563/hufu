package com.sunlands.feo.application.impl;

import com.sunlands.feo.application.UserService;
import com.sunlands.feo.domain.model.orgInfo.Organization;
import com.sunlands.feo.domain.model.user.Role;
import com.sunlands.feo.domain.model.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yangchao on 17/11/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void testCreateUser() throws Exception {
        User  u = new User();
        u.setName("u01");
        u.setUsername("user01");
        u.setPassword("1234567");
        Set<Role> s = new HashSet<>();
        Role r = new Role();
        r.setName("USER");
        s.add(r);
        u.setRoles(s);
        Organization o = new Organization();
        o.setName("");
        userService.createUser(u);
    }

    @Test
    public void testQueryByUserId() throws Exception {

    }

    @Test
    public void testQueryUsersByOrgId() throws Exception {

    }

    @Test
    public void testExist() throws Exception {

    }
}