package com.feo.port.rest.filter;

import com.feo.common.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;//请求头
    @Value("${tiger.homeUrl}")
    private String homeUrl;//前端主页

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Object origin = request.getRemoteHost() + ":" + request.getRemotePort();
        logger.info("Host : {}", origin);
        String token = request.getHeader(tokenHeader);
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            response.setStatus(204); //HttpStatus.SC_NO_CONTENT = 204
            response.setHeader("Access-Control-Expose-Headers", "Authorization");//允许浏览器端自定义响应头
            response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type,x-requested-with,Authorization");
            response.setHeader("Content-Type", "application/json;charset=utf-8");
            response.setHeader("Access-Control-Max-Age", "3600");
        } else {
            logger.info("jwt权限控制");
            if (token != null) {
                String username = jwtTokenUtil.getUsernameFromToken(token);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    logger.info("Current username : " + username);
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    if (jwtTokenUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                                request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        //toekn过期时间处理
                        String refreshedToken = jwtTokenUtil.refreshToken(token);
                        if (refreshedToken != null) {
                            logger.info("RefreshedToken : {}", refreshedToken);
                            response.setHeader(tokenHeader, refreshedToken);
                        }
                    }
                }
            }
            //统一登录获取用户信息
            // AttributePrincipal principal =(AttributePrincipal)request.getUserPrincipal();
          /*  if (true) {
                User user = new User();
                user.setUsername("1");
                String createToken = jwtTokenUtil.generateToken(user);
                Cookie c1 = new Cookie("token", createToken);
                response.addCookie(c1);
                response.sendRedirect(homeUrl);
                return;
            }*/
            chain.doFilter(request, response);
        }
    }
}
