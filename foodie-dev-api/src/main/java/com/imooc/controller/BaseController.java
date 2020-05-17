package com.imooc.controller;

import org.springframework.stereotype.Controller;

/**
 * @Author wuweifu
 * @Date 2020/5/7 9:45 下午
 * @Version V1.0
 * @Description:
 **/
@Controller
public class BaseController {

    public static final Integer COMMON_PAGE_SIZE = 10;

    public static final Integer PAGE_SIZE = 20;

    public static final String FOODIE_SHOPCART = "shopcart";

    /** 微信支付成功 -> 支付中心(payReturnUrl) -> 天天吃货后台(@PostMapping("/notifyMerchantOrderPaid")) */
    String payReturnUrl = "http://localhost:8088/orders/notifyMerchantOrderPaid";
}
