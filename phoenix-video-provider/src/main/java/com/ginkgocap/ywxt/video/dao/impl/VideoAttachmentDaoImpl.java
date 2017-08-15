package com.ginkgocap.ywxt.video.dao.impl;

import com.ginkgocap.ywxt.util.DateFunc;
import com.ginkgocap.ywxt.video.dao.VideoAttachmentDao;
import com.ginkgocap.ywxt.video.model.TbVideoAttachment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gintong on 2017/5/25.
 */
@Repository("videoAttachmentDao")
public class VideoAttachmentDaoImpl extends SqlSessionDaoSupport implements VideoAttachmentDao {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext=applicationContext;

    }

    @Override
    public TbVideoAttachment insertVideoAttachment(TbVideoAttachment tbVideoAttachment) {
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) applicationContext.getBean("sqlSessionFactory");
        Date date = DateFunc.getRegDate();
        tbVideoAttachment.setCreateTime(date);
        int insert = getSqlSession().insert("tb_video_attachment.insert", tbVideoAttachment);
        setSqlSessionFactory(sqlSessionFactory);
        return tbVideoAttachment;
    }

    @Override
    public TbVideoAttachment selectByPrimaryKey(Long id) {
        final Map<String, Object> mapParam = new HashMap<String, Object>(1);
        mapParam.put("id",id);
        return getSqlSession().selectOne("tb_video_attachment.selectByPrimaryKey", mapParam);
    }

    @Override
    public TbVideoAttachment updateByPrimaryKey(TbVideoAttachment tbVideoAttachment) {
        int update = getSqlSession().update("tb_video_attachment.updateByPrimaryKey", tbVideoAttachment);
        if(update > 0) {
            return tbVideoAttachment;
        }
        return new TbVideoAttachment();
    }
}
