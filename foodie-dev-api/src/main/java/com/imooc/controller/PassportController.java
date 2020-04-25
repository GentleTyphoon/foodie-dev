package com.imooc.controller;

import com.imooc.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author wuweifu
 * @Date 2020/4/25 8:36 下午
 * @Version V1.0
 * @Description: 用户相关接口
 **/
@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @GetMapping("/usernameIsExist")
    public HttpStatus usernameIsExist(@RequestParam String username) {

        /** 判断用户名非空 */
        if (StringUtils.isBlank(username)) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        /** 查找用户名是否存在 */
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return HttpStatus.OK;
    }


}
