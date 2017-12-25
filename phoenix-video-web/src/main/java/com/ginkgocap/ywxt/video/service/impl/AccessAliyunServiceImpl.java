package com.ginkgocap.ywxt.video.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.Message;
import com.aliyun.mns.model.QueueMeta;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.mts.model.v20140618.QueryMediaWorkflowListRequest;
import com.aliyuncs.mts.model.v20140618.QueryMediaWorkflowListResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.aliyuncs.vod.model.v20170321.*;
import com.ginkgocap.ywxt.video.dto.VideoDTO;
import com.ginkgocap.ywxt.video.model.TbVideo;
import com.ginkgocap.ywxt.video.service.AccessAliyunService;
import com.ginkgocap.ywxt.video.service.IRedisService;
import com.ginkgocap.ywxt.video.utils.JSONUtil;
import com.ginkgocap.ywxt.video.utils.oss.OSSConfigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by gintong on 2017/6/16.
 */
@Component
@Transactional(propagation = Propagation.NEVER)
public class AccessAliyunServiceImpl implements AccessAliyunService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessAliyunServiceImpl.class);

    private static final long EXPIRE = 1 * 30 * 60;

    @Resource
    private IRedisService iRedisService;

    private static final long AUTH_TIMEOUT = 3600L;

    private static OSSConfigure ossConfigure = OSSConfigure.getInstance();

    private static DefaultAcsClient client = null;

    private static MNSClient mnsClient  = null;

    private static DefaultAcsClient vodClient  = null;

    static {
        //oss
        IClientProfile profile = DefaultProfile.getProfile(
                "cn-beijing",
                ossConfigure.getAccessKeyId(),
                ossConfigure.getAccessKeySecret());
        client = new DefaultAcsClient(profile);
        //msn访问api
        CloudAccount cloudAccount = new CloudAccount(
                ossConfigure.getMns_accessKeyId(),
                ossConfigure.getMns_accessKeySecret(),
                ossConfigure.getMns_endpoint());
        mnsClient = cloudAccount.getMNSClient();
        //点播方式访问api
        DefaultProfile profile_vod = DefaultProfile.getProfile(
                "cn-shanghai",
                ossConfigure.getMns_accessKeyId(),
                ossConfigure.getMns_accessKeySecret());
        vodClient = new DefaultAcsClient(profile_vod);
    }

    /**
     * 阿里云 视频点播 获取播放凭证
     * @param videoId
     * @return
     */
    @Override
    public GetVideoPlayAuthResponse getVideoPlayAuth(String videoId) {
        GetVideoPlayAuthRequest req = new GetVideoPlayAuthRequest();
        req.setVideoId(videoId);
        GetVideoPlayAuthResponse res = null;
        try {
            res = vodClient.getAcsResponse(req);
        } catch (ServerException e) {
            e.printStackTrace();
            throw new RuntimeException("GetVideoPlayAuthRequest Server failed");
        } catch (ClientException e) {
            e.printStackTrace();
            throw new RuntimeException("GetVideoPlayAuthRequest Client failed");
        }
        LOGGER.info("获取播放凭证,response={}", JSON.toJSONString(res));
        return res;
    }

    /**
     * 阿里云 视频点播 获取视频播放地址
     *
     * @param videoId
     * @return
     */
    @Override
    public GetPlayInfoResponse GetPlayInfo(String videoId) {
        GetPlayInfoRequest req = new GetPlayInfoRequest();
        req.setVideoId(videoId);
        req.setAuthTimeout(AUTH_TIMEOUT);
        req.setFormats("mp4");
        GetPlayInfoResponse res = null;
        try {
            res = vodClient.getAcsResponse(req);
        } catch (ServerException e) {
            e.printStackTrace();
            throw new RuntimeException("GetPlayInfoRequest Server failed");
        } catch (ClientException e) {
            e.printStackTrace();
            throw new RuntimeException("GetPlayInfoRequest Client failed");
        }
        LOGGER.info("获取视频播放地址,response={}", JSON.toJSONString(res));
        return res;
    }

    /**
     * 阿里云 视频点播 获取视频上传凭证和地址
     *
     * @param tbVideo
     * @return
     */
    @Override
    public CreateUploadVideoResponse createUploadVideo(TbVideo tbVideo, String ip) {
        CreateUploadVideoRequest req = new CreateUploadVideoRequest();
        CreateUploadVideoResponse res = null;
        try {
              /*必选，视频源文件名称（必须带后缀, 支持 "3GP","AVI","FLV","MP4","M3U8","MPG","ASF","WMV","MKV","MOV","TS",    "WebM","MPEG","RM","RMVB","DAT","ASX","WVX","MPE","MPA","F4V","MTS","VOB","GIF"）*/
            req.setFileName(tbVideo.getAttachment().getFileName());
            //必选，视频源文件字节数
            req.setFileSize(tbVideo.getAttachment().getFileSize());
            //必选，视频标题
            req.setTitle(tbVideo.getTitle());
            //可选，分类ID
            req.setCateId(0);
            //可选，视频标签，多个用逗号分隔
            req.setTags(tbVideo.getTag());
            //可选，视频描述
            req.setDescription(tbVideo.getDescription());
            //可选，视频上传所在区域IP
            req.setIP(ip);
            res = vodClient.getAcsResponse(req);
        } catch (ServerException e) {
            LOGGER.error("CreateUploadVideoRequest Server Exception:");
            e.printStackTrace();
        } catch (ClientException e) {
            LOGGER.error("CreateUploadVideoRequest Client Exception:");
            e.printStackTrace();
        }
        LOGGER.info("获取视频上传凭证和地址,response={}", JSON.toJSONString(res));
        return res;
    }

    /**
     * 阿里云 视频点播 刷新视频上传凭证
     *
     * @param videoId
     * @return
     */
    @Override
    public RefreshUploadVideoResponse refreshUploadVideo(String videoId) {
        RefreshUploadVideoRequest req = new RefreshUploadVideoRequest();
        RefreshUploadVideoResponse res = null;
        try {
            req.setVideoId(videoId);
            res = vodClient.getAcsResponse(req);
        } catch (ServerException e) {
            System.out.println("refreshUploadVideo Server Exception:");
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("refreshUploadVideo Client Exception:");
            e.printStackTrace();
        }
        LOGGER.info("刷新视频上传凭证,response={}", JSON.toJSONString(res));
        return res;
    }

    /**
     * 阿里云 视频点播 获取视频信息
     *
     * @param videoId
     * @return
     */
    @Override
    public GetVideoInfoResponse getVideoInfo(String videoId) {
        GetVideoInfoRequest req = new GetVideoInfoRequest();
        req.setVideoId(videoId);
        GetVideoInfoResponse res = null;
        try {
            String s = iRedisService.get(videoId);
            LOGGER.info("先从redis中获取，s = {}", s);
            if(null == s) {
                res = vodClient.getAcsResponse(req);
                if (null != res.getVideo() && "Normal".equals(res.getVideo().getStatus())) {
                    s = JSONUtil.toJson(res);
                    iRedisService.set(videoId, s, EXPIRE);
                    LOGGER.info("放入redis中，key = {}， expire = {}", videoId, EXPIRE);
                }
            }
            res = JSONUtil.toBean(s, GetVideoInfoResponse.class);
        } catch (ServerException e) {
            e.printStackTrace();
            throw new RuntimeException("GetVideoInfoRequest Server failed");
        } catch (ClientException e) {
            e.printStackTrace();
            throw new RuntimeException("GetVideoInfoRequest Client failed");
        }
        LOGGER.info("获取视频信息,response={}", JSON.toJSONString(res));
        return res;
    }

    /**
     * 阿里云 视频点播 修改视频信息
     *
     * @param videoDTO
     * @return
     */
    @Override
    public UpdateVideoInfoResponse updateVideoInfo(VideoDTO videoDTO) {
        UpdateVideoInfoRequest req = new UpdateVideoInfoRequest();
        req.setVideoId(videoDTO.getAliyunVideoId());
        req.setCoverURL(videoDTO.getCoverURL());
      //  req.setCateId(videoDTO.getCateId().intValue());
        req.setDescription(videoDTO.getDescription());
        req.setTags(videoDTO.getTag());
        req.setTitle(videoDTO.getTitle());
        UpdateVideoInfoResponse res = null;
        try {
            res = vodClient.getAcsResponse(req);
            GetVideoInfoResponse videoInfo = getVideoInfo(videoDTO.getAliyunVideoId());
            if (null != videoInfo && "Normal".equals(videoInfo.getVideo().getStatus())) {
                iRedisService.del(videoDTO.getAliyunVideoId());
                iRedisService.set(videoDTO.getAliyunVideoId(), JSONUtil.toJson(res), EXPIRE);
                LOGGER.info("更新redis，key = {}， expire = {}", videoDTO.getAliyunVideoId(), EXPIRE);
            }
        } catch (ServerException e) {
            e.printStackTrace();
            throw new RuntimeException("UpdateVideoInfoRequest Server failed");
        } catch (ClientException e) {
            e.printStackTrace();
            throw new RuntimeException("UpdateVideoInfoRequest Client failed");
        }
        LOGGER.info("获取视频信息,response={}", JSON.toJSONString(res));
        return res;
    }

    /**
     * 阿里云 视频点播 删除视频
     *
     * @param videoIds 视频ID列表，多个用逗号分隔，最多支持10个
     * @return
     */
    @Override
    public DeleteVideoResponse deleteVideo(String videoIds) {
        DeleteVideoRequest req = new DeleteVideoRequest();
        req.setVideoIds(videoIds);
        DeleteVideoResponse res = null;
        try {
            res = vodClient.getAcsResponse(req);
            LOGGER.info("删除redis，key = {}", videoIds);
            iRedisService.del(videoIds);
        } catch (ServerException e) {
            e.printStackTrace();
            throw new RuntimeException("DeleteVideoRequest Server failed");
        } catch (ClientException e) {
            e.printStackTrace();
            throw new RuntimeException("DeleteVideoRequest Client failed");
        }
        LOGGER.info("删除视频信息,response={}", JSON.toJSONString(res));
        return res;
    }

    /**
     * 阿里云oss方式上传文件 获取安全令牌
     *
     * @return
     */
    @Override
    public AssumeRoleResponse getToken() {
        final AssumeRoleRequest request = new AssumeRoleRequest();
        request.setVersion("2015-04-01");
        request.setMethod(MethodType.POST);
        request.setProtocol(ProtocolType.HTTPS);
        request.setDurationSeconds(3600L);
        request.setRoleArn(ossConfigure.getRoleArn());
        request.setRoleSessionName("file-token");
        AssumeRoleResponse res = null;
        try {
            res = client.getAcsResponse(request);
        } catch (ServerException e) {
            e.printStackTrace();
            throw new RuntimeException("AssumeRoleRequest Server failed");
        } catch (ClientException e) {
            e.printStackTrace();
            throw new RuntimeException("AssumeRoleRequest Client failed");
        }
        LOGGER.info("获取安全令牌,credentials={}", JSON.toJSONString(res));
        return res;
    }

    /**
     * 阿里云 消息服务 创建队列
     *
     * @param queueName
     * @param pollingWaitSeconds
     * @param maxMessageSize
     * @return
     */
    @Override
    public CloudQueue createQueue(String queueName, Integer pollingWaitSeconds, Long maxMessageSize) {
        QueueMeta meta = new QueueMeta();
        meta.setQueueName(queueName);  // 设置队列名
        meta.setPollingWaitSeconds(pollingWaitSeconds);
        meta.setMaxMessageSize(maxMessageSize);
        CloudQueue queue = null;
        try {
            queue = mnsClient.createQueue(meta);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("queue Server failed");
        }
        // 程序退出时，需主动调用client的close方法进行资源释放
        // mnsClient.close();
        return queue;
    }

    /**
     * 阿里云 消息服务 接收和删除消息(从队列中取出并删除该条消息。)
     *
     * @param queueName
     * @return
     */
    @Override
    public Message consumeMessage(String queueName) {
        Message popMsg = null;
        try {
            CloudQueue queue = mnsClient.getQueueRef(queueName);
            popMsg = queue.popMessage();
            if (popMsg != null){
                LOGGER.info("message handle: " + popMsg.getReceiptHandle());
                LOGGER.info("message body: " + popMsg.getMessageBodyAsString());
                LOGGER.info("message id: " + popMsg.getMessageId());
                LOGGER.info("message dequeue count:" + popMsg.getDequeueCount());
                //删除已经取出消费的消息
                queue.deleteMessage(popMsg.getReceiptHandle());
                LOGGER.info("delete message successfully.");
                LOGGER.info("popMsg={}", popMsg.toString());
            }
            else{
                LOGGER.info("message not exist in TestQueue.");
            }
        } catch (ServiceException se) {
            se.printStackTrace();
            LOGGER.error("MNS exception requestId:" + se.getRequestId(), se);
            if (se.getErrorCode() != null) {
                if (se.getErrorCode().equals("QueueNotExist")) {
                    LOGGER.error("Queue is not exist.Please create before use");
                } else if (se.getErrorCode().equals("TimeExpired")) {
                    LOGGER.error("The request is time expired. Please check your local machine timeclock");
                }
            }
        } catch (Exception e) {
            LOGGER.error("Unknown exception happened!");
            e.printStackTrace();
        }
        return popMsg;
    }

    /**
     * 阿里云 媒体转码 查询媒体工作流
     *
     * @param mediaWorkflowId
     * @return
     */
    @Override
    public QueryMediaWorkflowListResponse MediaWorkflowIds(String mediaWorkflowId) {
        QueryMediaWorkflowListRequest req = new QueryMediaWorkflowListRequest();
        req.setMediaWorkflowIds(mediaWorkflowId);
        QueryMediaWorkflowListResponse res = null;
        try {
            res = client.getAcsResponse(req);
        } catch (ServerException e) {
            e.printStackTrace();
            throw new RuntimeException("QueryMediaWorkflowListRequest Server failed");
        } catch (ClientException e) {
            e.printStackTrace();
            throw new RuntimeException("QueryMediaWorkflowListRequest Client failed");
        }
        return res;
    }
}
