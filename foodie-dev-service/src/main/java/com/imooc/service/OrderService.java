package com.imooc.service;

import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.pojo.vo.OrderVO;

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
    public OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     * @param orderId   order的ID
     * @param orderStatus   订单状态枚举
     */
    public void updateOrderStatus(String orderId, Integer orderStatus);

    /***
     * 查询订单状态
     * @param orderId   订单ID
     * @return
     */
    public OrderStatus queryOrderStatusInfo(String orderId);
}
