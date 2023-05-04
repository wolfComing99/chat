package com.wolf.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author wolf
 * @since 2023-05-04
 */
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer articleId;

    private LocalDateTime createdTime;

    private Integer authorId;

    private String content;

    private String title;

    private String classify;

    private Integer likeNum;

    private Integer collectionNum;

    private Double hotNum;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }
    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }
    public Integer getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(Integer collectionNum) {
        this.collectionNum = collectionNum;
    }
    public Double getHotNum() {
        return hotNum;
    }

    public void setHotNum(Double hotNum) {
        this.hotNum = hotNum;
    }

    @Override
    public String toString() {
        return "Article{" +
            "articleId=" + articleId +
            ", createdTime=" + createdTime +
            ", authorId=" + authorId +
            ", content=" + content +
            ", title=" + title +
            ", classify=" + classify +
            ", likeNum=" + likeNum +
            ", collectionNum=" + collectionNum +
            ", hotNum=" + hotNum +
        "}";
    }
}
