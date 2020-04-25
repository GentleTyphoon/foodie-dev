package com.imooc.service;


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

}
