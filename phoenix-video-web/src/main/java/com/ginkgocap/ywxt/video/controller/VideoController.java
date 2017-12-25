package com.ginkgocap.ywxt.video.controller;

import com.alibaba.dubbo.common.json.JSON;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoResponse;
import com.ginkgocap.ywxt.track.entity.constant.BusinessModelEnum;
import com.ginkgocap.ywxt.track.entity.constant.MethodTypeEnum;
import com.ginkgocap.ywxt.track.entity.constant.OptTypeEnum;
import com.ginkgocap.ywxt.track.entity.constant.ServerTypeEnum;
import com.ginkgocap.ywxt.track.entity.model.TbBusinessTrack;
import com.ginkgocap.ywxt.track.entity.util.BusinessTrackUtils;
import com.ginkgocap.ywxt.track.entity.util.IPUtils;
import com.ginkgocap.ywxt.util.DateFunc;
import com.ginkgocap.ywxt.video.constant.MediaTypes;
import com.ginkgocap.ywxt.video.constant.VideoStatusEnum;
import com.ginkgocap.ywxt.video.dto.AuditingForbiddenDTO;
import com.ginkgocap.ywxt.video.dto.VideoDTO;
import com.ginkgocap.ywxt.video.model.*;
import com.ginkgocap.ywxt.video.service.*;
import com.ginkgocap.ywxt.video.utils.QueryReqBean;
import com.ginkgocap.ywxt.video.utils.validation.ValidationResult;
import com.ginkgocap.ywxt.video.utils.validation.ValidationUtils;
import com.gintong.frame.util.dto.CommonResultCode;
import com.gintong.frame.util.dto.InterfaceResult;
import com.google.common.base.Strings;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gintong on 2017/6/5.
 */
@RestController
@RequestMapping("/v1/video")
public class VideoController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoController.class);

    private static final Logger TRACK_LOGGER = LoggerFactory.getLogger("trackLog");

    @Resource
    private VideoService videoService;

    @Resource
    private AccessAliyunService accessAliyunService;

    @ApiOperation(value="创建视频", notes="根据TbVideo对象创建视频")
    @ApiImplicitParam(name = "tbVideo", value = "详细实体TbVideo", required = true, dataType = "TbVideo")
    @RequestMapping(value = { "/addVideo.json" }, method = { RequestMethod.PUT })
    public InterfaceResult addVideo(@RequestBody TbVideo tbVideo, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("创建视频,tbVideo={}", JSON.json(tbVideo));
        try {
            if (null == tbVideo) {
                return InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_NULL_EXCEPTION);
            }
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
            TbVideo insertVideo = videoService.insertVideo(tbVideo);
            if (null != insertVideo) {
                BusinessTrackUtils.addTbBusinessTrackLog4AddOpt(LOGGER, TRACK_LOGGER, BusinessModelEnum.BUSINESS_VIDEO.getKey(),
                        insertVideo.getId(), null, request, tbVideo.getUserId(), null);
                return InterfaceResult.getSuccessInterfaceResultInstance(insertVideo);
            }
        } catch (Exception e) {
            LOGGER.error("创建视频异常,{}", e);
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
        }
        return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
    }

    @ApiOperation(value="获取视频列表", notes="根据参数条件分页获取")
    @ApiImplicitParam(name = "queryReqBean", value = "请求参数", required = true, dataType  = "QueryReqBean")
    @RequestMapping(value = { "/getVideos.json" }, method = { RequestMethod.POST }, produces = MediaTypes.JSON_UTF_8)
    public InterfaceResult getVideos(@RequestBody QueryReqBean queryReqBean, HttpServletRequest request, HttpServletResponse response){
        LOGGER.info("获取视频列表,queryReqBean={}", queryReqBean.toString());
        try {
            //校验参数合法性
            ValidationResult validationResult = ValidationUtils.validateEntity(queryReqBean);
            if (validationResult.isHasErrors()) {
                return InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION, validationResult);
            }
            validationResult = ValidationUtils.validateEntity(queryReqBean.getPageParameter());
            if (validationResult.isHasErrors()) {
                return InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION, validationResult);
            }
            Map<String, Object> result = videoService.selectSearchPage(queryReqBean);
            //获取阿里云的视频信息
            List<TbVideo> list = (List<TbVideo>)result.get("results");
            for (TbVideo temp:list) {
                if(null != temp.getAttachmentId() && null != temp.getAttachment()) {
                    try {
                        temp.getAttachment().setAliyunVideo(accessAliyunService.getVideoInfo(temp.getAttachment().getAliyunVideoId()).getVideo());
                    } catch (Exception e) {
                        LOGGER.error("从阿里云获取视频信息异常，{},{}", e.getMessage(), e);
                    }
                }
            }
            return InterfaceResult.getSuccessInterfaceResultInstance(result);
        } catch (Exception e) {
            LOGGER.error("获取视频列表异常，{}",e);
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
        }
    }

    @ApiOperation(value = "查询视频信息", notes = "返回视频的详细信息")
    @ApiImplicitParam(name = "id", value = "视频id", required = true, dataType  = "Long", paramType = "path")
    @RequestMapping(value = { "/getVideo/{id}" }, method = { RequestMethod.GET }, produces = MediaTypes.JSON_UTF_8)
    public InterfaceResult getVideoById(@PathVariable("id") Long id,
                                        HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("id={},获取视频", id);
        try {
            Map<String, Object> result = new HashMap<>(2);
            GetVideoInfoResponse.Video videoInfo = null;
            TbVideo tbVideo = videoService.selectByPrimaryKey(id);
            result.put("tbVideo", tbVideo);
            if (null != tbVideo && null != tbVideo.getAttachment() && null != tbVideo.getAttachment().getAliyunVideoId()) {
                try {
                    videoInfo = accessAliyunService.getVideoInfo(tbVideo.getAttachment().getAliyunVideoId()).getVideo();
                    tbVideo.getAttachment().setAliyunVideo(videoInfo);
                } catch (Exception e) {
                    LOGGER.error("从阿里云获取视频信息异常，{}",e);
                }
            }
            BusinessTrackUtils.addTbBusinessTrackLog4ViewOpt(LOGGER, TRACK_LOGGER, BusinessModelEnum.BUSINESS_VIDEO.getKey(),
                    id, null, request, null, null);
            return InterfaceResult.getSuccessInterfaceResultInstance(result);
        } catch (Exception e) {
            LOGGER.error("获取视频异常，{}",e);
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
        }
    }

    @ApiOperation(value = "查询视频信息", notes = "返回视频的详细信息,包含是否关注组织信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "视频id", required = true, dataType  = "Long", paramType = "path"),
            @ApiImplicitParam(name = "personId", value = "登录用户id", required = true, dataType  = "Long", paramType = "path")})
    @RequestMapping(value = { "/getVideo/{id}/{personId}" }, method = { RequestMethod.GET }, produces = MediaTypes.JSON_UTF_8)
    public InterfaceResult getVideoByIdAndpersonId(
            @PathVariable("id") Long id,
            @PathVariable("personId") Long personId,
            HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("id={},获取视频", id);
        try {
            Map<String, Object> result = new HashMap<>(2);
            GetVideoInfoResponse.Video videoInfo = null;
            TbVideo tbVideo = videoService.selectByPrimaryKeyAndPersonId(id, personId);
            result.put("tbVideo", tbVideo);
            if (null != tbVideo && null != tbVideo.getAttachment() && null != tbVideo.getAttachment().getAliyunVideoId()) {
                try {
                    videoInfo = accessAliyunService.getVideoInfo(tbVideo.getAttachment().getAliyunVideoId()).getVideo();
                    tbVideo.getAttachment().setAliyunVideo(videoInfo);
                } catch (Exception e) {
                    LOGGER.error("从阿里云获取视频信息异常，{}",e);
                }
            }
            BusinessTrackUtils.addTbBusinessTrackLog4ViewOpt(LOGGER, TRACK_LOGGER, BusinessModelEnum.BUSINESS_VIDEO.getKey(),
                    id, null, request, personId, null);
            return InterfaceResult.getSuccessInterfaceResultInstance(result);
        } catch (Exception e) {
            LOGGER.error("获取视频异常，{}",e);
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
        }
    }

    @ApiOperation(value = "视频审核通过", notes = "视频状态由为审核变为正常")
    @ApiImplicitParam(name = "id", value = "视频id", required = true, dataType  = "Long", paramType = "path")
    @RequestMapping(value = { "/auditingPass/{id}" }, method = { RequestMethod.POST })
    public InterfaceResult videoAuditingPass(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("id={},视频审核通过",id);
        TbVideo tbVideo = videoService.selectByPrimaryKey(id);
        if(null == tbVideo) {
            LOGGER.error("视频审核通过,视频不存在,id={}",id);
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
        } else if(VideoStatusEnum.NO_AUDIT.getKey() != tbVideo.getStatus()) {
            LOGGER.error("视频审核通过,视频状态异常，只有未审核的才能审核！,id={}",id);
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
        } else {
            GetVideoInfoResponse.Video videoInfo = null;
            if (null != tbVideo && null != tbVideo.getAttachment() && null != tbVideo.getAttachment().getAliyunVideoId()) {
                try {
                    videoInfo = accessAliyunService.getVideoInfo(tbVideo.getAttachment().getAliyunVideoId()).getVideo();
                } catch (Exception e) {
                    LOGGER.error("从阿里云获取视频信息异常，{}",e);
                }
            }
            if( null != videoInfo) {
                tbVideo.setDuration(videoInfo.getDuration());//时长
            }
            tbVideo.setStatus(VideoStatusEnum.PASS_AUDIT.getKey());
            TbVideo updateVideo = videoService.updateVideo(tbVideo);
            if(null != updateVideo) {
                return InterfaceResult.getSuccessInterfaceResultInstance(tbVideo);
            }
        }
        return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
    }

    @ApiOperation(value = "视频审核驳回", notes = "视频状态由为审核变为审核驳回")
    @ApiImplicitParam(name = "id", value = "视频id", required = true, dataType  = "Long", paramType = "path")
    @RequestMapping(value = { "/auditingRebut/{id}" }, method = { RequestMethod.POST })
    public InterfaceResult videoAuditingRebut(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("id={},视频审核驳回",id);
        TbVideo tbVideo = videoService.selectByPrimaryKey(id);
        if(null == tbVideo) {
            LOGGER.error("视频审核驳回,视频不存在,id={}",id);
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
        } else if(VideoStatusEnum.NO_AUDIT.getKey() != tbVideo.getStatus()) {
            LOGGER.error("视频审核驳回,视频状态异常，只有未审核的才能审核！,id={}",id);
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
        } else {
            tbVideo.setStatus(VideoStatusEnum.REBUT_AUDIT.getKey());
            TbVideo updateVideo = videoService.updateVideo(tbVideo);
            if(null != updateVideo) {
                return InterfaceResult.getSuccessInterfaceResultInstance(tbVideo);
            }
        }
        return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
    }

    @ApiOperation(value = "视频审核禁用", notes = "视频状态由正常变为禁用")
    @ApiImplicitParam(name = "auditingForbiddenDTO", value = "实体DTO", required = true, dataType  = "AuditingForbiddenDTO")
    @RequestMapping(value = { "/auditingForbidden" }, method = { RequestMethod.POST })
    public InterfaceResult videoAuditingForbidden(@RequestBody AuditingForbiddenDTO auditingForbiddenDTO, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("id={},视频审核禁用",auditingForbiddenDTO.getId());
        TbVideo tbVideo = videoService.selectByPrimaryKey(auditingForbiddenDTO.getId());
        if(null == tbVideo) {
            LOGGER.error("视频审核禁用,视频不存在,id={}",auditingForbiddenDTO.getId());
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
        } else if(VideoStatusEnum.PASS_AUDIT.getKey() != tbVideo.getStatus()) {
            LOGGER.error("视频审核禁用,视频状态异常，只有审核正常的才能禁用！,id={}",auditingForbiddenDTO.getId());
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
        } else if(Strings.isNullOrEmpty(auditingForbiddenDTO.getForbiddenReason())) {
            LOGGER.error("缺少禁用原因！,id={}",auditingForbiddenDTO.getId());
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
        } else{
            tbVideo.setStatus(VideoStatusEnum.FORBIDDEN_AUDIT.getKey());
            tbVideo.setForbiddenReason(auditingForbiddenDTO.getForbiddenReason());
            TbVideo updateVideo = videoService.updateVideo(tbVideo);
            if(null != updateVideo) {
                return InterfaceResult.getSuccessInterfaceResultInstance(tbVideo);
            }
        }
        return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
    }

    @ApiOperation(value = "视频审核启用", notes = "视频状态由禁用变为正常")
    @ApiImplicitParam(name = "id", value = "视频id", required = true, dataType  = "Long", paramType = "path")
    @RequestMapping(value = { "/auditingNormal/{id}" }, method = { RequestMethod.POST })
    public InterfaceResult videoAuditingNormal(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("id={},视频审核启用",id);
        TbVideo tbVideo = videoService.selectByPrimaryKey(id);
        if(null == tbVideo) {
            LOGGER.error("视频审核启用,视频不存在,id={}",id);
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
        } else if(VideoStatusEnum.FORBIDDEN_AUDIT.getKey() != tbVideo.getStatus()) {
            LOGGER.error("视频审核启用,视频状态异常，只有审核禁用的才能启用！,id={}",id);
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
        } else {
            tbVideo.setStatus(VideoStatusEnum.PASS_AUDIT.getKey());
            TbVideo updateVideo = videoService.updateVideo(tbVideo);
            if(null != updateVideo) {
                return InterfaceResult.getSuccessInterfaceResultInstance(tbVideo);
            }
        }
        return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
    }

    @ApiOperation(value = "视频审核置顶", notes = "非置顶的视频置顶，置顶的视频取消置顶")
    @ApiImplicitParam(name = "id", value = "视频id", required = true, dataType  = "Long", paramType = "path")
    @RequestMapping(value = { "/auditingTop/{id}" }, method = { RequestMethod.POST })
    public InterfaceResult videoAuditingTop(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("id={},视频审核置顶",id);
        Date date = DateFunc.getRegDate();
        TbVideo tbVideo = videoService.selectByPrimaryKey(id);
        if(null == tbVideo) {
            LOGGER.error("视频审核置顶,视频不存在,id={}",id);
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
        } else if(0 == tbVideo.getTop()) {
            LOGGER.info("视频审核置顶！,id={}",id);
            tbVideo.setTop(1);
            tbVideo.setTopTime(date);
            TbVideo updateVideo = videoService.updateVideo(tbVideo);
            if(null != updateVideo) {
                return InterfaceResult.getSuccessInterfaceResultInstance(tbVideo);
            }
        } else if(1 == tbVideo.getTop()) {
            LOGGER.info("视频审核取消置顶！,id={}",id);
            tbVideo.setTop(0);
            tbVideo.setTopTime(date);
            TbVideo updateVideo = videoService.updateVideo(tbVideo);
            if(null != updateVideo) {
                return InterfaceResult.getSuccessInterfaceResultInstance(tbVideo);
            }
        }
        return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
    }

    @ApiOperation(value = "删除视频信息", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "视频id", required = true, dataType  = "Long", paramType = "path")
    @RequestMapping(value = { "/deleteVideo/{id}" }, method = { RequestMethod.DELETE })
    public InterfaceResult VideoById(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("id={},删除视频",id);
        try {
            TbVideo tbVideo = videoService.selectByPrimaryKey(id);
            if (null == tbVideo) {
                LOGGER.error("删除视频,视频不存在,id={}", id);
                return InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
            }
            tbVideo.setStatus(VideoStatusEnum.DELETE_AUDIT.getKey());
            TbVideo updateVideo = videoService.updateVideo(tbVideo);
            //阿里云同步删除
            try{
                accessAliyunService.deleteVideo(tbVideo.getAttachment().getAliyunVideoId());
            }catch (Exception e) {
                LOGGER.error("阿里云同步删除异常,{}",e);
            }
            if (null != updateVideo) {
                return InterfaceResult.getSuccessInterfaceResultInstance(tbVideo);
            }
        } catch (Exception e) {
            LOGGER.error("删除视频异常，{}", e);
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
        }
        return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
    }

    @ApiOperation(value = "编辑视频", notes = "编辑视频封面、标题、描述、标签等")
    @ApiImplicitParam(name = "videoDTO", value = "详细实体videoDTO", required = true, dataType = "VideoDTO")
    @RequestMapping(value = { "/editVideo.json" }, method = { RequestMethod.POST })
    public InterfaceResult editVideo(@RequestBody VideoDTO videoDTO, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("编辑视频", JSON.json(videoDTO));
        //校验参数合法性
        ValidationResult validationResult = ValidationUtils.validateEntity(videoDTO);
        if(validationResult.isHasErrors()) {
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION, validationResult);
        }
        TbVideo tbVideo = videoService.selectByPrimaryKey(videoDTO.getVideoId());
        if(null == tbVideo) {
            LOGGER.error("视频不存在");
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
        }
        //同步更新阿里云的 视频详情
        accessAliyunService.updateVideoInfo(videoDTO);
        GetVideoInfoResponse videoInfo = accessAliyunService.getVideoInfo(videoDTO.getAliyunVideoId());
        tbVideo.setTag(videoDTO.getTag());//视频标签
        tbVideo.setTitle(videoDTO.getTitle());//视频标题
        tbVideo.setDescription(videoDTO.getDescription());//视频描述
        tbVideo.setDuration(videoInfo.getVideo().getDuration());//视频时长
        TbVideo updateVideo = videoService.updateVideo(tbVideo);
        if(null != updateVideo) {
            return InterfaceResult.getSuccessInterfaceResultInstance(tbVideo);
        }
        return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
    }

}
