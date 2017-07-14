package com.ginkgocap.ywxt.video.model;

import com.aliyuncs.vod.model.v20170321.GetVideoInfoResponse;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tb_video_attachment")
public class TbVideoAttachment implements Serializable {
    /**
     * 主键
     */
    @Id
    private Long id;

    @NotEmpty(message = "视频文件名不能为空(带后缀)")
    @Column(name = "file_name")
    private String fileName;

    /**
     * 文件大小
     */
    @NotNull(message = "视频文件大小不能为空")
    @Min(value = 1, message = "文件大小不能小于1")
    @Column(name = "file_size")
    private Long fileSize;

    /**
     * 文件类型
     */
    @Column(name = "file_type")
    private String fileType;

    /**
     * 下载地址
     */
    @Column(name = "download_url")
    private String downloadUrl;

    /**
     * 视频截图1
     */
    @Column(name = "screenshot_url_a")
    private String screenshotUrlA;

    /**
     * 视频截图2
     */
    @Column(name = "screenshot_url_b")
    private String screenshotUrlB;

    /**
     * 视频截图3
     */
    @Column(name = "screenshot_url_c")
    private String screenshotUrlC;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 阿里云上对应的视频id
     */
    @Column(name = "aliyun_video_id")
    private String aliyunVideoId;

    /**
     * 阿里云上对应的视频id
     */
    @Transient
    private GetVideoInfoResponse.Video aliyunVideo=null;

    private static final long serialVersionUID = 1L;

    public TbVideoAttachment() {
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
     * @return file_name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
     * 获取文件大小
     *
     * @return file_size - 文件大小
     */
    public Long getFileSize() {
        return fileSize;
    }

    /**
     * 设置文件大小
     *
     * @param fileSize 文件大小
     */
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * 获取文件类型
     *
     * @return file_type - 文件类型
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 设置文件类型
     *
     * @param fileType 文件类型
     */
    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    /**
     * 获取下载地址
     *
     * @return download_url - 下载地址
     */
    public String getDownloadUrl() {
        return downloadUrl;
    }

    /**
     * 设置下载地址
     *
     * @param downloadUrl 下载地址
     */
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl == null ? null : downloadUrl.trim();
    }

    /**
     * 获取视频截图1
     *
     * @return screenshot_url_a - 视频截图1
     */
    public String getScreenshotUrlA() {
        return screenshotUrlA;
    }

    /**
     * 设置视频截图1
     *
     * @param screenshotUrlA 视频截图1
     */
    public void setScreenshotUrlA(String screenshotUrlA) {
        this.screenshotUrlA = screenshotUrlA == null ? null : screenshotUrlA.trim();
    }

    /**
     * 获取视频截图2
     *
     * @return screenshot_url_b - 视频截图2
     */
    public String getScreenshotUrlB() {
        return screenshotUrlB;
    }

    /**
     * 设置视频截图2
     *
     * @param screenshotUrlB 视频截图2
     */
    public void setScreenshotUrlB(String screenshotUrlB) {
        this.screenshotUrlB = screenshotUrlB == null ? null : screenshotUrlB.trim();
    }

    /**
     * 获取视频截图3
     *
     * @return screenshot_url_c - 视频截图3
     */
    public String getScreenshotUrlC() {
        return screenshotUrlC;
    }

    /**
     * 设置视频截图3
     *
     * @param screenshotUrlC 视频截图3
     */
    public void setScreenshotUrlC(String screenshotUrlC) {
        this.screenshotUrlC = screenshotUrlC == null ? null : screenshotUrlC.trim();
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

    public String getAliyunVideoId() {
        return aliyunVideoId;
    }

    public void setAliyunVideoId(String aliyunVideoId) {
        this.aliyunVideoId = aliyunVideoId;
    }

    public GetVideoInfoResponse.Video getAliyunVideo() {
        return aliyunVideo;
    }

    public void setAliyunVideo(GetVideoInfoResponse.Video aliyunVideo) {
        this.aliyunVideo = aliyunVideo;
    }
}