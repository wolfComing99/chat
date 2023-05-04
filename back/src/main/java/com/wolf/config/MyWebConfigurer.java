package com.wolf.config;

import com.wolf.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootConfiguration
public class MyWebConfigurer implements WebMvcConfigurer {
    @Value("${file.upload.dir}")
    public String uploadDir;

    @Bean
    public LoginInterceptor getLoginIntercepter() {
        return new LoginInterceptor();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //所有请求都允许跨域，使用这种配置方法就不能在 interceptor 中再配置 header 了
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowedHeaders("*")
                .maxAge(3600);
    }
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(getLoginIntercepter())
//                .addPathPatterns("/**")
//                //.excludePathPatterns("/index.html")
//                .excludePathPatterns("/loginByPassword")
//                .excludePathPatterns("/sendCode")
//                .excludePathPatterns("/loginByVerification")
//                .excludePathPatterns("/register");
//                //.excludePathPatterns("/logout");
//    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 头像映射
        registry.addResourceHandler("/upload/avatar/**")
                .addResourceLocations("file:///" + uploadDir + "/avatar/");
    }

}


