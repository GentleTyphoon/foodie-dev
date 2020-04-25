package com.imooc.service;


import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBo;

/**
 * @author wuweifu
 * 用户接口
 */
public interface UserService {

    /**
     * 查询用户名是否存在
     * @param username  用户名称
     * @return  boolean
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * 创建用户
     * @param userBo 前端传来的UserBo对象
     * @return
     */
    public Users createUser(UserBo userBo);
}
