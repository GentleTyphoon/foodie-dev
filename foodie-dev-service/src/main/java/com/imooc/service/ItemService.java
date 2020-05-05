package com.imooc.service;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.CommentLevelCountsVO;
import com.imooc.pojo.vo.ItemCommentVO;

import java.util.List;

/**
 * @Author wuweifu
 * @Date 2020/5/5 7:03 下午
 * @Version V1.0
 * @Description:
 **/
public interface ItemService {

    /**
     * 根据商品ID查询详情
     * @param id
     * @return
     */
    public Items queryItemById(String id);

    /**
     * 根据商品id查询商品图片列表
     * @param itemId
     * @return
     */
    public List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品ID查询商品规格
     * @param itemId
     * @return
     */
    public List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品ID查询商品参数
     * @param itemId
     * @return
     */
    public ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品id查询商品的各种评价等级数量
     * @param itemId
     * @return
     */
    public CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 商品评价
     * @return
     */
    public List<ItemCommentVO> queryPagedComment(String itemId, Integer level);
}
