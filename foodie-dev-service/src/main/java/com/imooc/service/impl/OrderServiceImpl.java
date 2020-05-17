package com.imooc.service.impl;

import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.pojo.*;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.pojo.vo.MerchantOrdersVO;
import com.imooc.pojo.vo.OrderVO;
import com.imooc.service.ItemService;
import com.imooc.service.OrderService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author wuweifu
 * @Date 2020/5/14 10:56 下午
 * @Version V1.0
 * @Description:
 **/
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private Sid sid;

    @Autowired
    private AddressServiceImpl addressService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderVO createOrder(SubmitOrderBO submitOrderBO) {

        String userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();

        /**  邮费0 */
        Integer postAmount = 0;

        /** 1.新订单数据保存 */
        Orders orders = new Orders();
        String orderId = Sid.next();
        orders.setId(orderId);
        orders.setUserId(userId);

        UserAddress userAddress = addressService.queryUserAddress(userId, addressId);

        orders.setReceiverName(userAddress.getReceiver());
        orders.setReceiverMobile(userAddress.getMobile());
        orders.setReceiverAddress(userAddress.getProvince() +
                " " + userAddress.getCity() +
                " " + userAddress.getDetail()
        );

//        orders.setTotalAmount(1);
//        orders.setRealPayAmount();
        orders.setPostAmount(postAmount);

        orders.setPayMethod(payMethod);
        orders.setLeftMsg(leftMsg);

        orders.setIsComment(YesOrNo.NO.type);
        orders.setIsDelete(YesOrNo.NO.type);
        orders.setUpdatedTime(new Date());
        orders.setCreatedTime(new Date());

        /** 2.循环itemSpecIds保存订单商品信息表 */
        String[] specIds = itemSpecIds.split(",");
        Integer totalAmount = 0;
        Integer realPayAmount = 0;
        for (String specId : specIds) {
            /**2.1 根据SpecId 查询规格对象 */
            ItemsSpec itemsSpec = itemService.queryitemSpecById(specId);
            //TODO 购买数量从redis取
            int butCounts = 1;
            totalAmount += itemsSpec.getPriceNormal() *  butCounts;
            realPayAmount += itemsSpec.getPriceDiscount() * butCounts;

            /** 2.2 根据商品ID 查询商品对象 商品图片 */
            String itemId = itemsSpec.getItemId();
            Items items = itemService.queryItemById(itemId);
            String imgUrl = itemService.queryItemMainImageById(itemId);

            /** 2.3 保存订单商品表 一个订单包含多个订单商品表 对应一个订单状态表 */
            OrderItems orderItems = new OrderItems();
            orderItems.setId(sid.nextShort());
            orderItems.setOrderId(orderId);
            orderItems.setItemId(itemId);
            orderItems.setItemName(items.getItemName());
            orderItems.setItemImg(imgUrl);
            orderItems.setBuyCounts(butCounts);
            orderItems.setItemSpecId(specId);
            orderItems.setItemSpecName(itemsSpec.getName());
            orderItems.setPrice(itemsSpec.getPriceDiscount());
            orderItemsMapper.insert(orderItems);

            /** 2.4 在用户提交订单后，在规格表中需要扣除库存 */
            itemService.decreaseItemSpecStock(specId, butCounts);
        }

        orders.setTotalAmount(totalAmount);
        orders.setRealPayAmount(realPayAmount);
        ordersMapper.insert(orders);

        /** 3. 新订单状态表 */
        OrderStatus waitPayOrderStatus = new OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waitPayOrderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(waitPayOrderStatus);

        /** 4. 构建商户订单，用于传给支付中心 ----- */
        MerchantOrdersVO merchantOrdersVO = new MerchantOrdersVO();
        merchantOrdersVO.setMerchantOrderId(orderId);
        merchantOrdersVO.setMerchantUserId(userId);
        merchantOrdersVO.setAmount(realPayAmount + postAmount);
        merchantOrdersVO.setPayMethod(payMethod);

        /** 5. 构建自定义订单VO -> 用于在controller请求支付中心 */
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setMerchantOrdersVO(merchantOrdersVO);

        return orderVO;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateOrderStatus(String orderId, Integer orderStatus) {

        OrderStatus paidStatus = new OrderStatus();
        paidStatus.setOrderId(orderId);
        paidStatus.setOrderStatus(orderStatus);
        paidStatus.setPayTime(new Date());

        orderStatusMapper.updateByPrimaryKeySelective(paidStatus);
    }

    @Override
    public OrderStatus queryOrderStatusInfo(String orderId) {
        return orderStatusMapper.selectByPrimaryKey(orderId);
    }
}
