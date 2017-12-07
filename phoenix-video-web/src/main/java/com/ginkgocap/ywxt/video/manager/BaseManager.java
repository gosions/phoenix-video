package com.ginkgocap.ywxt.video.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ginkgocap.ywxt.video.constant.MediaTypes;
import com.ginkgocap.ywxt.video.utils.netease.CheckSumBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.List;

/**
 * @author cinderella
 * @version 2017/12/5
 */
public class BaseManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseManager.class);

    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 7000;
    private static final int MAX_POOL_TOTAL = 100;

    @Value("${netease.appKey}")
    private String appKey;

    @Value("${netease.appSecret}")
    private String appSecret;

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(MAX_POOL_TOTAL);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        // 在提交请求之前 测试连接是否可用
        configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }

    protected HttpPost getHeader(final String apiUrl) {
        HttpPost httpPost = new HttpPost(apiUrl);
        httpPost.setConfig(requestConfig);

        final String curTime = String.valueOf(System.currentTimeMillis() / 1000L);
        final String nonce =  curTime;
        final String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", MediaTypes.NETEASE_UTF_8);

        return httpPost;
    }

    protected String doPost(HttpPost httpPost, List<NameValuePair> nvps) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        LOGGER.info(result);
        Integer code = (Integer)JSON.parseObject(result).get("code");
        if (200 == code) {
            return result;
        }
        LOGGER.error("\n201 -- 客户端版本不对，需升级sdk\n" +
                "301 -- 被封禁\n" +
                "302 -- 用户名或密码错误\n" +
                "315 -- IP限制\n" +
                "403 -- 非法操作或没有权限\n" +
                "404 -- 对象不存在\n" +
                "405 -- 参数长度过长\n" +
                "406 -- 对象只读\n" +
                "408 -- 客户端请求超时\n" +
                "413 -- 验证失败(短信服务)\n" +
                "414 -- 参数错误\n" +
                "415 -- 客户端网络问题\n" +
                "416 -- 频率控制\n" +
                "417 -- 重复操作\n" +
                "418 -- 通道不可用(短信服务)\n" +
                "419 -- 数量超过上限\n" +
                "422 -- 账号被禁用\n" +
                "431 -- HTTP重复请求\n" +
                "500 -- 服务器内部错误\n" +
                "503 -- 服务器繁忙\n" +
                "508 -- 消息撤回时间超限\n" +
                "509 -- 无效协议\n" +
                "514 -- 服务不可用\n" +
                "998 -- 解包错误\n" +
                "999 -- 打包错误");
        return null;

    }
}
