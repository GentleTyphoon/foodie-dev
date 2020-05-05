package com.imooc.pojo.vo;

import java.util.Date;

/**
 * @Author wuweifu
 * @Date 2020/5/5 11:02 下午
 * @Version V1.0
 * @Description: 商品评价
 **/
public class ItemCommentVO {

    private Integer commentLevel;
    private String content;
    private String specName;
    private Date createdTime;
    private String face;
    private String nickname;


    public Integer getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(Integer commentLevel) {
        this.commentLevel = commentLevel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
