package com.imooc.service;

import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;

import java.util.List;

/**
 * @Author wuweifu
 * @Date 2020/5/4 1:44 上午
 * @Version V1.0
 * @Description: 用户地址service接口
 **/
public interface AddressService {

    /**
     * 根据用户ID查询用户收货地址列表
     * @param userId 用户ID
     * @return
     */
    public List<UserAddress> queryAll(String userId);

    /**
     * 新增收货地址
     * @param addressBO 用户地址BO
     */
    public void addNewUserAddress(AddressBO addressBO);

    /**
     * 修改收货地址
     * @param addressBO
     */
    public void updateUserAddress(AddressBO addressBO);

    /**
     * 根据用户ID和地址ID删除对应的地址信息
     * @param userId    用户ID
     * @param addressId 地址主键
     */
    public void deleteUserAddress(String userId, String addressId);

    /**
     * 设置默认收货地址
     * @param userId    用户ID
     * @param addressId 地址主键
     */
    public void updateUserAddress2Default(String userId, String addressId);

    /**
     * 查询用户地址对象
     * @param userId    用户ID
     * @param addressId 地址ID
     * @return
     */
    public UserAddress queryUserAddress(String userId, String addressId);
}
