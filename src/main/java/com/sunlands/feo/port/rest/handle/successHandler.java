/**
 *
 */
package com.sunlands.feo.port.rest.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunlands.feo.domain.model.user.User;
import com.sunlands.feo.common.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author duanwj
 */
@Component("authenticationSuccessHandler")
public class successHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        logger.info("登录成功");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = new User();
        user.setUsername(userDetails.getUsername());
        String token = jwtTokenUtil.generateToken(user);
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        response.getWriter().write(objectMapper.writeValueAsString(map));
    }
}