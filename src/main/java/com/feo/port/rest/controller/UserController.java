/**
 * @describe
 * @author duanwj
 * @since 2017年9月29日 上午11:09:58
 */
package com.feo.port.rest.controller;

import com.feo.domain.model.user.User;
import com.feo.domain.model.user.UserSimpleView;
import com.feo.domain.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;

    /**
     * 获取当前角色信息
     *
     * @param user
     * @return
     */
    @GetMapping(value = "/me")
    public User getCurrentUser(@AuthenticationPrincipal UserDetails user) {
        User users = userRepository.findByUsername(user.getUsername());
        return users;
    }

    /**
     * 查看所有用户信息
     *
     * @return
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")//此方法只允许 ROLE_ADMIN 角色访问
    public Page<User> usersList() {
        int page = 0, size = 2;
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, size, sort);
        return userRepository.findAll(pageable);
    }

    /**
     * 查看单一用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public UserSimpleView getUserFindOne(@PathVariable("id") Integer id) {

        /*使用BeanUtils实现bean属性copy*/
        User one = userRepository.findOne(id);
        UserSimpleView userSimpleView = new UserSimpleView();
        BeanUtils.copyProperties(one, userSimpleView);

        return userSimpleView;

    }

    @GetMapping("/name")
    public User getUserByUserName(@RequestParam("userName") String userName) {
        //todo 方法实现
        return userRepository.findByUsername(userName);
    }

    /**
     * 更新一个用户信息
     *
     * @param id
     * @param name
     * @return
     */
    @PutMapping("/{id}")
    public User userUpdate(@PathVariable("id") Integer id, @RequestParam("name") String name) {
        User user = new User();
        user.setId(id);
        user.setUsername(name);
        return userRepository.save(user);
    }

    /**
     * 删除一个用户
     */
    @DeleteMapping("/{id}")
    public void userDelete(@PathVariable("id") Integer id) {
        userRepository.delete(id);
    }
}
