package com.ginkgocap.ywxt.video.controller;

import com.alibaba.fastjson.JSON;
import com.ginkgocap.ywxt.video.constant.DeviceTypeEnum;
import com.ginkgocap.ywxt.video.utils.Md5;
import com.ginkgocap.ywxt.video.utils.agora.media.DynamicKey5;
import com.gintong.frame.util.dto.CommonResultCode;
import com.gintong.frame.util.dto.InterfaceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.agora.signal.Signal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author cinderella
 * @version 2017/11/16
 */
@RestController
@RequestMapping("/v1/agora")
public class AgoraMediaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AgoraMediaController.class);

    private final String SIGNALING_KEY_VERSION = "1";

    @Value("${agora.media.android.appID}")
    private String androidAppID;

    @Value("${agora.media.android.appCertificate}")
    private String androidAppCertificate;

    @Value("${agora.media.ios.appID}")
    private String iosAppID;

    @Value("${agora.media.ios.appCertificate}")
    private String iosAppCertificate;

    @RequestMapping(value = { "/generateMediaChannelKey/{deviceType}/{channel}" }, method = { RequestMethod.GET })
    public InterfaceResult generateMediaChannelKey(
            @PathVariable("deviceType") int deviceType,
            @PathVariable("channel") String channel,
            HttpServletRequest request, HttpServletResponse response){
        final int ts = (int)(System.currentTimeMillis()/1000);
        final int r = new Random().nextInt();
        final long uid = 2882341273L;
        final int expiredTs = 0;
        String generateMediaChannelKey = null;
        try {
            if (DeviceTypeEnum.DEVICE_IOS.getKey() == deviceType) {
                generateMediaChannelKey = DynamicKey5.generateMediaChannelKey(iosAppID, iosAppCertificate, channel, ts, r, uid, expiredTs);
            } else if (DeviceTypeEnum.DEVICE_ANDROID.getKey() == deviceType) {
                generateMediaChannelKey = DynamicKey5.generateMediaChannelKey(androidAppID, androidAppCertificate, channel, ts, r, uid, expiredTs);
            } else {
                return InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
            }
        } catch (Exception ex) {
            LOGGER.error("generateMediaChannelKey Exception : {}", ex.getMessage());
        }
        LOGGER.info("generateMediaChannelKey = {}", generateMediaChannelKey);
        return InterfaceResult.getSuccessInterfaceResultInstance(generateMediaChannelKey);
    }

    @RequestMapping(value = { "/getSignalingKey/{deviceType}/{account}" }, method = { RequestMethod.GET })
    public InterfaceResult getSignalingKey(
            @PathVariable("deviceType") int deviceType,
            @PathVariable("account") String account,
            HttpServletRequest request, HttpServletResponse response){
        final int ts = (int)(System.currentTimeMillis()/1000);
        final long expiredTime = ts + 3600 * 60 * 24;
        LOGGER.info("expiredTime : one day");
        String signalingKey = null;
        String appId = androidAppID;
        String appCertificate = androidAppCertificate;
        try {
            StringBuilder signalingKeyBuilder = new StringBuilder();
            signalingKeyBuilder.append(SIGNALING_KEY_VERSION).append(":");
            if (DeviceTypeEnum.DEVICE_IOS.getKey() == deviceType) {
                appId = iosAppID;
                appCertificate = iosAppCertificate;
            }
            LOGGER.info("getSignalingKey appId = {}, appCertificate = {}", appId, appCertificate);
            signalingKeyBuilder.append(appId).append(":").append(expiredTime).append(":")
                    .append(doMD5(account, appId, appCertificate, expiredTime));
            signalingKey = signalingKeyBuilder.toString();
            LOGGER.info("getSignalingKey signalingKey = {}", signalingKey);
        } catch (Exception ex) {
            LOGGER.error("generateMediaChannelKey Exception : {}", ex.getMessage());
        }
        LOGGER.info("getSignalingKey, account = {}, signalingKey = {}", account, signalingKey);
        return InterfaceResult.getSuccessInterfaceResultInstance(signalingKey);
    }

    @RequestMapping(value = { "/login/{deviceType}/{account}/{token}" }, method = { RequestMethod.GET })
    public InterfaceResult login(
            @PathVariable("deviceType") int deviceType,
            @PathVariable("token") String token,
            @PathVariable("account") final String account,
            HttpServletRequest request, HttpServletResponse response){
        final CountDownLatch loginLatch = new CountDownLatch(1);
        String appId = androidAppID;
        if (DeviceTypeEnum.DEVICE_IOS.getKey() == deviceType) {
            appId = iosAppID;
        }
        Signal.LoginSession loginSession = null;
        try {
            Signal sig = new Signal(appId);
            sig.setDoLog(true);
            loginSession = sig.login(account, token, new Signal.LoginCallback() {
                @Override
                public void onLoginSuccess(final Signal.LoginSession session, int uid) {
                    super.onLoginSuccess(session, uid);
                    LOGGER.info("{} login success", account);
                    loginLatch.countDown();
                }

                @Override
                public void onLoginFailed(Signal.LoginSession session, int ecode) {
                    super.onLoginFailed(session, ecode);
                    LOGGER.info("{} login fail", account);
                    loginLatch.countDown();
                }

                @Override
                public void onError(Signal.LoginSession session, int ecode, String reason) {
                    super.onError(session, ecode, reason);
                    LOGGER.error("{} login error", account);
                    loginLatch.countDown();
                }

                @Override
                public void onLogout(Signal.LoginSession session, int ecode) {
                    super.onLogout(session, ecode);
                    LOGGER.info("{} logout success", account);
                    loginLatch.countDown();
                }
            });
            loginLatch.await();
            LOGGER.info("login loginSession = {}", JSON.toJSONString(loginSession));
        } catch (Exception ex) {
            LOGGER.error("login Exception : {}", ex.getMessage());
        }
        return InterfaceResult.getSuccessInterfaceResultInstance(loginSession);
    }

    private String doMD5(final String account, final String appId , final String appCertificate , final long expiredTime) {
        StringBuilder stringBuilder = new StringBuilder()
                .append(account).append(appId).append(appCertificate).append(expiredTime);
        return Md5.md5(stringBuilder.toString());
    }

}
