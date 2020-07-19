package com.imooc.controller.center;

import com.imooc.controller.BaseController;
import com.imooc.enums.YesOrNo;
import com.imooc.pojo.OrderItems;
import com.imooc.pojo.Orders;
import com.imooc.service.center.MyCommentsService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author wuweifu
 * @Date 2020/7/19 9:41 下午
 * @Version V1.0
 * @Description: 用户中心评价模块
 **/
@Api(value = "用户中心评价模块", tags = {"用户中心评价模块"})
@RestController
@RequestMapping("/mycomments")
public class MyCommentsController extends BaseController {

    @Autowired
    private MyCommentsService myCommentsService;

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("/pending")
    public IMOOCJSONResult pending(
            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam String userId,

            @ApiParam(name = "orderId", value = "订单ID", required = true)
            @RequestParam String orderId
    ) {
        /** 判断用户和订单是否关联 */
        IMOOCJSONResult result = checkUserOrder(orderId, userId);
        if (HttpStatus.OK.value() != result.getStatus()) {
            return result;
        }

        /** 判断该笔订单是否已经评价过，评价过就直接return */
        Orders orders = (Orders) result.getData();
        if (YesOrNo.YES.type.equals(orders.getIsComment())) {
            return IMOOCJSONResult.errorMsg("该笔订单已经评价过");
        }

        List<OrderItems> orderItems = myCommentsService.queryPendingComment(orderId);

        return IMOOCJSONResult.ok(orderItems);
    }

}
