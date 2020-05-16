package com.imooc.service;

import com.imooc.pojo.bo.SubmitOrderBO;

/**
 * @Author wuweifu
 * @Date 2020/5/14 10:56 下午
 * @Version V1.0
 * @Description:
 **/
public interface OrderService {

    /**
     *  创建订单
     * @param submitOrderBO
     */
    public String createOrder(SubmitOrderBO submitOrderBO);
}
