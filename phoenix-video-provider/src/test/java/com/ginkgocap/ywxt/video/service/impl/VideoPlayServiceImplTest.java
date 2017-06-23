package com.ginkgocap.ywxt.video.service.impl;

import com.ginkgocap.ywxt.video.model.TbVideoPlay;
import com.ginkgocap.ywxt.video.service.VideoPlayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;

import java.util.Map;

/**
 * Created by gintong on 2017/5/31.
 */
@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
@TransactionConfiguration(transactionManager = "videoTransactionManager")
public class VideoPlayServiceImplTest {

    @Resource
    VideoPlayService videoPlayService;

    @Test
    public void selectAllByVideoId() throws Exception {
       Map<String, Object> stringObjectMap = videoPlayService.selectAllByVideoId(1L, 2, 2);
       // Page<TbVideoPlay> tbVideoPlayPage = videoPlayService.selectAllPage(1L, 1, 2);
        System.out.println(1);
    }

}