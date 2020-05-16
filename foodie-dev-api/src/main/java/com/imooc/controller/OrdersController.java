package com.imooc.controller;

import com.imooc.enums.PayMethod;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.service.OrderService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "用户下订单", notes = "用户下订单", httpMethod = "POST")
    @PostMapping("/create")
    public IMOOCJSONResult list(@RequestBody SubmitOrderBO submitOrderBO, HttpServletRequest request, HttpServletResponse response) {


        if (!submitOrderBO.getPayMethod().equals(PayMethod.WEIXIN.type) &&
                !submitOrderBO.getPayMethod().equals(PayMethod.ALIPAY.type)) {
            return IMOOCJSONResult.errorMsg("支付方式不支持");
        }

        /** 1.创建订单 */
        String orderId = orderService.createOrder(submitOrderBO);

        /** 2.创建订单以后 移除购物车中已结算（已提交）的商品 */
        // TODO 整合redis后，完善购物车中的已结算商品清除，并且同步到前端的cookie
        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, "", true);

        /** 3.向支付中心发送当前订单，用户保存支付中心的订单数据 */

        return IMOOCJSONResult.ok(orderId);
    }


}
