//package org.csu.petstore.controller;
//
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//
//@Configuration // 标记为配置类
//@WebFilter(filterName = "CORSFilter")
//public class CORSFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000"); // 允许跨域请求
//        response.setHeader("Access-Control-Allow-Credentials", "true"); // 允许跨域请求携带cookie
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT"); // 允许跨域请求的方法
//        response.setHeader("Access-Control-Max-Age", "3600"); // 预检请求的缓存时间
//        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization"); // 允许跨域请求的头信息
//
//        // 如果是预检请求，直接返回，不继续执行后续过滤器链
//        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
//            response.setStatus(HttpServletResponse.SC_OK);
//            return;
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
//    }
//
//    @Override
//    public void destroy() {
//        Filter.super.destroy();
//    }
//}
