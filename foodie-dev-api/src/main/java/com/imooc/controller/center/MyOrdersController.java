package com.imooc.controller.center;

import com.imooc.controller.BaseController;
import com.imooc.pojo.Orders;
import com.imooc.pojo.Users;
import com.imooc.service.center.CenterUserService;
import com.imooc.service.center.MyOrdersService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(value = "center - 用户中心-订单管理", tags = {"用户中心订单管理的相关接口"})
@RestController
@RequestMapping("/myorders")
public class MyOrdersController extends BaseController {

    @Autowired
    private MyOrdersService myOrdersService;

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("/query")
    public IMOOCJSONResult comments(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,

            @ApiParam(name = "orderStatus", value = "订单状态", required = false)
            @RequestParam Integer orderStatus,

            @ApiParam(name = "page", value = "第几页", required = false)
            @RequestParam Integer page,

            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult grid = myOrdersService.queryMyOrders(userId, orderStatus, page, pageSize);

        return IMOOCJSONResult.ok(grid);
    }


    /**
     * 商家发货没有后端，所以这个接口仅仅只是用于模拟
     * @param orderId   订单ID
     * @return
     * @throws Exception
     */
    @ApiOperation(value="商家发货", notes="商家发货", httpMethod = "GET")
    @GetMapping("/deliver")
    public IMOOCJSONResult deliver(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId) throws Exception {

        if (StringUtils.isBlank(orderId)) {
            return IMOOCJSONResult.errorMsg("订单ID不能为空");
        }
        myOrdersService.updateDeliverOrderStatus(orderId);
        return IMOOCJSONResult.ok();
    }

    /**
     * 用户确认收货
     * @param orderId   订单ID
     * @param userId    用户ID
     * @return
     * @throws Exception
     */
    @ApiOperation(value="用户确认收货", notes="用户确认收货", httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public IMOOCJSONResult confirmReceive(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId,

            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId
    ) throws Exception {

        IMOOCJSONResult result = checkUserOrder(orderId, userId);

        if (HttpStatus.OK.value() != result.getStatus()) {
            return result;
        }



        return IMOOCJSONResult.ok();
    }

    /**
     * 用户删除订单
     * @param orderId   订单ID
     * @param userId    用户ID
     * @return
     * @throws Exception
     */
    @ApiOperation(value="用户删除订单", notes="用户删除订单", httpMethod = "POST")
    @PostMapping("/delete")
    public IMOOCJSONResult delete(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId,

            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId
    ) throws Exception {

        IMOOCJSONResult result = checkUserOrder(orderId, userId);

        if (HttpStatus.OK.value() != result.getStatus()) {
            return result;
        }



        return IMOOCJSONResult.ok();
    }

    /**
     * 验证用户和订单是否用关联，避免非法调用
     * @return
     */
    private IMOOCJSONResult checkUserOrder(String orderId, String userId) {

        Orders orders = myOrdersService.queryMyOrder(userId, orderId);

        if (null == orders) {
            return IMOOCJSONResult.errorMap("订单不存在!");
        }

        return IMOOCJSONResult.ok();
    }

}
