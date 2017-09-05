package com.ginkgocap.ywxt.video.controller;

import com.alibaba.dubbo.common.json.JSON;
import com.ginkgocap.ywxt.track.entity.constant.BusinessModelEnum;
import com.ginkgocap.ywxt.track.entity.constant.MethodTypeEnum;
import com.ginkgocap.ywxt.track.entity.constant.OptTypeEnum;
import com.ginkgocap.ywxt.track.entity.constant.ServerTypeEnum;
import com.ginkgocap.ywxt.track.entity.model.TbBusinessTrack;
import com.ginkgocap.ywxt.track.entity.util.IPUtils;
import com.ginkgocap.ywxt.video.model.TbVideoPlay;
import com.ginkgocap.ywxt.video.service.VideoPlayService;
import com.gintong.frame.util.dto.CommonResultCode;
import com.gintong.frame.util.dto.InterfaceResult;
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
import java.util.Map;

/**
 * Created by gintong on 2017/6/6.
 */
@RestController
@RequestMapping("/v1/playVideo")
public class VideoPlayController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoPlayController.class);

    private static final Logger TRACK_LOGGER = LoggerFactory.getLogger("trackLog");

    @Resource
    private VideoPlayService videoPlayService;

    @ApiOperation(value="视频播放", notes="")
    @ApiImplicitParam(name = "tbVideoPlay", value = "详细实体TbVideoPlay", required = true, dataType = "TbVideoPlay")
    @RequestMapping(value = { "/playVideo.json" }, method = { RequestMethod.PUT })
    public InterfaceResult playVideoById(@RequestBody TbVideoPlay tbVideoPlay, HttpServletRequest request, HttpServletResponse response){
        LOGGER.info("视频播放,tbVideoDiscuss={}", null == tbVideoPlay ? "" : tbVideoPlay.toString());
        TbVideoPlay insertVideoPlay = videoPlayService.insertVideoPlay(tbVideoPlay);
        if( null != insertVideoPlay) {
            try {
                TbBusinessTrack tbBusinessTrack = new TbBusinessTrack();
                tbBusinessTrack.setBusinessModel(BusinessModelEnum.BUSINESS_VIDEO.getKey());
                tbBusinessTrack.setBusinessModelId(tbVideoPlay.getVideoId());
                tbBusinessTrack.setOptType(OptTypeEnum.OPT_PLAY.getKey());
                tbBusinessTrack.setServerType(ServerTypeEnum.SERVICE_INTERFACE.getKey());
                tbBusinessTrack.setClientIp(IPUtils.getRemoteAddr(request));
                tbBusinessTrack.setUrl(request.getRequestURL().toString() + (request.getQueryString() == null ? "" : ("?" + request.getQueryString())));
                tbBusinessTrack.setMethodType(MethodTypeEnum.REQUEST_METHOD_PUT.getKey());
                tbBusinessTrack.setUserAgent(request.getHeader("User-Agent"));
                tbBusinessTrack.setParameter(tbVideoPlay.toString());
                tbBusinessTrack.setGmtCreate(new Timestamp(new Date().getTime()));
                tbBusinessTrack.setUserId(tbVideoPlay.getUserId());
                TRACK_LOGGER.info(tbBusinessTrack.toString());
            }catch (Exception e){
                LOGGER.error("GET TRACK_LOGGER EXCEPTION, {},{}", e.getMessage(), e);
            }
            return InterfaceResult.getSuccessInterfaceResultInstance(insertVideoPlay);
        }
        return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
    }

    @ApiOperation(value="获取视频播放列表", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId", value = "视频id", required = true, dataType  = "Long", paramType = "path"),
            @ApiImplicitParam(name = "currentPage", value = "当前页数", required = true, dataType  = "int", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "每页个数", required = true, dataType  = "int", paramType = "path")})
    @RequestMapping(value = { "/{videoId}/{currentPage}/{pageSize}" }, method = { RequestMethod.GET })
    public InterfaceResult getVideos(
            @PathVariable("videoId") Long videoId,
            @PathVariable("currentPage") int currentPage,
            @PathVariable("pageSize") int pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("获取视频播放列表,videoId={},currentPage={},pageSize={}", videoId, currentPage, pageSize);
        Map<String, Object> stringObjectMap = videoPlayService.selectAllByVideoId(videoId, currentPage, pageSize);
        return InterfaceResult.getSuccessInterfaceResultInstance(stringObjectMap);
    }

}
