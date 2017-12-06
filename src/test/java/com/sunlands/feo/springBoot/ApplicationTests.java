package com.sunlands.feo.springBoot;

import com.sunlands.feo.common.JwtTokenUtil;
import com.sunlands.feo.domain.model.user.User;
import com.sunlands.feo.domain.repository.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.AntPathMatcher;

/**
 * @author duanwj
 * 测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void contextLoads() throws Exception {
        //mockMvc.perform(MockMvcRequestBuilders.get("/users")
        //        .accept(MediaType.APPLICATION_JSON_UTF8))
        //        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void hello() {
        User u = userRepository.findOne(1);
        System.out.println(u.toString());
    }

    @Test
    public void hello1() {
       /* List<Object[]> list= userRoleRepository.findAllById(2);*/
         /*System.out.println(JSON.toJSONString(list));*/
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        System.out.println("------------------------------------------");
        System.out.println("------------------------------------------");
        boolean f = antPathMatcher.match("/user", "/user");
        System.out.println("是否匹配" + f);
    }

}
