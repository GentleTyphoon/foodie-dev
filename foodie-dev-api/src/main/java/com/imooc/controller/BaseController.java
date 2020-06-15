package com.imooc.controller;

import org.springframework.stereotype.Controller;

import java.io.File;

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
//    String payReturnUrl = "http://localhost:8088/orders/notifyMerchantOrderPaid";
    String payReturnUrl = "http://56sm9i.natappfree.cc/orders/notifyMerchantOrderPaid";

    /** 支付中心的调用地址 */
    String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    /**
     * 用户头像上传的位置
     */
    public static final String IMAGE_USER_FACE_LOCATION =
            File.separator + "Users" +
            File.separator + "wuweifu" +
            File.separator + "Documents" +
            File.separator + "";

}
