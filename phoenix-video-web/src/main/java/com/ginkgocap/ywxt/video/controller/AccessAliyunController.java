package com.ginkgocap.ywxt.video.controller;


import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.model.Message;
import com.aliyuncs.mts.model.v20140618.QueryMediaWorkflowListResponse;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.aliyuncs.vod.model.v20170321.*;
import com.ginkgocap.ywxt.video.model.TbVideo;
import com.ginkgocap.ywxt.video.service.AccessAliyunService;
import com.ginkgocap.ywxt.video.service.IRedisService;
import com.ginkgocap.ywxt.video.utils.IPUtils;
import com.ginkgocap.ywxt.video.utils.JSONUtil;
import com.ginkgocap.ywxt.video.utils.validation.ValidationResult;
import com.ginkgocap.ywxt.video.utils.validation.ValidationUtils;
import com.gintong.frame.util.dto.CommonResultCode;
import com.gintong.frame.util.dto.InterfaceResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by gintong on 2017/6/9.
 */
@RestController
@RequestMapping("/v1/access")
public class AccessAliyunController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessAliyunController.class);

    @Resource
    private AccessAliyunService accessAliyunService;

    @Resource
    private IRedisService iRedisService;

    @ApiIgnore
    @ApiOperation(value = "获取安全令牌", notes = "OSS方式")
    @RequestMapping(value = { "/getToken" }, method = { RequestMethod.GET })
    public InterfaceResult getToken(HttpServletRequest request, HttpServletResponse response){
        AssumeRoleResponse token = accessAliyunService.getToken();
        return InterfaceResult.getSuccessInterfaceResultInstance(token);
    }

    @ApiIgnore
    @ApiOperation(value = "创建队列", notes = "")
    @RequestMapping(value = { "/createQueue/{queueName}/{pollingWaitSeconds}/{maxMessageSize}" }, method = { RequestMethod.GET })
    public InterfaceResult createQueue(
            @PathVariable("queueName") String queueName,
            @PathVariable("pollingWaitSeconds") Integer pollingWaitSeconds,
            @PathVariable("maxMessageSize") Long maxMessageSize,
            HttpServletRequest request, HttpServletResponse response) {
        CloudQueue queue = accessAliyunService.createQueue(queueName, pollingWaitSeconds, maxMessageSize);
        return InterfaceResult.getSuccessInterfaceResultInstance(queue);
    }

    @ApiIgnore
    @ApiOperation(value = "接收和删除消息", notes = "从队列中取出并删除该条消息。")
    @RequestMapping(value = { "/consume/{queueName}" }, method = { RequestMethod.GET })
    public InterfaceResult consume(@PathVariable("queueName") String queueName, HttpServletRequest request, HttpServletResponse response) {
        Message message = accessAliyunService.consumeMessage(queueName);
        return InterfaceResult.getSuccessInterfaceResultInstance(message);
    }

    @ApiIgnore
    @ApiOperation(value = "查询媒体工作流", notes = "")
    @RequestMapping(value = { "/mediaWorkflowIds/{mediaWorkflowId}" }, method = { RequestMethod.GET })
    public InterfaceResult MediaWorkflowIds(@PathVariable("mediaWorkflowId") String mediaWorkflowId, HttpServletRequest request, HttpServletResponse response) {
        QueryMediaWorkflowListResponse res = accessAliyunService.MediaWorkflowIds(mediaWorkflowId);
        return InterfaceResult.getSuccessInterfaceResultInstance(res);
    }

/*    @ApiIgnore
    @ApiOperation(value = "消息主題", notes = "")
    @RequestMapping(value = { "/cloudTopic/{cloudTopic}" }, method = { RequestMethod.GET })
    public InterfaceResult cloudTopic(@PathVariable("cloudTopic") String cloudTopic, HttpServletRequest request, HttpServletResponse response) {
        CloudTopic topicRef = mnsClient.getTopicRef(cloudTopic);
        try {
            TopicMeta attribute = topicRef.getAttribute();
        } catch (Exception e) {
            throw new RuntimeException("QueryMediaWorkflowListRequest Server failed");
        }
        return InterfaceResult.getSuccessInterfaceResultInstance(topicRef.getAttribute());
    }*/

 /*   @ApiOperation(value = "提交转码", notes = "OSS方式")
    @RequestMapping(value = { "/submitJobs" }, method = { RequestMethod.POST })
    public InterfaceResult submitJobs(HttpServletRequest request, HttpServletResponse response) {
        //提交转码
        SubmitJobsRequest req = new SubmitJobsRequest();
        req.setAcceptFormat(FormatType.JSON);//

        Map<String,String> params = new HashMap<String,String>(3);
        params.put("Bucket", ossConfigure.getBucketName());
        params.put("Location", "oss-cn-beijing");
        params.put("Object", "");
        String input = AlipayCore.createBizContentValue(params);

        req.setInput(input);
        req.setOutputBucket("gintong-video-002");
        req.setOutputLocation("oss-cn-beijing");
        req.setPipelineId("mts-service-pipeline");
        SubmitJobsResponse res = null;
        try {
            res = client.getAcsResponse(req);
            res.getJobResultList();
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return InterfaceResult.getSuccessInterfaceResultInstance(null);
    }*/

/*    @ApiOperation(value = "播放鉴权", notes = "OSS方式,媒体转码后")
    @RequestMapping(value = { "/playAuthInfo" }, method = { RequestMethod.POST })
    public InterfaceResult playAuthInfo(HttpServletRequest request, HttpServletResponse response) {
        PlayerAuthRequest req = new PlayerAuthRequest();
        PlayerAuthResponse res = null;
        ShaHmac1 shaHmac1 = new ShaHmac1();
       // String signString = shaHmac1.signString();
        try {
            res = client.getAcsResponse(req);
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return InterfaceResult.getSuccessInterfaceResultInstance(null);
    }*/

    @ApiOperation(value = "获取播放凭证", notes = "视频点播方式")
    @ApiImplicitParam(name = "id", value = "阿里云的视频id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = { "/getVideoPlayAuth/{id}" }, method = { RequestMethod.GET })
    public InterfaceResult getVideoPlayAuth(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {
        GetVideoPlayAuthResponse videoPlayAuth = accessAliyunService.getVideoPlayAuth(id);
        return InterfaceResult.getSuccessInterfaceResultInstance(videoPlayAuth);
    }

    @ApiOperation(value = "获取视频播放地址", notes = "视频点播方式")
    @ApiImplicitParam(name = "id", value = "阿里云的视频id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = { "/getPlayInfo/{id}" }, method = { RequestMethod.GET })
    public InterfaceResult getPlayInfo(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {
        try{
            GetPlayInfoResponse playInfo = accessAliyunService.GetPlayInfo(id);
            return InterfaceResult.getSuccessInterfaceResultInstance(playInfo);
        }catch (Exception e){
            LOGGER.error("获取视频播放地址,{}",e);
        }
        return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
    }

    @ApiOperation(value = "获取视频信息", notes = "视频点播方式")
    @ApiImplicitParam(name = "id", value = "阿里云的视频id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = { "/getVideoInfo/{id}" }, method = { RequestMethod.GET })
    public InterfaceResult getVideoInfo(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {
        String s = iRedisService.get(id);
        LOGGER.info("先从redis中获取，s={}",s);
        if(null == s) {
            GetVideoInfoResponse videoInfo = accessAliyunService.getVideoInfo(id);
            s = JSONUtil.toJson(videoInfo);
            iRedisService.set(id, s, 1 * 24 * 60 * 60);
        }
        return InterfaceResult.getSuccessInterfaceResultInstance(JSONUtil.toBean(s, GetVideoInfoResponse.class));
    }

    @ApiOperation(value = "获取视频上传凭证和地址", notes = "视频点播方式")
    @RequestMapping(value = { "/createUploadVideo" }, method = { RequestMethod.POST })
    public InterfaceResult createUploadVideo(@RequestBody TbVideo tbVideo, HttpServletRequest request, HttpServletResponse response) {
        try {
            //校验参数合法性
            ValidationResult validationResult = ValidationUtils.validateEntity(tbVideo);
            if (validationResult.isHasErrors()) {
                return InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION, validationResult);
            }
            if (null != tbVideo.getAttachment()) {
                validationResult = ValidationUtils.validateEntity(tbVideo.getAttachment());
                if (validationResult.isHasErrors()) {
                    return InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION, validationResult);
                }
            }

            //获取ip地址
            String ip = IPUtils.getRemoteAddr(request);
            CreateUploadVideoResponse uploadVideo = accessAliyunService.createUploadVideo(tbVideo, ip);
            return InterfaceResult.getSuccessInterfaceResultInstance(uploadVideo);

        } catch (Exception e) {
            LOGGER.error("获取视频上传凭证和地址异常,{}",e);
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
        }
    }

    @ApiOperation(value = "刷新视频上传凭证", notes = "视频点播方式,用于视频文件上传超时后重新获取上传凭证。")
    @ApiImplicitParam(name = "id", value = "阿里云的视频id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = { "/refreshUploadVideo/{id}" }, method = { RequestMethod.POST })
    public InterfaceResult RefreshUploadVideo(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {
        RefreshUploadVideoResponse refreshUploadVideoResponse = accessAliyunService.refreshUploadVideo(id);
        return InterfaceResult.getSuccessInterfaceResultInstance(refreshUploadVideoResponse);
    }




}
