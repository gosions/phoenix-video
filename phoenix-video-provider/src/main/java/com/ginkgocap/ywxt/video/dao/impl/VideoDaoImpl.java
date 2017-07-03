package com.ginkgocap.ywxt.video.dao.impl;

import com.ginkgocap.ywxt.util.DateFunc;
import com.ginkgocap.ywxt.video.dao.VideoAttachmentDao;
import com.ginkgocap.ywxt.video.dao.VideoDao;
import com.ginkgocap.ywxt.video.model.TbVideo;
import com.ginkgocap.ywxt.video.model.TbVideoAttachment;
import com.ginkgocap.ywxt.video.model.TbVideoPlay;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.BeansException;
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
@Repository("videoDao")
public class VideoDaoImpl extends SqlSessionDaoSupport implements VideoDao{

    private ApplicationContext applicationContext;

    @Resource
    private VideoAttachmentDao videoAttachmentDao;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext=applicationContext;

    }

    @Override
    public TbVideo insertVideo(TbVideo tbVideo) {
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) applicationContext.getBean("sqlSessionFactory");
        Date date = DateFunc.getRegDate();

        if(null != tbVideo.getAttachment()) {
            TbVideoAttachment tbVideoAttachment = videoAttachmentDao.insertVideoAttachment(tbVideo.getAttachment());
            if(null !=tbVideoAttachment) {
                tbVideo.setAttachmentId(tbVideoAttachment.getId());
                tbVideo.setAttachment(tbVideoAttachment);
            }
        }
        if(null != tbVideo.getTransformAttachment()) {
            TbVideoAttachment tbVideoAttachment = videoAttachmentDao.insertVideoAttachment(tbVideo.getTransformAttachment());
            if(null !=tbVideoAttachment) {
                tbVideo.setTransformAttachmentId(tbVideoAttachment.getId());
                tbVideo.setTransformAttachment(tbVideoAttachment);
            }
        }
        tbVideo.setCreateTime(date);
        int insert = getSqlSession().insert("tb_video.insert", tbVideo);
        setSqlSessionFactory(sqlSessionFactory);
        if(insert > 0) {
            return tbVideo;
        }
        return new TbVideo();
    }

    @Override
    public TbVideo selectByPrimaryKey(Long id) {
        final Map<String, Object> mapParam = new HashMap<String, Object>(1);
        mapParam.put("id",id);
        TbVideo tbVideo = getSqlSession().selectOne("tb_video.selectByPrimaryKey", mapParam);
        if(null != tbVideo){
            if(null != tbVideo.getAttachmentId()){
                TbVideoAttachment attachment = videoAttachmentDao.selectByPrimaryKey(tbVideo.getAttachmentId());
                tbVideo.setAttachment(attachment);
            }
            if(null != tbVideo.getTransformAttachmentId()){
                TbVideoAttachment attachment = videoAttachmentDao.selectByPrimaryKey(tbVideo.getTransformAttachmentId());
                tbVideo.setTransformAttachment(attachment);
            }
            return tbVideo;
        }
        return new TbVideo();
    }

    @Override
    public List<TbVideo> selectByPrimary() {
        return null;
    }

    @Override
    public TbVideo updateVideo(TbVideo tbVideo) {
        int update = getSqlSession().update("tb_video.updateByPrimaryKey", tbVideo);
        if(update > 0) {
            if(null != tbVideo.getAttachment()) {
                videoAttachmentDao.updateByPrimaryKey(tbVideo.getAttachment());
            }
            return selectByPrimaryKey(tbVideo.getId());
        }
        return new TbVideo();
    }

    @Override
    public Long selectSearchCount(Map<String, Object> searchParams) {
        return getSqlSession().selectOne("tb_video.selectSearchCount", searchParams);
    }

    @Override
    public List<TbVideo> selectSearch(Map<String, Object> searchParams) {
        List<TbVideo> list = getSqlSession().selectList("tb_video.selectSearch", searchParams);
        for (TbVideo temp:list) {
            if(null != temp.getAttachmentId()){
                TbVideoAttachment attachment = videoAttachmentDao.selectByPrimaryKey(temp.getAttachmentId());
                temp.setAttachment(attachment);
            }
            if(null != temp.getTransformAttachmentId()){
                TbVideoAttachment attachment = videoAttachmentDao.selectByPrimaryKey(temp.getTransformAttachmentId());
                temp.setTransformAttachment(attachment);
            }
        }
        return list;
    }

}
