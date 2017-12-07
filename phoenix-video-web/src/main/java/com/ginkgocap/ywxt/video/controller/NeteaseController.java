package com.ginkgocap.ywxt.video.controller;

import com.ginkgocap.ywxt.video.manager.NeteaseManager;
import com.gintong.frame.util.dto.CommonResultCode;
import com.gintong.frame.util.dto.InterfaceResult;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author cinderella
 * @version 2017/12/5
 */
@RestController
@RequestMapping("/v1/netease")
public class NeteaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NeteaseController.class);

    @Resource
    private NeteaseManager neteaseManager;

    @ApiOperation(value = "创建网易云通信ID", notes = "")
    @RequestMapping(value = { "/create/{accId}" }, method = { RequestMethod.GET })
    public InterfaceResult createAccount(@PathVariable("accId") String accId) {
        String account = neteaseManager.createAccount(accId);
        if (null != account) {
            return InterfaceResult.getSuccessInterfaceResultInstance(account);
        }
        return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
    }

}
