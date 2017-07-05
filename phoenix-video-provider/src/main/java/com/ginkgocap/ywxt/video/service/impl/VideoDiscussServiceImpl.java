package com.ginkgocap.ywxt.video.service.impl;

import com.ginkgocap.ywxt.user.model.User;
import com.ginkgocap.ywxt.user.service.UserService;
import com.ginkgocap.ywxt.video.dao.VideoDiscussDao;
import com.ginkgocap.ywxt.video.model.TbVideoDiscuss;
import com.ginkgocap.ywxt.video.service.VideoDiscussService;
import com.ginkgocap.ywxt.video.utils.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gintong on 2017/5/25.
 */
@Service("videoDiscussService")
public class VideoDiscussServiceImpl implements VideoDiscussService {

    private final Logger logger = LoggerFactory.getLogger(VideoDiscussServiceImpl.class);

    @Autowired
    private VideoDiscussDao videoDiscussDao;

    @Autowired
    private UserService userService;

    @Value("${nginx.root}")
    private String nginxRoot;


    @Override
    public TbVideoDiscuss insertVideoDiscuss(TbVideoDiscuss tbVideoDiscuss) {
        return videoDiscussDao.insertVideoDiscuss(tbVideoDiscuss);
    }

    @Override
    public TbVideoDiscuss selectByPrimaryKey(Long id) {
        return videoDiscussDao.selectByPrimaryKey(id);
    }

    @Override
    public Map<String, Object> selectAllByVideoId(Long videoId, int currentPage, int pageSize) {
        logger.info("获取视频评论列表,videoId={},currentPage={},pageSize={}", videoId, currentPage, pageSize);
        long count = videoDiscussDao.selectAllByVideoIdCount(videoId);
        PageUtil page = new PageUtil((int)count,currentPage,pageSize);

        List<TbVideoDiscuss> list = videoDiscussDao.selectAllByVideoId(videoId, page.getPageStartRow(), pageSize);
        for (TbVideoDiscuss temp:list) {
            if(null != temp.getUserId()) {
                User user = userService.findUserByUserId(temp.getUserId());
                if(null != user) {
                    if(null != user.getPicPath()) {
                        user.setPicPath(nginxRoot + user.getPicPath());
                    }
                    temp.setUser(user);
                }
            }
        }
        if(count<=0){
            list=new ArrayList<TbVideoDiscuss>(pageSize);
        }
        //设置回复内容
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put("page", page);
        result.put("results", list);
        return result;
    }
}
