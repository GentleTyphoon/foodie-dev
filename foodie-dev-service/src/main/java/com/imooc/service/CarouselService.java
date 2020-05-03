package com.imooc.service;

import com.imooc.pojo.Carousel;

import java.util.List;

/**
 * @Author wuweifu
 * @Date 2020/5/4 1:44 上午
 * @Version V1.0
 * @Description:
 **/
public interface CarouselService {

    /**
     * 查询所有轮播图列表
     * @param isShow
     * @return
     */
    public List<Carousel> queryAll(Integer isShow);
}
