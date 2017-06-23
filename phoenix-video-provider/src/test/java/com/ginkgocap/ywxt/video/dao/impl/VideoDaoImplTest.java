package com.ginkgocap.ywxt.video.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ginkgocap.ywxt.video.dao.VideoDao;
import com.ginkgocap.ywxt.video.model.TbVideo;
import com.ginkgocap.ywxt.video.model.TbVideoAttachment;
import com.ginkgocap.ywxt.video.ui.dao.impl.HttpUtils;
import com.ginkgocap.ywxt.video.ui.dao.impl.UserInfoDTO;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by gintong on 2017/5/25.
 */
@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class VideoDaoImplTest {

    @Resource
    protected VideoDao videoDao;

    @Test
    public void insertVideo() throws Exception {
        TbVideo tbVideo = new TbVideo();
        tbVideo.setTitle("33333");
        tbVideo.setDiscussTime(33333L);
        tbVideo.setUserId(33333L);
        tbVideo.setStatus(0);
        tbVideo.setStatus(0);

        TbVideoAttachment tbVideoAttachment = new TbVideoAttachment();
        tbVideoAttachment.setFileName("33333");
        tbVideo.setAttachment(tbVideoAttachment);

        TbVideo insertVideo = videoDao.insertVideo(tbVideo);
        System.out.println(insertVideo.getAttachmentId());

    }

    @Test
    public void inser() throws Exception {
        // 设置参数
        String appid = "wxa8d92f54c4a0e3f6";
        String secret = "ef8da730c4f38a77006d07d7e4d460eb";
        String Code = "081rszls0OSlLc1t0dls0DVtls0rszlI";
        System.out.println("=============================Code:" + Code);
        String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + appid + "&secret=" + secret + "&";
        URL = URL + "code=" + Code + "&grant_type=authorization_code";
        // 获取token对象
        System.out.println("=============================insertweixinUserInfo 获取token URL  "
                + URL);
        String resp = HttpUtils.URLGet(URL, null,
                HttpUtils.URL_PARAM_DECODECHARSET_UTF8);
        System.out.println("=============================insertweixinUserInfo 获取token resp  "
                + resp);
        AccessTokenDTO accessTokenDTO = JSON.parseObject(resp,
                new TypeReference<AccessTokenDTO>() {
                });

        // 获取用户信息
        String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + accessTokenDTO.getAccess_token() + "&openid="
                + accessTokenDTO.getOpenid();
        System.out.println("=============================insertweixinUserInfo 获取userInfo infoUrl  "
                + infoUrl);
        String userInfoString = HttpUtils.URLGet(infoUrl, null,
                HttpUtils.URL_PARAM_DECODECHARSET_UTF8);
        System.out.println("============================= 获取userInfo 返回String  "
                + userInfoString);
        UserInfoDTO userInfoDTO = JSON.parseObject(userInfoString,
                new TypeReference<UserInfoDTO>() {
                });
        if (userInfoDTO == null || userInfoDTO.getNickname() == null) {
            // throw new Exception("获取用户微信信息失败");
            //EnnMember ennMember = ennMemberDao.findById("2");

        }
        String nickname = userInfoDTO.getNickname();
        System.out.println("=============================insertweixinUserInfo before userInfoDTO:"
                + nickname);
        nickname = new String(nickname.getBytes("iso-8859-1"), "UTF-8");
        System.out.println("=============================insertweixinUserInfo after userInfoDTO:"
                + nickname);
        System.out.println("=============================微信获取用户Unioid:"
                + userInfoDTO.getUnionid());

;
    }


}