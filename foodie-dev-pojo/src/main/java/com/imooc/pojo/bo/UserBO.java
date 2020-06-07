package com.imooc.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author wuweifu
 * @Date 2020/4/25 11:20 下午
 * @Version V1.0
 * @Description: 前端传来的新建用户对象
 **/
@ApiModel(value = "用户对象BO", description = "客户端传入的用户数据封装")
public class UserBO {

    @ApiModelProperty(value = "用户名", name = "username", example = "wwf", required = true)
    private String username;

    @ApiModelProperty(value = " 密码", name = "password", example = "123456", required = true)
    private String password;

    @ApiModelProperty(value = "确认密码", name = "confirmPassword", example = "123456", required = false)
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
