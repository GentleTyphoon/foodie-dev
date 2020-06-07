package com.imooc.controller.center;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUserBO;
import com.imooc.service.center.CenterUserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author wuweifu
 * @Date 2020/6/7 4:15 下午
 * @Version V1.0
 * @Description:
 **/
@Api(value = "用户信息相关接口")
@RestController
@RequestMapping("/userInfo")
public class CenterUserController {

    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("/update")
    public IMOOCJSONResult userInfo(
            @ApiParam(name = "update", value = "用户ID", required = true)
            @RequestParam String userId,
            @RequestBody @Valid CenterUserBO centerUserBO,
            BindingResult bindingResult,
            HttpServletRequest request,
            HttpServletResponse response
            ) {

        /** 判断 bindingResult 是否有错误的验证信息，如果有，则直接return */
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = getErrors(bindingResult);

            return IMOOCJSONResult.errorMap(errors);
        }


        Users users = centerUserService.updateUserInfo(userId, centerUserBO);

        users = setNullProperty(users);

        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(users), true);

        //TODO 后续要改， 增加令牌token, 会整合进redis，分布式会话

        return IMOOCJSONResult.ok(users);
    }

    private Map<String, String> getErrors(BindingResult bindingResult) {

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        Map<String, String> map = new HashMap<String, String>();

        for (FieldError fieldError : fieldErrors) {
            /** 发生验证错误的某一个属性 */
            String field = fieldError.getField();

            /** 验证错误的信息 */
            String defaultMessage = fieldError.getDefaultMessage();

            map.put(field, defaultMessage);
        }

        return map;
    }

    private Users setNullProperty(Users users) {
        users.setPassword(null);
        users.setMobile(null);
        users.setEmail(null);
        users.setCreatedTime(null);
        users.setUpdatedTime(null);
        users.setBirthday(null);
        return users;
    }

}
