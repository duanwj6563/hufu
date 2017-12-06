package com.sunlands.feo.application.impl;

import com.sunlands.feo.application.UserService;
import com.sunlands.feo.common.AssertUtil;
import com.sunlands.feo.domain.exception.ServerStatus;
import com.sunlands.feo.domain.exception.UserDefinedException;
import com.sunlands.feo.domain.model.user.User;
import com.sunlands.feo.domain.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangchao on 17/11/8.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        AssertUtil.isNotNull(user);
        AssertUtil.isNotNull(user.getName(), "姓名为空");
        AssertUtil.isNotNull(user.getUsername(), "用户名为空");
        //AssertUtil.isNotNull(user.getPassword(),"密码为空");
        AssertUtil.isNotNull(user.getRoles(), "角色为空");

        return userRepository.saveAndFlush(user);
    }

    @Override
    public User queryByUserId(Integer userId) {
        exist(userId);
        return userRepository.findOne(userId);
    }

    @Override
    public List<User> queryUsersByOrgId(Integer orgId) {
        AssertUtil.isNotNull(orgId);
        return userRepository.findByOrganization_id(orgId);
    }

    @Override
    public User getUser() {
        //TODO 获取用户唯一标识
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());
//        User user = userRepository.findOne(2);
        return user;
    }

    public void exist(Integer userId) {
        AssertUtil.isNotNull(userId);
        if (!userRepository.exists(userId))
            throw new UserDefinedException(ServerStatus.NO_EXIST);
    }
}
