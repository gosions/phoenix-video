package com.ginkgocap.ywxt.video.controller;

import com.ginkgocap.ywxt.video.model.TbVideoLike;
import com.ginkgocap.ywxt.video.service.VideoLikeService;
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
import java.util.Map;

/**
 * Created by gintong on 2017/6/6.
 */
@RestController
@RequestMapping("/v1/likeVideo")
public class VideoLikeController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoLikeController.class);

    @Resource
    private VideoLikeService videoLikeService;

    @ApiOperation(value = "视频点赞", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId", value = "视频id", required = true, dataType  = "Long", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "点赞用户id", required = true, dataType  = "Long", paramType = "path")})
    @RequestMapping(value = { "/likeVideo/{videoId}/{userId}" }, method = { RequestMethod.POST })
    public InterfaceResult likeVideoById(
            @PathVariable("videoId") Long videoId,
            @PathVariable("userId") Long userId,
            HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("视频点赞,videoId={},userId={}", videoId, userId);
        TbVideoLike tbVideo = new TbVideoLike();
        tbVideo.setUserId(videoId);
        tbVideo.setVideoId(userId);
        TbVideoLike tbVideoLike = videoLikeService.insertVideoLike(tbVideo);
        if(null != tbVideoLike) {
            return InterfaceResult.getSuccessInterfaceResultInstance(tbVideoLike);
        }
        return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
    }

    @ApiOperation(value="获取视频点赞列表", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId", value = "视频id", required = true, dataType  = "Long"),
            @ApiImplicitParam(name = "currentPage", value = "当前页数", required = true, dataType  = "int", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "每页个数", required = true, dataType  = "int", paramType = "path")})
    @RequestMapping(value = { "/{videoId}/{currentPage}/{pageSize}" }, method = { RequestMethod.GET })
    public InterfaceResult getVideos(
            @PathVariable("videoId") Long videoId,
            @PathVariable("currentPage") int currentPage,
            @PathVariable("pageSize") int pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("获取视频点赞列表,videoId={},currentPage={},pageSize={}", videoId, currentPage, pageSize);
        Map<String, Object> stringObjectMap = videoLikeService.selectAllByVideoId(videoId, currentPage, pageSize);
        return InterfaceResult.getSuccessInterfaceResultInstance(stringObjectMap);
    }

}
