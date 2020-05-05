package com.imooc.mapper;

import com.imooc.pojo.vo.ItemCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom {

    /**
     * 商品评价
     * @param params
     * @return
     */
    public List<ItemCommentVO> queryItemComments(@Param("params") Map<String, Object> params);
}