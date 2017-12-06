/**
 *
 */
package com.sunlands.feo.port.rest.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunlands.feo.domain.exception.ServerStatus;
import com.sunlands.feo.domain.exception.ExceptionReturn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author duanwj
 */
@Component("authenctiationFailureHandler")
public class failureHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        response.setContentType("application/json;charset=UTF-8");
        logger.info("登录失败,host:{}",request.getRemoteHost());

        ExceptionReturn  er= new ExceptionReturn(ServerStatus.UNAUTHORIZED);
        er.setPath(request.getServletPath());
        response.getWriter().write(objectMapper.writeValueAsString(er));

    }

}
