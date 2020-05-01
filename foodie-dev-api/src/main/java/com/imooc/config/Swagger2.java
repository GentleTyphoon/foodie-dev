package com.imooc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author wuweifu
 * @Date 2020/4/26 1:55 上午
 * @Version V1.0
 * @Description:
 **/
@Configuration
@EnableSwagger2
public class Swagger2 {


    //  http://localhost:8088/swagger-ui.html   原始页面
    //  http://localhost:8088/doc.html

    //配置swagger2核心配置 docket
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)  //指定API的类型为swagger2
                .apiInfo(apiInfo())                              //用于定义API文档汇总信息
                .select().apis(RequestHandlerSelectors.basePackage("com.imooc.controller")) //指定controller包
                .paths(PathSelectors.any()) //所有controller
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("天天吃货电商平台API")             //文档页标题
                .contact(new Contact("imooc", "https://imooc.com", "abc@imooc.com"))
                .description("天天吃货API文档")          //作者信息
                .version("1.0.0")                      //文档版本号
                .termsOfServiceUrl("https://www.imooc.com") //网站地址
                .build();

    }

}
