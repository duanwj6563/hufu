package com.sunlands.feo.port.rest.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class Filter001 implements Filter {
    private final Logger logger = LoggerFactory.getLogger(Filter001.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse rep, FilterChain chain) throws IOException, ServletException {
        // AttributePrincipal principal =(AttributePrincipal)request.getUserPrincipal();
        HttpServletResponse response = (HttpServletResponse) rep;
       /* Cookie c1 = new Cookie("token", "wwwwwwww");
        response.addCookie(c1);
        //response.sendRedirect("http://172.16.125.81:8989/#/");
        response.sendRedirect("/file/goUpload");*/
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
