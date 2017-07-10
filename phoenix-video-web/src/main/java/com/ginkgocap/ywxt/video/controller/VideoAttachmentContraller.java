package com.ginkgocap.ywxt.video.controller;

import com.ginkgocap.ywxt.video.model.TbVideoAttachment;
import com.ginkgocap.ywxt.video.service.VideoService;
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
import java.io.IOException;

/**
 * Created by gintong on 2017/6/5.
 */
@ApiIgnore
@RestController
@RequestMapping("/v1/attachment")
public class VideoAttachmentContraller extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoAttachmentContraller.class);

    @Resource
    private VideoService videoService;


    @ApiOperation(value="创建视频附件", notes="根据TbVideoAttachment对象创建视频附件")
    @ApiImplicitParam(name = "tbVideoAttachment", value = "详细实体TbVideoAttachment", required = true, dataType = "TbVideoAttachment")
    @RequestMapping(value = { "/addAttachment.json" }, method = { RequestMethod.PUT })
    public InterfaceResult addAttachment(@RequestBody TbVideoAttachment attachment, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (null != attachment) {
        }
        return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
    }

/*    @ApiOperation(value = "查询视频信息", response = TbVideo.class, responseContainer = "TbVideo")
    @ApiImplicitParam(name = "id", value = "视频id", required = true, dataType  = "Long")
    @RequestMapping(value = { "/getVideo/{id}" }, method = { RequestMethod.GET })
    public InterfaceResult getVideoById(@PathVariable("id") long id,
                                        HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("id={},获取视频",id);
        TbVideo tbVideo = videoService.selectByPrimaryKey(id);
        return InterfaceResult.getSuccessInterfaceResultInstance(tbVideo);
    }*/

}
