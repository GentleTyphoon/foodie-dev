package com.imooc.controller.center;

import com.imooc.controller.BaseController;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUserBO;
import com.imooc.resource.FileUpload;
import com.imooc.service.center.CenterUserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
public class CenterUserController extends BaseController {

    @Autowired
    private CenterUserService centerUserService;

    @Autowired
    private FileUpload fileUpload;

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

        setNullProperty(users);

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

    @ApiOperation(value = "修改用户头像", notes = "修改用户头像", httpMethod = "POST")
    @PostMapping("/uploadFace")
    public IMOOCJSONResult uploadFace(
            @ApiParam(name = "update", value = "用户ID", required = true)
            @RequestParam String userId,

            @ApiParam(name = "file", value = "用户头像", required = true)
            MultipartFile file,

            HttpServletRequest request,
            HttpServletResponse response
    ) {

        /** 定义头像保存的地址 */
//        String fileSpace = IMAGE_USER_FACE_LOCATION;
        String fileSpace = fileUpload.getImageUserFaceLocation();

        /** 在路径上为每一个用户增加一个用户userId, 用于区分不同用户上传 */
        String uploadPathPrefix = File.separator + userId;

        /** 开始文件上传 */
        if (null != file) {

            FileOutputStream fileOutputStream = null;

            try {
                /** 获得文件上传的文件名称 */
                String originalFilename = file.getOriginalFilename();

                if (StringUtils.isNoneBlank(originalFilename)) {

                    /** xxx.png -> ["xxx", "png"] */
                    String[] fileNameArr = originalFilename.split("\\.");

                    /** 获取文件后缀名 */
                    String suffix = fileNameArr[fileNameArr.length - 1];

                    /** eg. face-{userId}.png 覆盖式上传. 如果要增量式 额外拼接时间戳 */
                    String newFileName = "face-" + userId + "." + suffix;

                    /** 上传的头像最终保存的位置 */
                    String finalFacePath = fileSpace + uploadPathPrefix + File.separator + newFileName;

                    File outFile = new File(finalFacePath);
                    if (null != outFile.getParentFile()) {
                        /** 创建文件夹 */
                        outFile.getParentFile().mkdirs();
                    }

                    /** 文件输出保存到目录 */
                    fileOutputStream = new FileOutputStream(outFile);
                    InputStream inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != fileOutputStream) {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } else {
            return IMOOCJSONResult.errorMsg("文件不能为空");
        }

        return IMOOCJSONResult.ok();
        }

}
