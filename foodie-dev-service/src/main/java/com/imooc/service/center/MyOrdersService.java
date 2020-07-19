package com.imooc.service.center;


import com.imooc.utils.PagedGridResult;

/**
 * @author wuweifu
 *  用户中心-订单管理接口
 */
public interface MyOrdersService {

    /**
     * 查询我的订单列表
     * @param userId    用户ID
     * @param orderStatus   订单状态
     * @param page  页码
     * @param pageSize  每页显示条数
     * @return
     */
    public PagedGridResult queryMyOrders(String userId,
                                         Integer orderStatus,
                                         Integer page,
                                         Integer pageSize
    );


    /**
     * @param orderId
     * @Description: 订单状态 --> 商家发货
     */
    public void updateDeliverOrderStatus(String orderId);


}
