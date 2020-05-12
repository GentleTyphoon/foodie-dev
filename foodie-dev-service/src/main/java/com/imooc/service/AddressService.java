package com.imooc.service;

import com.imooc.pojo.Carousel;
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

}
