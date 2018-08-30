package com.simulator.http.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;


@Component
public class LoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        WrappedHttpServletRequest requestWrapper = null;
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest =
                    (HttpServletRequest) servletRequest;
            if ("POST".equals(
                    httpServletRequest.getMethod().toUpperCase())) {
                requestWrapper = new WrappedHttpServletRequest(
                        httpServletRequest);
                MDC.put("postData", requestWrapper.getRequestParams());
            }
        }

        if (requestWrapper == null) {
            filterChain.doFilter(
                    servletRequest, servletResponse);
        } else {
            filterChain.doFilter(
                    requestWrapper, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}