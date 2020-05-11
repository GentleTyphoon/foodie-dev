package com.imooc.service;

import com.imooc.pojo.Carousel;
import com.imooc.pojo.UserAddress;

import java.util.List;

/**
 * @Author wuweifu
 * @Date 2020/5/4 1:44 上午
 * @Version V1.0
 * @Description: 用户地址service接口
 **/
public interface AddressService {

    /**
     *
     * @param userId
     * @return
     */
    public List<UserAddress> queryAll(String userId);
}
