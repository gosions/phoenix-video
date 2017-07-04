package com.ginkgocap.ywxt.video.dao.impl;

import com.ginkgocap.ywxt.user.model.User;
import com.ginkgocap.ywxt.user.service.UserService;
import com.ginkgocap.ywxt.util.DateFunc;
import com.ginkgocap.ywxt.video.dao.VideoDao;
import com.ginkgocap.ywxt.video.dao.VideoEnshrineDao;
import com.ginkgocap.ywxt.video.model.TbVideoEnshrine;
import com.ginkgocap.ywxt.video.util.Utils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gintong on 2017/5/25.
 */
@Repository("videoEnshrineDao")
public class VideoEnshrineDaoImpl extends SqlSessionDaoSupport implements VideoEnshrineDao {

    private ApplicationContext applicationContext;

    @Resource
    private VideoDao videoDao;

    @Autowired
    private UserService userService;

    @Value("${nginx.root}")
    private String nginxRoot;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext=applicationContext;

    }

    @Override
    public TbVideoEnshrine insertVideoEnshrine(TbVideoEnshrine tbVideo) {
        //存在即不插入
        TbVideoEnshrine tbVideoEnshrine = selectByUserIdAndVideoId(tbVideo.getUserId(), tbVideo.getVideoId());
        if(null != tbVideoEnshrine) {
            return tbVideoEnshrine;
        }
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) applicationContext.getBean("sqlSessionFactory");
        Date date = DateFunc.getRegDate();
        tbVideo.setCreateTime(date);
        int insert = getSqlSession().insert("tb_video_enshrine.insert", tbVideo);
        setSqlSessionFactory(sqlSessionFactory);
        return tbVideo;
    }

    @Override
    public TbVideoEnshrine selectByPrimaryKey(Long id) {
        final Map<String, Object> mapParam = new HashMap<String, Object>(1);
        mapParam.put("id",id);
        return getSqlSession().selectOne("tb_video_enshrine.selectByPrimaryKey", mapParam);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        final Map<String, Object> mapParam = new HashMap<String, Object>(1);
        mapParam.put("id",id);
        return getSqlSession().delete("tb_video_enshrine.deleteByPrimaryKey", mapParam);
    }

    @Override
    public TbVideoEnshrine selectByUserIdAndVideoId(Long userId, Long videoId) {
        final Map<String, Object> mapParam = new HashMap<String, Object>(2);
        mapParam.put("userId", userId);
        mapParam.put("videoId", videoId);
        return getSqlSession().selectOne("tb_video_enshrine.selectByUserIdAndVideoId", mapParam);
    }

    @Override
    public TbVideoEnshrine updateByPrimaryKey(TbVideoEnshrine tbVideo) {
        //todo
        return null;
    }

    @Override
    public List<TbVideoEnshrine> selectAllByVideoId(Long videoId, int currentPage, int pageSize) {
        final Map<String, Object> mapParam = new HashMap<String, Object>(3);
        mapParam.put("videoId",videoId);
        mapParam.put("startRow",currentPage);
        mapParam.put("pageSize",pageSize);
        return getSqlSession().selectList("tb_video_enshrine.selectAllByVideoId", mapParam);
    }

    @Override
    public long selectAllByVideoIdCount(Long videoId) {
        final Map<String, Object> mapParam = new HashMap<String, Object>(1);
        mapParam.put("videoId",videoId);
        Long count = getSqlSession().selectOne("tb_video_enshrine.selectAllByVideoIdCount", mapParam);
        if(Utils.isNullOrEmpty(count))
            count=new Long(0);
        return count;
    }

    @Override
    public List<TbVideoEnshrine> selectAllByUserId(Long userId, int currentPage, int pageSize) {
        final Map<String, Object> mapParam = new HashMap<String, Object>(3);
        mapParam.put("userId",userId);
        mapParam.put("startRow",currentPage);
        mapParam.put("pageSize",pageSize);
        List<TbVideoEnshrine> list = getSqlSession().selectList("tb_video_enshrine.selectAllByUserId", mapParam);
        for (TbVideoEnshrine temp:list) {
            temp.setVideo(videoDao.selectByPrimaryKey(temp.getVideoId()));
            if(null != temp.getVideo() && null != temp.getVideo().getUserId()) {
                User user = userService.findUserByUserId(temp.getVideo().getUserId());
                if(null != user) {
                    user.setPicPath(nginxRoot + user.getPicPath());
                    temp.getVideo().setUser(user);
                }
            }
        }
        return list;
    }

    @Override
    public long selectAllByUserIdCount(Long userId) {
        final Map<String, Object> mapParam = new HashMap<String, Object>(1);
        mapParam.put("userId",userId);
        Long count = getSqlSession().selectOne("tb_video_enshrine.selectAllByUserIdCount", mapParam);
        if(Utils.isNullOrEmpty(count))
            count=new Long(0);
        return count;
    }


}
