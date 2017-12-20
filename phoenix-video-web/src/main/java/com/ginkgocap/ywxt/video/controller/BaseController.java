package com.ginkgocap.ywxt.video.controller;

import com.ginkgocap.ywxt.video.utils.JsonReadUtil;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by gintong on 2017/6/5.
 */
@Controller
public class BaseController {

    /**
     * 获取head中的json参数串
     *
     * @param request
     * @return result
     * @throws IOException
     *
     */
    protected String getJsonParamStr(HttpServletRequest request) throws IOException {
        String result = JsonReadUtil.getJsonIn(request);
        return result;
    }

    protected String getMicLinkKey(final long meetingId) {
        return "meeting_live_" + meetingId;
    }

}
