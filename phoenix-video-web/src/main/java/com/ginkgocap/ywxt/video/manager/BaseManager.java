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
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
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

    protected HttpPost getHeader(final String apiUrl, final String contentType) {
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
        httpPost.addHeader("Content-Type", contentType);

        return httpPost;
    }

    protected String doPost(final HttpPost httpPost) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        LOGGER.info(result);
        return result;

    }

    /**
     * 获取属性名数组
     * */
    private String[] getFiledName(Object o){
        Field[] fields=o.getClass().getDeclaredFields();
        String[] fieldNames=new String[fields.length];
        for(int i=0;i<fields.length;i++){
            System.out.println(fields[i].getType());
            fieldNames[i]=fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 根据属性名获取属性值
     * */
    private Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    /**
     * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     * */
    protected List<NameValuePair> getFiledsInfo(Object o){
        Field[] fields=o.getClass().getDeclaredFields();
        List<NameValuePair> list = new ArrayList(16);
        for(int i = 0; i < fields.length; i++){
            list.add(new BasicNameValuePair(fields[i].getName(), getFieldValueByName(fields[i].getName(), o) + ""));
        }
        return list;
    }
}
