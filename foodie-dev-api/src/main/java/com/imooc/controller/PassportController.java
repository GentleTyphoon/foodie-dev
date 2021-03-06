package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Author wuweifu
 * @Date 2020/4/25 8:36 下午
 * @Version V1.0
 * @Description: 用户相关接口
 **/
@Api(value = "注册登陆", tags = {"用于注册登陆的相关接口"})
@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户名是否存在", notes = "用户是否存在,", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public IMOOCJSONResult usernameIsExist(@RequestParam String username) {

        /** 判断用户名非空 */
        if (StringUtils.isBlank(username)) {
            return IMOOCJSONResult.errorMsg("用户名不能为空");
        }

        /** 查找用户名是否存在 */
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }

        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public IMOOCJSONResult regist(@RequestBody UserBO userBo,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

        String username = userBo.getUsername();
        String password = userBo.getPassword();
        String confirmPassword = userBo.getConfirmPassword();

        /** 判断用户名和密码非空 */
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }

        /** 查询用户名是否已经存在 */
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }

        /** 判断密码长度是否大于等于6位 */
        if (password.length() < 6) {
            return IMOOCJSONResult.errorMsg("密码长度不能小于6位");
        }

        /** 判断2次密码是否一致 */
        if (!password.equals(confirmPassword)) {
            return IMOOCJSONResult.errorMsg("两次密码输入不一致");
        }

        /** 注册 */
        Users user = userService.createUser(userBo);

        CookieUtils.setCookie(request, response,
                "user", JsonUtils.objectToJson(user), true );

        // TODO 生成永华token，存入redis会话
        // TODO 同步购物车数据

        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public IMOOCJSONResult login(@RequestBody UserBO userBo,
                                 HttpServletRequest request,
                                 HttpServletResponse response
    ) throws Exception {

        String username = userBo.getUsername();
        String password = userBo.getPassword();

        // 用户名或密码不能为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }

        // 实现登陆
        Users users = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));

        if (users == null) {
            return IMOOCJSONResult.errorMsg("用户名或密码不正确");
        }

        users = setNullProperty(users);

        CookieUtils.setCookie(request, response,
                "user", JsonUtils.objectToJson(users), true );

        // TODO 生成永华token，存入redis会话
        // TODO 同步购物车数据

        return IMOOCJSONResult.ok(users);
    }


    private Users setNullProperty(Users users) {
        users.setPassword(null);
        users.setMobile(null);
        users.setEmail(null);
        users.setCreatedTime(null);
        users.setUpdatedTime(null);
        users.setBirthday(null);
        return users;
    }

    @ApiOperation(value = "用户退出登陆", notes = "用户退出登陆", httpMethod = "POST")
    @PostMapping("/logout")
    public IMOOCJSONResult logout(@RequestParam String userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

        //  清楚用户cookie
        CookieUtils.deleteCookie(request, response, "user");

        //TODO 用户退出登陆，需要清空购物车
        //TODO 分布式会话中需要清除用户数据

        return IMOOCJSONResult.ok();
    }

}
