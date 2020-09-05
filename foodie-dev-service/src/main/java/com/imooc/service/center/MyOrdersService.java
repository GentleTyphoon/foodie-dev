package com.imooc.service.center;


import com.imooc.pojo.Orders;
import com.imooc.pojo.vo.OrderStatusCountsVO;
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
     * 订单状态 --> 商家发货
     * @param orderId   订单ID
     */
    public void updateDeliverOrderStatus(String orderId);

    /**
     * 查询我的订单
     * @param userId    用户ID
     * @param orderId   订单ID
     * @return
     */
    public Orders queryMyOrder(String userId, String orderId);

    /**
     *  更新订单状态 -> 确认收货
     * @param orderId   订单ID
     * @return
     */
    public boolean updateReceiveOrderStatus(String orderId);

    /**
     * 删除订单 (逻辑删除)
     * @param orderId
     * @param userId
     * @return
     */
    public boolean deleteOrder(String orderId, String userId);

    /**
     * 查询用户订单数
     * @param userId
     * @return
     */
    public OrderStatusCountsVO getOrderStatusCounts(String userId);

    /**
     * 分页查询 订单动向
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult getMyOrderTrend(String userId,
                                         Integer page,
                                         Integer pageSize
    );

}
