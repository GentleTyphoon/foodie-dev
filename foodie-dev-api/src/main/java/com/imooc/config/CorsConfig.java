package com.imooc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @Author wuweifu
 * @Date 2020/5/3 12:26 上午
 * @Version V1.0
 * @Description:
 **/
@Configuration
public class CorsConfig {

    public CorsConfig() {
    }

    @Bean
    public CorsFilter corsFilter() {
        // 1.添加CORS配置信息
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:8080");

        //设置是否发送cookie信息
        corsConfiguration.setAllowCredentials(true);

        //设置允许请求的方式
        corsConfiguration.addAllowedMethod("*");

        //设置允许的请求头
        corsConfiguration.addAllowedHeader("*");

        // 2.为URL添加映射路径
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        // 返回重新定义好的corsSource
        return new CorsFilter(corsConfigurationSource);

    }

}
