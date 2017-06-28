package com.ginkgocap.ywxt.video.controller;


import com.alibaba.dubbo.common.json.JSON;
import com.ginkgocap.ywxt.video.model.TbVideoDiscuss;
import com.ginkgocap.ywxt.video.service.VideoDiscussService;
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
@RequestMapping("/v1/discussVideo")
public class VideoDiscussContraller extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoDiscussContraller.class);

    @Resource
    private VideoDiscussService videoDiscussService;

    @ApiOperation(value="视频评论", notes="")
    @ApiImplicitParam(name = "tbVideoDiscuss", value = "详细实体TbVideoDiscuss", required = true, dataType = "TbVideoDiscuss")
    @RequestMapping(value = { "/discussVideo.json" }, method = { RequestMethod.PUT })
    public InterfaceResult discussVideoById(@RequestBody TbVideoDiscuss tbVideoDiscuss, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("视频评论,tbVideoDiscuss={}", JSON.json(tbVideoDiscuss));
        TbVideoDiscuss insertVideoDiscuss = videoDiscussService.insertVideoDiscuss(tbVideoDiscuss);
        if( null != insertVideoDiscuss) {
            return InterfaceResult.getSuccessInterfaceResultInstance(insertVideoDiscuss);
        }
        return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
    }

    @ApiOperation(value="获取视频评论列表", notes="")
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
        LOGGER.info("获取视频评论列表,videoId={},currentPage={},pageSize={}", videoId, currentPage, pageSize);
        Map<String, Object> stringObjectMap = videoDiscussService.selectAllByVideoId(videoId, currentPage, pageSize);
        return InterfaceResult.getSuccessInterfaceResultInstance(stringObjectMap);
    }
}
