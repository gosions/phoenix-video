package com.ginkgocap.ywxt.video.dao.impl;

import com.ginkgocap.ywxt.video.dao.VideoPlayDao;
import com.ginkgocap.ywxt.video.model.TbVideoPlay;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

/**
 * Created by gintong on 2017/5/31.
 */
@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class VideoPlayDaoImplTest {

    @Resource
    protected VideoPlayDao videoPlayDao;

    @Test
    public void selectAllByVideoId() throws Exception {
        List<TbVideoPlay> tbVideoPlays = videoPlayDao.selectAllByVideoId(1L, 1, 10);
        //PageHelper.Page<TbVideoPlay> tbVideoPlayPage = videoPlayDao.selectAllPage(1L, 1, 10);
        System.out.print(1);
    }

    @Test
    public void selectAllByVideoIdCount() throws Exception {
        Long aLong = videoPlayDao.selectAllByVideoIdCount(1L);
        System.out.println("---"+aLong);
    }

    @Test
    public void insertVideo() throws Exception {
        TbVideoPlay tbVideoPlay = new TbVideoPlay();
        tbVideoPlay.setVideoId(1L);
        tbVideoPlay.setUserId(3L);
        tbVideoPlay.setPlayDuration(86400L);

        TbVideoPlay tbVideoPlay1 = videoPlayDao.insertVideoPlay(tbVideoPlay);

        System.out.print(tbVideoPlay1.toString());
    }

    @Test
    public void selectByPrimaryKey() {
        Long id = 1L;
        TbVideoPlay tbVideoPlay = videoPlayDao.selectByPrimaryKey(id);
        System.out.print(tbVideoPlay.toString());
    }

}