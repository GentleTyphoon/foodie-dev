package com.imooc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author wuweifu
 * @Date 2020/4/24 6:21 下午
 * @Version V1.0
 * @Description:
 **/
@RestController
public class HelloController {

    @GetMapping("/hello")
    public Object hello() {
        return "Hello world !";
    } 
}
