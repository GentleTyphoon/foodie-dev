package com.imooc.pojo.bo;

/**
 * @Author wuweifu
 * @Date 2020/4/25 11:20 下午
 * @Version V1.0
 * @Description: 前端传来的新建用户对象
 **/
public class UserBo {

    private String username;
    private String password;
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
