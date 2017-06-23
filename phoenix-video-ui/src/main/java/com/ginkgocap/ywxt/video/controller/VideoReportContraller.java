package com.ginkgocap.ywxt.video.controller;


import com.alibaba.dubbo.common.json.JSON;
import com.ginkgocap.ywxt.video.model.TbVideoReport;
import com.ginkgocap.ywxt.video.service.VideoReportService;
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
@RequestMapping("/v1/reportVideo")
public class VideoReportContraller extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoReportContraller.class);

    @Resource
    private VideoReportService videoReportService;

    @ApiOperation(value="视频举报", notes="")
    @ApiImplicitParam(name = "tbVideoReport", value = "详细实体TbVideoReport", required = true, dataType = "TbVideoReport")
    @RequestMapping(value = { "/reportVideo.json" }, method = { RequestMethod.PUT })
    public InterfaceResult reportVideoById(@RequestBody TbVideoReport tbVideoReport, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("视频举报,tbVideoDiscuss={}", JSON.json(tbVideoReport));
        TbVideoReport insertVideoReport = videoReportService.insertVideoReport(tbVideoReport);
        if( null != insertVideoReport) {
            return InterfaceResult.getSuccessInterfaceResultInstance(insertVideoReport);
        }
        return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
    }

    @ApiOperation(value="获取视频举报列表", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId", value = "视频id", required = true, dataType  = "Long"),
            @ApiImplicitParam(name = "currentPage", value = "当前页数", required = true, dataType  = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页个数", required = true, dataType  = "int")})
    @RequestMapping(value = { "/{videoId}/{currentPage}/{pageSize}" }, method = { RequestMethod.GET })
    public InterfaceResult getVideos(
            @PathVariable("videoId") Long videoId,
            @PathVariable("currentPage") int currentPage,
            @PathVariable("pageSize") int pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("获取视频举报列表,videoId={},currentPage={},pageSize={}", videoId, currentPage, pageSize);
        Map<String, Object> stringObjectMap = videoReportService.selectAllByVideoId(videoId, currentPage, pageSize);
        return InterfaceResult.getSuccessInterfaceResultInstance(stringObjectMap);
    }
}
