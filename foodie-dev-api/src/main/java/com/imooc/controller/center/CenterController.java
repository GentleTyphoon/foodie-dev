package com.imooc.controller.center;

import com.imooc.pojo.Users;
import com.imooc.service.center.CenterUserService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author wuweifu
 * @Date 2020/5/31 11:19 下午
 * @Version V1.0
 * @Description: 用户中心API
 **/
@RestController
@RequestMapping("/center")
@Api(value = "center - 用户中心", tags = {"用户中心展示的相关接口"})
public class CenterController {

    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "GET")
    @GetMapping("/userInfo")
    public IMOOCJSONResult userInfo(
            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam String userId
    ) {

        Users users = centerUserService.queryUserInfo(userId);

        return IMOOCJSONResult.ok(users);
    }

}
