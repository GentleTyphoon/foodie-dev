package com.imooc.mapper;

import com.imooc.pojo.vo.ItemCommentVO;
import com.imooc.pojo.vo.SearchItemsVO;
import com.imooc.pojo.vo.ShopcartVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom {

    /**
     * 商品评价
     *
     * @param params
     * @return
     */
    public List<ItemCommentVO> queryItemComments(@Param("params") Map<String, Object> params);

    /**
     * 搜索商品列表
     *
     * @param paramsMap
     * @return
     */
    public List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> paramsMap);

    /**
     * 根据分类ID查询
     *
     * @param paramsMap
     * @return
     */
    public List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> paramsMap);

    /**
     * 刷新购物车
     *
     * @param specIdsList
     * @return
     */
    public List<ShopcartVO> queryItemsBySpecIds(@Param("specIdsList") List<String> specIdsList);

    /**
     *  减库存
     * @param specId    物品规格ID
     * @param pendingCounts 减少的数量
     * @return
     */
    public int decreaseItemSpecStock(@Param("specId") String specId, @Param("pendingCounts") int pendingCounts);
}