package com.nhom2.asmsof3021.config;

import com.nhom2.asmsof3021.interceptor.GlobalInterceptor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
    private final GlobalInterceptor logger;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logger).addPathPatterns("/**")
                .excludePathPatterns("/images/**", "/assets/**", "/css/**","/icons/**","/icon/**","/js/**")
        ;
    }
}
