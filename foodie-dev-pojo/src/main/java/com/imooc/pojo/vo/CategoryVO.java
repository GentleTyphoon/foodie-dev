package com.imooc.pojo.vo;


import java.util.List;

/**
 * @Author wuweifu
 * @Date 2020/5/4 6:27 下午
 * @Version V1.0
 * @Description: 商品二级分类VO
 **/
public class CategoryVO {

    /** 主键 */
    private Integer id;
    /** 分类名称 */
    private String name;
    /** 分类类型 */
    private String type;
    /** 父id */
    private String fatherId;

    /** 商品三级级分类VO */
    private List<SubCategoryVO> subCatList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public List<SubCategoryVO> getSubCatList() {
        return subCatList;
    }

    public void setSubCatList(List<SubCategoryVO> subCatList) {
        this.subCatList = subCatList;
    }
}
