package com.ginkgocap.ywxt.video.controller;


import com.alibaba.dubbo.common.json.JSON;
import com.ginkgocap.ywxt.video.model.TbVideoShare;
import com.ginkgocap.ywxt.video.service.VideoShareService;
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
import java.util.Map;

/**
 * Created by gintong on 2017/6/6.
 */
@RestController
@RequestMapping("/v1/shareVideo")
public class VideoShareController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoShareController.class);

    @Resource
    private VideoShareService videoShareService;

    @ApiOperation(value="视频分享", notes="")
    @ApiImplicitParam(name = "tbVideoShare", value = "详细实体TbVideoShare", required = true, dataType = "TbVideoShare")
    @RequestMapping(value = { "/tbVideoShare.json" }, method = { RequestMethod.PUT })
    public InterfaceResult shareVideoById(@RequestBody TbVideoShare tbVideoShare, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("视频分享,tbVideoShare={}", JSON.json(tbVideoShare));
        TbVideoShare insertVideoShare = videoShareService.insertVideoShare(tbVideoShare);
        if( null != insertVideoShare) {
            return InterfaceResult.getSuccessInterfaceResultInstance(insertVideoShare);
        }
        return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
    }

    @ApiOperation(value="视频分享举报列表", notes="")
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
        LOGGER.info("获取视频分享列表,videoId={},currentPage={},pageSize={}", videoId, currentPage, pageSize);
        Map<String, Object> stringObjectMap = videoShareService.selectAllByVideoId(videoId, currentPage, pageSize);
        return InterfaceResult.getSuccessInterfaceResultInstance(stringObjectMap);
    }
}
