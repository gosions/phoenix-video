package com.ginkgocap.ywxt.video.dto;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * Created by gintong on 2017/6/26.
 */
public class VideoDTO implements Serializable {

    /**
     * 视频id
     */
    private Long videoId;

    /**
     * 阿里云上对应的视频id
     */
    @NotEmpty(message = "阿里云上对应的视频id不能为空")
    private String aliyunVideoId;

    /**
     * 视频标题
     */
    @NotEmpty(message = "视频标题不能为空")
    private String title;

    /**
     * 标签
     */
    private String tag;

    /**
     * 简介/描述
     */
    private String description;

    /**
     * 视频封面
     */
    private String coverURL;

    /**
     * 视频分类id
     */
    private Number cateId;

    public VideoDTO() {
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public String getAliyunVideoId() {
        return aliyunVideoId;
    }

    public void setAliyunVideoId(String aliyunVideoId) {
        this.aliyunVideoId = aliyunVideoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }

    public Number getCateId() {
        return cateId;
    }

    public void setCateId(Number cateId) {
        this.cateId = cateId;
    }
}
