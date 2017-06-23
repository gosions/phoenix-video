package com.ginkgocap.ywxt.video.dao.impl;

import com.ginkgocap.ywxt.util.DateFunc;
import com.ginkgocap.ywxt.video.dao.VideoDao;
import com.ginkgocap.ywxt.video.model.TbVideo;
import com.ginkgocap.ywxt.video.model.TbVideoLike;
import com.ginkgocap.ywxt.video.util.Utils;
import com.ginkgocap.ywxt.video.dao.VideoLikeDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
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
@Repository("videoLikeDao")
public class VideoLikeDaoImpl extends SqlSessionDaoSupport implements VideoLikeDao {

    private ApplicationContext applicationContext;

    @Autowired
    private VideoDao videoDao;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext=applicationContext;

    }

    @Override
    public TbVideoLike insertVideoLike(TbVideoLike tbVideo) {
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) applicationContext.getBean("sqlSessionFactory");
        Date date = DateFunc.getRegDate();
        tbVideo.setCreateTime(date);
        int insert = getSqlSession().insert("tb_video_like.insert", tbVideo);
        if( insert > 0) {
            TbVideo primaryKey = videoDao.selectByPrimaryKey(tbVideo.getVideoId());
            primaryKey.setLikeTime(primaryKey.getLikeTime() + 1L);
            videoDao.updateVideo(primaryKey);
        }
        setSqlSessionFactory(sqlSessionFactory);
        return tbVideo;
    }

    @Override
    public TbVideoLike selectByPrimaryKey(Long id) {
        final Map<String, Object> mapParam = new HashMap<String, Object>(1);
        mapParam.put("id",id);
        return getSqlSession().selectOne("tb_video_like.selectByPrimaryKey", mapParam);
    }

    @Override
    public TbVideoLike selectByPrimaryUserIdAndVideoId(Long userId, Long videoId) {
        final Map<String, Object> mapParam = new HashMap<String, Object>(4);
        mapParam.put("userId",userId);
        mapParam.put("videoId",videoId);
        return getSqlSession().selectOne("tb_video_like.selectByPrimaryUserIdAndVideoId", mapParam);
    }

    @Override
    public TbVideoLike updateByPrimaryKey(TbVideoLike tbVideo) {
        //todo
        return null;
    }

    @Override
    public List<TbVideoLike> selectAllByVideoId(Long videoId, int currentPage, int pageSize) {
        final Map<String, Object> mapParam = new HashMap<String, Object>(3);
        mapParam.put("videoId",videoId);
        mapParam.put("startRow",currentPage);
        mapParam.put("pageSize",pageSize);
        return getSqlSession().selectList("tb_video_like.selectAllByVideoId", mapParam);
    }

    @Override
    public long selectAllByVideoIdCount(Long videoId) {
        final Map<String, Object> mapParam = new HashMap<String, Object>(1);
        mapParam.put("videoId",videoId);
        Long count = getSqlSession().selectOne("tb_video_like.selectAllByVideoIdCount", mapParam);
        if(Utils.isNullOrEmpty(count))
            count=new Long(0);
        return count;
    }


}
