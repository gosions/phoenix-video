package com.ginkgocap.ywxt.video.file;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.mts.model.v20140618.*;
import com.aliyuncs.profile.DefaultProfile;

/**
 * Created by gintong on 2017/5/26.
 */
public class OSSFileDemo {

    /**
     * 阿里云ACCESS_ID
     */
    private static String ACCESS_ID = "ACCESS_ID";
    /**
     * 阿里云ACCESS_KEY
     */
    private  static String accessKeySecret = "ACCESS_KEY";
    /**
     * 阿里云OSS_ENDPOINT  青岛Url
     */
    private static String OSS_ENDPOINT = "http://oss-cn-qingdao.aliyuncs.com";

    /**
     * 阿里云BUCKET_NAME  OSS
     */
    private static String BUCKET_NAME = "demo10";

    DefaultProfile profile = DefaultProfile.getProfile(
            "cn-beijing",
            ACCESS_ID,
            accessKeySecret);

    static {
        try {
            DefaultProfile.addEndpoint("cn-beijing",
                    "cn-beijing",
                    "Mts",
                    "mts.cn-beijing.aliyuncs.com");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    DefaultAcsClient client = new DefaultAcsClient(profile);

    //提交工作流
    SearchMediaWorkflowResponse searchMediaWorkflow(DefaultAcsClient client) {
        SearchMediaWorkflowRequest request = new SearchMediaWorkflowRequest();
        SearchMediaWorkflowResponse response = null;
        try {
            response = client.getAcsResponse(request);
        } catch (ServerException e) {
            throw new RuntimeException("SearchMediaWorkflowRequest Server failed");
        } catch (ClientException e) {
            throw new RuntimeException("SearchMediaWorkflowRequest Client failed");
        }
        return response;
    }

    //媒体库绑定输入Bucket。
    BindInputBucketResponse bindInputBucketResponse(DefaultAcsClient client) {
        BindInputBucketRequest request = new BindInputBucketRequest();
        request.setBucket("video9187210");//设置Bucket
        BindInputBucketResponse response = null;
        try {
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return  response;
    }

    //提交截图作业
    SubmitSnapshotJobResponse submitSnapshotJobResponse(DefaultAcsClient client) {
        SubmitSnapshotJobRequest request = new SubmitSnapshotJobRequest();
        return null;
    }

    //提交转码
    SubmitJobsResponse submitJobsResponse(DefaultAcsClient client) {
        SubmitJobsRequest request = new SubmitJobsRequest();
        request.setAcceptFormat(FormatType.JSON);//
        request.setInput("");
        request.setOutputs("");
        request.setOutputBucket("");
        request.setPipelineId("");
        SubmitJobsResponse response = null;
        try {
            response = client.getAcsResponse(request);
            response.getJobResultList();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void toTest(){
        DefaultProfile profile = DefaultProfile.getProfile(
                "cn-hangzhou",
                "accessKeyId",
                "accessKeySecret");
        DefaultAcsClient client = new DefaultAcsClient(profile);
        SubmitJobsResponse response = submitJobsResponse(client);


    }

}
