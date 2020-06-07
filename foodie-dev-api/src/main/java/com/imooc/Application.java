package com.imooc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
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
/** 扫描所有包以及相关组件包 */
@ComponentScan(basePackages = {"org.n3r.idworker", "com.imooc"})
/** 开启定时任务 */
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
