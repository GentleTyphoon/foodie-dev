package com.imooc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author wuweifu
 * @Date 2020/4/24 6:08 下午
 * @Version V1.0
 * @Description:
 **/
@SpringBootApplication
/** 扫描 mybatis 通用 mapper 所在的包 */
@MapperScan(basePackages = "com.imooc.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
