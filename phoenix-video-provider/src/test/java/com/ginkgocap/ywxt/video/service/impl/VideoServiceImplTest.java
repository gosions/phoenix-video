package com.ginkgocap.ywxt.video.service.impl;

import com.alibaba.dubbo.common.json.JSON;
import com.ginkgocap.ywxt.util.JsonUtil;
import com.ginkgocap.ywxt.video.model.TbVideo;
import com.ginkgocap.ywxt.video.service.VideoService;
import com.ginkgocap.ywxt.video.utils.QueryReqBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by gintong on 2017/5/25.
 */
@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
@TransactionConfiguration(transactionManager = "videoTransactionManager")
public class VideoServiceImplTest {

    @Resource
    VideoService videoService;

    @Test
    public void insertVideo() throws Exception {
       /* TbVideo tbVideo = new TbVideo();
        tbVideo.setTitle("113");
        tbVideo.setUserId(1L);
        TbVideo tbVideo1 = videoService.insertVideo(tbVideo);*/
        String js = "{   \"pageParameter\": {     \"currentPage\": 1,     \"endRow\": 0,     \"pageSize\": 5,     \"startRow\": 0,     \"totalCount\": 0,     \"totalPage\": 0   },   \"searchParams\": {\"statusList\":[2,3]},   \"sortExp\": \"like_time DESC\" }";
        QueryReqBean queryReqBean = JSON.parse(js,QueryReqBean.class);
        Map<String, Object> stringObjectMap = videoService.selectSearchPage(queryReqBean);
        System.out.print(1);
    }


}