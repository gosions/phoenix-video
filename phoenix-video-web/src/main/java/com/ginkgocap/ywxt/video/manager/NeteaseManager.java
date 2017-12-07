package com.ginkgocap.ywxt.video.manager;

import com.ginkgocap.ywxt.video.constant.NeteaseImApiUrl;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cinderella
 * @version 2017/12/5
 */
@Component
@Transactional(propagation = Propagation.NEVER, rollbackFor = Exception.class)
public class NeteaseManager extends BaseManager{

    private static final Logger LOGGER = LoggerFactory.getLogger(NeteaseManager.class);

    /**
     * 创建网易云通信ID
     * @param accId
     * @return
     */
    public String createAccount(final String accId) {
        HttpPost httpPost = getHeader(NeteaseImApiUrl.CREATE_ACCOUNT);
        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("accid", accId));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            return doPost(httpPost, nvps);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建聊天室
     * @param creator
     * @param roomName
     * @return
     */
    public String createChatRoom(final String creator, final String roomName) {
        HttpPost httpPost = getHeader(NeteaseImApiUrl.CREATE_CHAT_ROOM);
        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("creator", creator));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            return doPost(httpPost, nvps);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
