package com.ginkgocap.ywxt.video.dao.impl;

import com.ginkgocap.ywxt.util.DateFunc;
import com.ginkgocap.ywxt.video.dao.VideoDao;
import com.ginkgocap.ywxt.video.dao.VideoPlayDao;
import com.ginkgocap.ywxt.video.model.TbVideo;
import com.ginkgocap.ywxt.video.model.TbVideoPlay;
import com.ginkgocap.ywxt.video.util.Utils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gintong on 2017/5/25.
 */
@Repository("videoPlayDao")
public class VideoPlayDaoImpl extends SqlSessionDaoSupport implements VideoPlayDao {

    private final Logger logger = LoggerFactory.getLogger(VideoPlayDaoImpl.class);

    private ApplicationContext applicationContext;

    @Autowired
    private VideoDao videoDao;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext=applicationContext;

    }

    @Override
    public TbVideoPlay insertVideoPlay(TbVideoPlay tbVideoPlay) {
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) applicationContext.getBean("sqlSessionFactory");
        Date date = DateFunc.getRegDate();
        tbVideoPlay.setCreateTime(date);
        int insert = getSqlSession().insert("tb_video_play.insert", tbVideoPlay);
        if(insert > 0) {
            TbVideo tbVideo = videoDao.selectByPrimaryKey(tbVideoPlay.getVideoId());
            tbVideo.setPlayTime(tbVideo.getPlayTime() + 1L);
            videoDao.updateVideo(tbVideo);
        }
        setSqlSessionFactory(sqlSessionFactory);
        return tbVideoPlay;
    }

    @Override
    public TbVideoPlay selectByPrimaryKey(Long id) {
        final Map<String, Object> mapParam = new HashMap<String, Object>(1);
        mapParam.put("id",id);
        return getSqlSession().selectOne("tb_video_play.selectByPrimaryKey", mapParam);
    }

    @Override
    public TbVideoPlay updateByPrimaryKey(TbVideoPlay tbVideoPlay) {
        //todo
        return null;
    }

    @Override
    public List<TbVideoPlay> selectAllByVideoId(Long videoId, int currentPage, int pageSize) {
        final Map<String, Object> mapParam = new HashMap<String, Object>(3);
        mapParam.put("videoId",videoId);
        mapParam.put("startRow",currentPage);
        mapParam.put("pageSize",pageSize);
        logger.info("获取视频评论列表,mapParam={}", mapParam.toString());
        return getSqlSession().selectList("tb_video_play.selectAllByVideoId", mapParam);
    }

    @Override
    public long selectAllByVideoIdCount(Long videoId) {
        final Map<String, Object> mapParam = new HashMap<String, Object>(1);
        mapParam.put("videoId",videoId);
        Long count = (Long)getSqlSession().selectOne("tb_video_play.selectAllByVideoIdCount", mapParam);
        if(Utils.isNullOrEmpty(count))
            count=new Long(0);
        return count;
    }


}
