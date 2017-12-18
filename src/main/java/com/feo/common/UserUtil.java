package com.feo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserUtil {
    private static Logger logger = LoggerFactory.getLogger(UserUtil.class);

    /**
     * 获取当前用户名
     *
     * @return
     */
    public static String getUserNanme() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        logger.info("当前登录用户人是;{}", username);
        //username = "111";
        return username;
    }
}
