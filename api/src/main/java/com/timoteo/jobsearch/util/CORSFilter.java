package com.timoteo.jobsearch.util;

import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CORSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", getOrigin(servletRequest));
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Origin,Content-Type,Accept,Authorization,access_token");
        response.setHeader("Access-Control-Expose-Headers", "Location, Authorization");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getOrigin(ServletRequest servletRequest){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String origin = request.getHeader("origin");
        return origin != null ? origin : "*";
    }

}
