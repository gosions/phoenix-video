package com.ginkgocap.ywxt.video.service.impl;

import com.ginkgocap.ywxt.video.service.VideoDiscussService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by gintong on 2017/6/7.
 */
@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
@TransactionConfiguration(transactionManager = "videoTransactionManager")
public class VideoDiscussServiceImplTest {

    @Resource
    VideoDiscussService videoDiscussService;

    @Test
    public void selectAllByVideoId() throws Exception {
        videoDiscussService.selectAllByVideoId(11L,1,5);
    }

}