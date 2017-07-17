package com.ginkgocap.ywxt.video.model;

import com.ginkgocap.ywxt.video.dto.UserDTO;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_video")
public class TbVideo implements Serializable{
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 视频标题
     */
    @NotEmpty(message = "视频标题不能为空")
    @Length(max = 200, message = "视频标题不能超过200")
    private String title;

    /**
     * 标签
     */
    private String tag;

    /**
     * 简介/描述
     */
    @Length(max = 1000, message = "简介/描述不能超过1000")
    private String description;

    /**
     * 状态，0--未审核，1--正常，2--审核驳回，3--禁用，4--删除
     */
    private int status=0;

    /**
     * 视频禁用的原因
     */
    private String forbiddenReason;

    /**
     * 是否置顶，0--否，1--是
     */
    private int top=0;

    /**
     * 点赞次数
     */
    @Column(name = "like_time")
    private Long likeTime=0L;

    /**
     * 播放次数
     */
    @Column(name = "play_time")
    private Long playTime=0L;

    /**
     * 评论次数
     */
    @Column(name = "discuss_time")
    private Long discussTime=0L;

    /**
     * 收藏次数
     */
    @Column(name = "enshrine_time")
    private Long enshrineTime=0L;

    /**
     * 视频时长
     */
    @Column(name = "duration")
    private Float duration=Float.parseFloat("0.00");

    /**
     * 原视频附件
     */
    @Column(name = "attachment_id")
    private Long attachmentId;

    @Transient
    private TbVideoAttachment attachment=null;

    /**
     * 转码后的视频附件
     */
    @Column(name = "transform_attachment_id")
    private Long transformAttachmentId;

    @Transient
    private TbVideoAttachment transformAttachment=null;

    /**
     * 置顶/取消置顶时间
     */
    @Column(name = "top_time")
    private Date topTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建者
     */
    @Column(name = "user_id")
    private Long userId;

    @Transient
    private UserDTO userDTO;

    public TbVideo() {
    }

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取视频标题
     *
     * @return title - 视频标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置视频标题
     *
     * @param title 视频标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取标签
     *
     * @return tag - 标签
     */
    public String getTag() {
        return tag;
    }

    /**
     * 设置标签
     *
     * @param tag 标签
     */
    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    /**
     * 获取简介/描述
     *
     * @return description - 简介/描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置简介/描述
     *
     * @param description 简介/描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 获取状态，0--未审核，1--正常，2--审核驳回，3--禁用，4--删除
     *
     * @return status - 状态，0--未审核，1--正常，2--审核驳回，3--禁用，4--删除
     */
    public int getStatus() {
        return status;
    }

    /**
     * 设置状态，0--未审核，1--正常，2--审核驳回，3--禁用，4--删除
     *
     * @param status 状态，0--未审核，1--正常，2--审核驳回，3--禁用，4--删除
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 获取是否置顶，0--否，1--是
     *
     * @return top - 是否置顶，0--否，1--是
     */
    public int getTop() {
        return top;
    }

    /**
     * 设置是否置顶，0--否，1--是
     *
     * @param top 是否置顶，0--否，1--是
     */
    public void setTop(int top) {
        this.top = top;
    }

    /**
     * 获取点赞次数
     *
     * @return like_time - 点赞次数
     */
    public Long getLikeTime() {
        return likeTime;
    }

    /**
     * 设置点赞次数
     *
     * @param likeTime 点赞次数
     */
    public void setLikeTime(Long likeTime) {
        this.likeTime = likeTime;
    }

    /**
     * 获取播放次数
     *
     * @return play_time - 播放次数
     */
    public Long getPlayTime() {
        return playTime;
    }

    /**
     * 设置播放次数
     *
     * @param playTime 播放次数
     */
    public void setPlayTime(Long playTime) {
        this.playTime = playTime;
    }

    /**
     * 获取评论次数
     *
     * @return discuss_time - 评论次数
     */
    public Long getDiscussTime() {
        return discussTime;
    }

    /**
     * 设置评论次数
     *
     * @param discussTime 评论次数
     */
    public void setDiscussTime(Long discussTime) {
        this.discussTime = discussTime;
    }

    /**
     * 获取原视频附件
     *
     * @return attachment_id - 原视频附件
     */
    public Long getAttachmentId() {
        return attachmentId;
    }

    /**
     * 设置原视频附件
     *
     * @param attachmentId 原视频附件
     */
    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    /**
     * 获取转码后的视频附件
     *
     * @return transform_attachment_id - 转码后的视频附件
     */
    public Long getTransformAttachmentId() {
        return transformAttachmentId;
    }

    /**
     * 设置转码后的视频附件
     *
     * @param transformAttachmentId 转码后的视频附件
     */
    public void setTransformAttachmentId(Long transformAttachmentId) {
        this.transformAttachmentId = transformAttachmentId;
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

    /**
     * 获取创建者
     *
     * @return user_id - 创建者
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置创建者
     *
     * @param userId 创建者
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public TbVideoAttachment getAttachment() {
        return attachment;
    }

    public void setAttachment(TbVideoAttachment attachment) {
        this.attachment = attachment;
    }

    public TbVideoAttachment getTransformAttachment() {
        return transformAttachment;
    }

    public void setTransformAttachment(TbVideoAttachment transformAttachment) {
        this.transformAttachment = transformAttachment;
    }

    public String getForbiddenReason() {
        return forbiddenReason;
    }

    public void setForbiddenReason(String forbiddenReason) {
        this.forbiddenReason = forbiddenReason;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public Date getTopTime() {
        return topTime;
    }

    public void setTopTime(Date topTime) {
        this.topTime = topTime;
    }

    public Long getEnshrineTime() {
        return enshrineTime;
    }

    public void setEnshrineTime(Long enshrineTime) {
        this.enshrineTime = enshrineTime;
    }
}