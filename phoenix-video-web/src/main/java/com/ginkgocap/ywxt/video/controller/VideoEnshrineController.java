package com.ginkgocap.ywxt.video.controller;


import com.ginkgocap.ywxt.video.model.TbVideoEnshrine;
import com.ginkgocap.ywxt.video.service.AccessAliyunService;
import com.ginkgocap.ywxt.video.service.VideoEnshrineService;
import com.gintong.frame.util.dto.CommonResultCode;
import com.gintong.frame.util.dto.InterfaceResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by gintong on 2017/6/6.
 */
@RestController
@RequestMapping("/v1/enshrineVideo")
public class VideoEnshrineController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoEnshrineController.class);

    @Resource
    private VideoEnshrineService videoEnshrineService;

    @Resource
    private AccessAliyunService accessAliyunService;

    @ApiOperation(value = "视频收藏", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId", value = "视频id", required = true, dataType  = "Long", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "收藏用户id", required = true, dataType  = "Long", paramType = "path")})
    @RequestMapping(value = { "/getVideo/{videoId}/{userId}" }, method = { RequestMethod.POST })
    public InterfaceResult enshrineVideoById(
            @PathVariable("videoId") long videoId,
            @PathVariable("userId") long userId,
            HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("视频收藏,videoId={},userId={}", videoId, userId);
        TbVideoEnshrine tbVideoEnshrine = new TbVideoEnshrine();
        tbVideoEnshrine.setUserId(userId);
        tbVideoEnshrine.setVideoId(videoId);
        TbVideoEnshrine insertVideoEnshrine = videoEnshrineService.insertVideoEnshrine(tbVideoEnshrine);
        if(null != insertVideoEnshrine) {
            return InterfaceResult.getSuccessInterfaceResultInstance(insertVideoEnshrine);
        }
        return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
    }

    @ApiOperation(value="根据视频id获取视频收藏列表", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId", value = "视频id", required = true, dataType  = "Long", paramType = "path"),
            @ApiImplicitParam(name = "currentPage", value = "当前页数", required = true, dataType  = "int", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "每页个数", required = true, dataType  = "int", paramType = "path")})
    @RequestMapping(value = { "/video/{videoId}/{currentPage}/{pageSize}" }, method = { RequestMethod.GET })
    public InterfaceResult getVideosByVideo(
            @PathVariable("videoId") Long videoId,
            @PathVariable("currentPage") int currentPage,
            @PathVariable("pageSize") int pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("根据视频id获取视频收藏列表,videoId={},currentPage={},pageSize={}", videoId, currentPage, pageSize);
        try {
            Map<String, Object> stringObjectMap = videoEnshrineService.selectAllByVideoId(videoId, currentPage, pageSize);
            return InterfaceResult.getSuccessInterfaceResultInstance(stringObjectMap);
        } catch (Exception e) {
            LOGGER.error("根据视频id获取视频收藏列表异常,{}", e);
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
        }
    }

    @ApiOperation(value="根据收藏用户id获取视频收藏列表", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType  = "Long", paramType = "path"),
            @ApiImplicitParam(name = "currentPage", value = "当前页数", required = true, dataType  = "int", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "每页个数", required = true, dataType  = "int", paramType = "path")})
    @RequestMapping(value = { "/user/{userId}/{currentPage}/{pageSize}" }, method = { RequestMethod.GET })
    public InterfaceResult getVideosByUser(
            @PathVariable("userId") Long userId,
            @PathVariable("currentPage") int currentPage,
            @PathVariable("pageSize") int pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("根据收藏用户id获取视频收藏列表,userId={},currentPage={},pageSize={}", userId, currentPage, pageSize);
        try {
            Map<String, Object> stringObjectMap = videoEnshrineService.selectAllByUserId(userId, currentPage, pageSize);
            List<TbVideoEnshrine> list = (List<TbVideoEnshrine>) stringObjectMap.get("results");
            for (TbVideoEnshrine temp : list) {
                if (null != temp.getVideo() && null != temp.getVideo().getAttachment() && null != temp.getVideo().getAttachment().getAliyunVideoId()) {
                    try {
                        temp.getVideo().getAttachment().setAliyunVideo(accessAliyunService.getVideoInfo(temp.getVideo().getAttachment().getAliyunVideoId()).getVideo());
                    }catch (Exception e) {
                        LOGGER.error("从获取阿里云获取视频信息异常,{}",e);
                    }
                }
            }
            return InterfaceResult.getSuccessInterfaceResultInstance(stringObjectMap);
        } catch (Exception e) {
            LOGGER.error("根据收藏用户id获取视频收藏列表,{}", e);
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
        }
    }

    @ApiOperation(value="收藏的视频取消收藏", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType  = "Long", paramType = "path"),
            @ApiImplicitParam(name = "videoId", value = "视频id", required = true, dataType  = "Long", paramType = "path")})
    @RequestMapping(value = { "/cancelEnshrine/{videoId}/{userId}" }, method = { RequestMethod.POST })
    public InterfaceResult cancelEnshrine(
            @PathVariable("videoId") long videoId,
            @PathVariable("userId") long userId,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            LOGGER.error("用戶取消收藏");
            TbVideoEnshrine tbVideoEnshrine = videoEnshrineService.selectByUserIdAndVideoId(userId, videoId);
            if(null == tbVideoEnshrine) {
                return InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
            }
            int i = videoEnshrineService.deleteByPrimaryKey(tbVideoEnshrine.getId());
            if(i > 0) {
                return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
            } else {
                return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
            }

        } catch (Exception e) {
            LOGGER.error("取消收藏", e);
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
        }
    }
}
