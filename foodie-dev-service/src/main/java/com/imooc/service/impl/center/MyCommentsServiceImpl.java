package com.imooc.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.ItemsCommentsMapperCustom;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.pojo.OrderItems;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.Orders;
import com.imooc.pojo.bo.center.OrderItemsCommentBO;
import com.imooc.pojo.vo.MyCommentVO;
import com.imooc.service.center.MyCommentsService;
import com.imooc.utils.PagedGridResult;
import org.apache.catalina.User;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author wuweifu
 * @Date 2020/7/19 9:36 下午
 * @Version V1.0
 * @Description:
 **/
@Service
public class MyCommentsServiceImpl extends BaseService implements MyCommentsService {

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private ItemsCommentsMapperCustom itemsCommentsMapperCustom;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {

        OrderItems orderItems = new OrderItems();
        orderItems.setOrderId(orderId);

        return orderItemsMapper.select(orderItems);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComment(String orderId, String userId, List<OrderItemsCommentBO> commentList) {

        /** 1.保存评价 items_comments */
        for (OrderItemsCommentBO orderItemsCommentBO : commentList) {
            orderItemsCommentBO.setCommentId(sid.nextShort());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", commentList);

        itemsCommentsMapperCustom.saveComment(map);

        /** 2.修改订单表改成已评价 orders -> is_comment */
        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setIsComment(YesOrNo.YES.type);
        ordersMapper.updateByPrimaryKeySelective(orders);

        /** 3.修改订单状态表的评价时间 orders_status -> comment_time */
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        PageHelper.startPage(page, pageSize);

        List<MyCommentVO> list = itemsCommentsMapperCustom.queryMyComments(map);

        PagedGridResult pagedGridResult = setterPagedGrid(list, page);

        return pagedGridResult;
    }

}
