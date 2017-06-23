package com.ginkgocap.ywxt.video.dao.impl;

import com.ginkgocap.ywxt.util.DateFunc;
import com.ginkgocap.ywxt.video.dao.VideoReportDao;
import com.ginkgocap.ywxt.video.model.TbVideoReport;
import com.ginkgocap.ywxt.video.util.Utils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gintong on 2017/5/25.
 */
@Repository("videoReportDao")
public class VideoReportDaoImpl extends SqlSessionDaoSupport implements VideoReportDao {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext=applicationContext;

    }

    @Override
    public TbVideoReport insertVideoReport(TbVideoReport tbVideo) {
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) applicationContext.getBean("sqlSessionFactory");
        Date date = DateFunc.getRegDate();
        tbVideo.setCreateTime(date);
        int insert = getSqlSession().insert("tb_video_report.insert", tbVideo);
        setSqlSessionFactory(sqlSessionFactory);
        return tbVideo;
    }

    @Override
    public TbVideoReport selectByPrimaryKey(Long id) {
        final Map<String, Object> mapParam = new HashMap<String, Object>(1);
        mapParam.put("id",id);
        return getSqlSession().selectOne("tb_video_report.selectByPrimaryKey", mapParam);
    }

    @Override
    public TbVideoReport updateByPrimaryKey(TbVideoReport tbVideo) {
        //todo
        return null;
    }

    @Override
    public List<TbVideoReport> selectAllByVideoId(Long videoId, int currentPage, int pageSize) {
        final Map<String, Object> mapParam = new HashMap<String, Object>(3);
        mapParam.put("videoId",videoId);
        mapParam.put("startRow",currentPage);
        mapParam.put("pageSize",pageSize);
        return getSqlSession().selectList("tb_video_report.selectAllByVideoId", mapParam);
    }

    @Override
    public long selectAllByVideoIdCount(Long videoId) {
        final Map<String, Object> mapParam = new HashMap<String, Object>(1);
        mapParam.put("videoId",videoId);
        Long count = getSqlSession().selectOne("tb_video_report.selectAllByVideoIdCount", mapParam);
        if(Utils.isNullOrEmpty(count))
            count=new Long(0);
        return count;
    }


}
