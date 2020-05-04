package com.imooc.pojo.vo;

/**
 * @Author wuweifu
 * @Date 2020/5/4 6:33 下午
 * @Version V1.0
 * @Description: 商品三级级分类VO
 **/
public class SubCategoryVO {

    /** 主键 */
    private Integer subId;
    /** 分类名称 */
    private String subName;
    /** 分类类型 */
    private String subType;
    /** 父id */
    private String subFatherId;


    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getSubFatherId() {
        return subFatherId;
    }

    public void setSubFatherId(String subFatherId) {
        this.subFatherId = subFatherId;
    }
}
