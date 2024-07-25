package com.bdp.innovation.potentialRiskService.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ResponseHeaderFilter implements Filter {
 
    @Override
    public void doFilter(ServletRequest request,
                        ServletResponse response,
                        FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
       // log.info("Added Headers :: 	ResponseHeaderFilter");
        res.reset();
        
        res.addHeader("Access-Control-Allow-Origin",req.getHeader(HttpHeaders.ORIGIN));
        res.addHeader("X-Content-Type-Options", "nosniff");
        res.addHeader("Cache-Control", "max-age=604800,must-revalidate");
        res.addHeader("X-XSS-Protection", "1; mode=block");
        res.addHeader("X-Frame-Options", "DENY");
        res.addHeader("Content-Security-Policy", "default-src 'self'");
        
        res.addHeader("Strict-Transport-Security", "max-age=63072000; includeSubDomains; preload");
        res.addHeader("X-Content-Type-Options", "nosniff");
        res.addHeader("X-XSS-Protection", "1; mode=block");
        
        chain.doFilter(req, res);
    }
}