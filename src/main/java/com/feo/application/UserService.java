package com.feo.application;

import com.feo.domain.model.user.User;

import java.util.List;

/**
 * Created by yangchao on 17/11/8.
 */
public interface UserService {

    /**
     * @param user
     * @return
     */
    User createUser(User user);

    /**
     * @param userId
     * @return
     */
    User queryByUserId(Integer userId);

    /**
     * @param orgId
     * @return
     */
    List<User> queryUsersByOrgId(Integer orgId);

    /**
     * 获取当前登录用户
     *
     * @return
     */
    User getUser();
}
