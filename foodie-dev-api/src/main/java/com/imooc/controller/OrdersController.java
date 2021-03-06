package com.imooc.controller;

import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayMethod;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.pojo.vo.MerchantOrdersVO;
import com.imooc.pojo.vo.OrderVO;
import com.imooc.service.OrderService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author wuweifu
 * @Date 2020/5/11 11:19 下午
 * @Version V1.0
 * @Description: 用户地址API
 **/
@Api(value = "订单", tags = "订单API")
@RestController
@RequestMapping("/orders")
public class OrdersController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value = "用户下订单", notes = "用户下订单", httpMethod = "POST")
    @PostMapping("/create")
    public IMOOCJSONResult list(@RequestBody SubmitOrderBO submitOrderBO, HttpServletRequest request, HttpServletResponse response) {

        if (!submitOrderBO.getPayMethod().equals(PayMethod.WEIXIN.type) &&
                !submitOrderBO.getPayMethod().equals(PayMethod.ALIPAY.type)) {
            return IMOOCJSONResult.errorMsg("支付方式不支持");
        }

        /** 1.创建订单 */
        OrderVO orderVO = orderService.createOrder(submitOrderBO);
        String orderId = orderVO.getOrderId();
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(payReturnUrl);
        // TODO 测试支付，暂时写为1分钱, 之后注释掉即可
        merchantOrdersVO.setAmount(1);

        /** 2.创建订单以后 移除购物车中已结算（已提交）的商品 */
        // TODO 整合redis后，完善购物车中的已结算商品清除，并且同步到前端的cookie
        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, "", true);

        /** 3.向支付中心发送当前订单，用户保存支付中心的订单数据 */
        HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("imoocUserId","imooc");
        headers.add("password","imooc");

        HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO, headers);

        ResponseEntity<IMOOCJSONResult> responseEntity = restTemplate.postForEntity(paymentUrl, entity,
                IMOOCJSONResult.class);

        IMOOCJSONResult result = responseEntity.getBody();
        if (result.getStatus() != 200) {
            return IMOOCJSONResult.errorMsg("支付中心订单创建失败");
        }


        return IMOOCJSONResult.ok(orderId);
    }

    /**
     * 回调接口 -> 订单状态改为等待收货
     * @param merchantOrderId   order表的ID
     * @return
     * eg:http://localhost:8088/orders/notifyMerchantOrderPaid?merchantOrderId=20051710487920066560
     * 这个回调地址的链接就是支付中心orders表里面的 return_url
     */
    @PostMapping("/notifyMerchantOrderPaid")
     public Integer notifyMerchantOrderPaid(@RequestParam String merchantOrderId) {

        /** 支付中心通知天天吃货的后台 更改订单状态 */
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);

        /** 返回200 */
        return HttpStatus.OK.value();
     }

    /**
     * 查询订单状态
     * @param orderId   订单ID
     * @return
     */
    @PostMapping("/getPaidOrderInfo")
    public IMOOCJSONResult getPaidOrderInfo(@RequestParam String orderId) {

        OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
        return IMOOCJSONResult.ok(orderStatus);
    }

}
