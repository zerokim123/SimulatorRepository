package com.simulator.http.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.simulator.common.CommonConst;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new CustomHandlerInterceptor())
                .addPathPatterns(CommonConst.PATH_PATTERN) // 適用対象のパス(パターン)を指定する
                .excludePathPatterns("/static/**"); // 除外するパス(パターン)を指定する
    }
}