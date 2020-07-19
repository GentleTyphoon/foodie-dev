package com.imooc.service.center;

import com.imooc.pojo.OrderItems;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评价商品接口
 */
public interface MyCommentsService {

    /**
     * 根据订单ID查询关联的商品
     * @param orderId
     * @return
     */
    public List<OrderItems> queryPendingComment(@Param("orderId") String orderId);


}
