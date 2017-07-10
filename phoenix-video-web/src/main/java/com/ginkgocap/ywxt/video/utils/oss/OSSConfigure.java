package com.ginkgocap.ywxt.video.utils.oss;


import com.ginkgocap.ywxt.video.utils.PropertiesUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * 阿里云配置对象
 *
 * @author xufeng
 */
public class OSSConfigure {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String accessUrl;
    private String roleArn;

    private String mns_accessKeyId;
    private String mns_accessKeySecret;
    private String mns_endpoint;

    private static OSSConfigure INSTANCE;

    private OSSConfigure() {
        PropertiesUtils properties = new PropertiesUtils("file.oss.properties");
        endpoint = properties.getProperty("oss.endpoint").trim();
        accessKeyId = properties.getProperty("oss.accessKeyId").trim();
        accessKeySecret = properties.getProperty("oss.accessKeySecret").trim();
        bucketName = properties.getProperty("oss.bucketName").trim();
        accessUrl = properties.getProperty("oss.accessUrl").trim();
        roleArn = properties.getProperty("oss.Arn").trim();
        mns_accessKeyId = properties.getProperty("mns.accessKeyId").trim();
        mns_accessKeySecret = properties.getProperty("mns.accessKeySecret").trim();
        mns_endpoint = properties.getProperty("mns.endpoint").trim();
    }

    public static final OSSConfigure getInstance() {
        if (INSTANCE == null) {
            synchronized (OSSConfigure.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OSSConfigure();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 通过配置文件.properties文件获取，这几项内容。
     *
     * @param storageConfName
     * @throws IOException
     */
    public OSSConfigure(String storageConfName) throws IOException {

        Properties prop = new Properties();
        prop.load(this.getClass().getClassLoader().getResourceAsStream("conf/" + storageConfName));

        endpoint = prop.getProperty("oss.endpoint").trim();
        accessKeyId = prop.getProperty("oss.accessKeyId").trim();
        accessKeySecret = prop.getProperty("oss.accessKeySecret").trim();
        bucketName = prop.getProperty("oss.bucketName").trim();
        accessUrl = prop.getProperty("oss.accessUrl").trim();
        roleArn = prop.getProperty("oss.Arn").trim();
        mns_accessKeyId = prop.getProperty("mns.accessKeyId").trim();
        mns_accessKeySecret = prop.getProperty("mns.accessKeySecret").trim();
        mns_endpoint = prop.getProperty("mns.endpoint").trim();

    }

    public OSSConfigure(String endpoint, String accessKeyId, String accessKeySecret, String bucketName,
                        String accessUrl) {

        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucketName = bucketName;
        this.accessUrl = accessUrl;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public String getRoleArn() {
        return roleArn;
    }

    public void setRoleArn(String roleArn) {
        this.roleArn = roleArn;
    }

    public String getMns_accessKeyId() {
        return mns_accessKeyId;
    }

    public void setMns_accessKeyId(String mns_accessKeyId) {
        this.mns_accessKeyId = mns_accessKeyId;
    }

    public String getMns_accessKeySecret() {
        return mns_accessKeySecret;
    }

    public void setMns_accessKeySecret(String mns_accessKeySecret) {
        this.mns_accessKeySecret = mns_accessKeySecret;
    }

    public String getMns_endpoint() {
        return mns_endpoint;
    }

    public void setMns_endpoint(String mns_endpoint) {
        this.mns_endpoint = mns_endpoint;
    }
}
