package com.imooc.service;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.CommentLevelCountsVO;
import com.imooc.pojo.vo.ItemCommentVO;
import com.imooc.pojo.vo.ShopcartVO;
import com.imooc.utils.PagedGridResult;

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
     * @param itemId    商品ID
     * @param level 评价等级
     * @param page  当前页码
     * @param pageSize  每页显示条数
     * @return
     */
    public PagedGridResult queryPagedComment(String itemId, Integer level, Integer page, Integer pageSize);

    /**
     * 搜索商品列表
     * @param keywords  搜索关键字
     * @param sort  排序keyword
     * @param page  当前页码
     * @param pageSize  每页显示条数
     * @return
     */
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);

    /**
     * 搜索商品列表
     * @param catId  商品分类ID
     * @param sort  排序keyword
     * @param page  当前页码
     * @param pageSize  每页显示条数
     * @return
     */
    public PagedGridResult searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize);

    /**
     * 根据规格IDS查询最新的购物车中的商品数据(用户刷新渲染购物车中的商品数据)
     * @param specIds
     * @return
     */
    public List<ShopcartVO> queryItemsBySpecIds(String specIds);
}
