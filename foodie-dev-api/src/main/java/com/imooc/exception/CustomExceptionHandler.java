package com.imooc.exception;

import com.imooc.utils.IMOOCJSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @Author wuweifu
 * @Date 2020/6/15 11:36 下午
 * @Version V1.0
 * @Description: 自定义异常处理
 **/
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public IMOOCJSONResult handlerMaxUploadFile(MaxUploadSizeExceededException ex) {

        return IMOOCJSONResult.errorMsg("文件上传大小不能超过500KB, 请压缩图片或降低图片质量再上传");

    }

}
