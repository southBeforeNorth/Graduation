package com.backend.config;

import com.backend.util.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedOrigins("*")
                .allowedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePath = new ArrayList<>();
        excludePath.add("/user/login");
        excludePath.add("/user/create");
        excludePath.add("/user/nameList");
        excludePath.add("/user/StringId/{id}");
        excludePath.add("/merchant/login");
        excludePath.add("/merchant/create");
        excludePath.add("/merchant/nameList");
        excludePath.add("/sportGround/page");
        excludePath.add("/sportGround/get/{id}");
        excludePath.add("/dictionary/{dictionaryName}");
        excludePath.add("/dictionary/multiple/{dictionaryNames}");
        excludePath.add("/sportGround/getMerchant/{id}");
        excludePath.add("/orderInfo/getBySportGroundId/{id}");
        excludePath.add("/orderInfo/getCommentList/{id}");
        excludePath.add("/orderInfo//getCommentByPage/{id}");
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}
