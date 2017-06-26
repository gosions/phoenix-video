package com.ginkgocap.ywxt.video.model;

import com.ginkgocap.ywxt.user.model.User;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_video_report")
public class TbVideoReport implements Serializable {
    @Id
    private Long id;

    /**
     * 视频id
     */
    @Column(name = "video_id")
    private Long videoId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    @Transient
    private User user;

    /**
     * 举报类型
     */
    @Column(name = "report_type")
    private String reportType;

    /**
     * 举报描述
     */
    @Column(name = "report_describe")
    private String reportDescribe;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public TbVideoReport() {
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取视频id
     *
     * @return video_id - 视频id
     */
    public Long getVideoId() {
        return videoId;
    }

    /**
     * 设置视频id
     *
     * @param videoId 视频id
     */
    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取举报类型
     *
     * @return report_type - 举报类型
     */
    public String getReportType() {
        return reportType;
    }

    /**
     * 设置举报类型
     *
     * @param reportType 举报类型
     */
    public void setReportType(String reportType) {
        this.reportType = reportType == null ? null : reportType.trim();
    }

    /**
     * 获取举报描述
     *
     * @return report_describe - 举报描述
     */
    public String getReportDescribe() {
        return reportDescribe;
    }

    /**
     * 设置举报描述
     *
     * @param reportDescribe 举报描述
     */
    public void setReportDescribe(String reportDescribe) {
        this.reportDescribe = reportDescribe == null ? null : reportDescribe.trim();
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}