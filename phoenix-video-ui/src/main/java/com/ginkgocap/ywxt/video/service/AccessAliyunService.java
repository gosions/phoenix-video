package com.ginkgocap.ywxt.video.service;

import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.model.Message;
import com.aliyuncs.mts.model.v20140618.QueryMediaWorkflowListResponse;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.aliyuncs.vod.model.v20170321.*;
import com.ginkgocap.ywxt.video.dto.VideoDTO;
import com.ginkgocap.ywxt.video.model.TbVideo;

/**
 * Created by gintong on 2017/6/16.
 */
public interface AccessAliyunService {

    /**
     * 阿里云 视频点播 获取播放凭证
     * @param videoId
     * @return
     */
    GetVideoPlayAuthResponse getVideoPlayAuth(String videoId);

    /**
     * 阿里云 视频点播 获取视频上传凭证和地址
     * @param tbVideo
     * @return
     */
    CreateUploadVideoResponse createUploadVideo(TbVideo tbVideo, String ip);

    /**
     * 阿里云 视频点播 刷新视频上传凭证
     * @param videoId
     * @return
     */
    RefreshUploadVideoResponse refreshUploadVideo(String videoId);

    /**
     * 阿里云 视频点播 获取视频信息
     * @param videoId
     * @return
     */
    GetVideoInfoResponse getVideoInfo(String videoId);

    /**
     * 阿里云 视频点播 修改视频信息
     * @param videoDTO
     * @return
     */
    UpdateVideoInfoResponse updateVideoInfo(VideoDTO videoDTO);


    /**
     * 阿里云oss方式上传文件 获取安全令牌
     * @return
     */
    AssumeRoleResponse getToken();

    /**
     * 阿里云 消息服务 创建队列
     * @param queueName
     * @param pollingWaitSeconds
     * @param maxMessageSize
     * @return
     */
    CloudQueue createQueue(String queueName, Integer pollingWaitSeconds, Long maxMessageSize);

    /**
     * 阿里云 消息服务 接收和删除消息(从队列中取出并删除该条消息。)
     * @param queueName
     * @return
     */
    Message consumeMessage(String queueName);

    /**
     * 阿里云 媒体转码 查询媒体工作流
     * @param mediaWorkflowId
     * @return
     */
    QueryMediaWorkflowListResponse MediaWorkflowIds(String mediaWorkflowId);


}
