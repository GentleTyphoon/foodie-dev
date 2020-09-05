package com.imooc.service.center;

import com.imooc.pojo.OrderItems;
import com.imooc.pojo.bo.center.OrderItemsCommentBO;
import com.imooc.utils.PagedGridResult;
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

    /**
     * 保存用户的评论
     * @param orderId
     * @param userId
     * @param commentList
     */
    public void saveComment(String orderId, String userId, List<OrderItemsCommentBO> commentList);

    /**
     * 分页查询我的评价
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize);
}
