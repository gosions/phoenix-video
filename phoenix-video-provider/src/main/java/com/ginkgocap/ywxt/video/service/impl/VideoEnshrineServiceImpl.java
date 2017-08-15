package com.ginkgocap.ywxt.video.service.impl;

import com.ginkgocap.ywxt.user.model.User;
import com.ginkgocap.ywxt.user.service.UserService;
import com.ginkgocap.ywxt.video.dao.VideoEnshrineDao;
import com.ginkgocap.ywxt.video.model.TbVideoEnshrine;
import com.ginkgocap.ywxt.video.service.VideoEnshrineService;
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
@Service("videoEnshrineService")
public class VideoEnshrineServiceImpl implements VideoEnshrineService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private VideoEnshrineDao videoEnshrineDao;

    @Autowired
    private UserService userService;

    @Value("${nginx.root}")
    private String nginxRoot;

    @Override
    public TbVideoEnshrine insertVideoEnshrine(TbVideoEnshrine tbVideoEnshrine) {
        return videoEnshrineDao.insertVideoEnshrine(tbVideoEnshrine);
    }

    @Override
    public TbVideoEnshrine selectByPrimaryKey(Long id) {
        return videoEnshrineDao.selectByPrimaryKey(id);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return videoEnshrineDao.deleteByPrimaryKey(id);
    }

    @Override
    public TbVideoEnshrine selectByUserIdAndVideoId(Long userId, Long videoId) {
        return videoEnshrineDao.selectByUserIdAndVideoId(userId, videoId);
    }

    @Override
    public Map<String, Object> selectAllByVideoId(Long videoId, int currentPage, int pageSize) {
        long count = videoEnshrineDao.selectAllByVideoIdCount(videoId);
        PageUtil page = new PageUtil((int)count,currentPage,pageSize);
        List<TbVideoEnshrine> list = videoEnshrineDao.selectAllByVideoId(videoId, page.getPageStartRow(), pageSize);
        for (TbVideoEnshrine temp:list) {
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
            list=new ArrayList<TbVideoEnshrine>(pageSize);
        }
        //设置回复内容
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put("page", page);
        result.put("results", list);
        return result;
    }

    @Override
    public Map<String, Object> selectAllByUserId(Long userId, int currentPage, int pageSize) {
        long count = videoEnshrineDao.selectAllByUserIdCount(userId);
        PageUtil page = new PageUtil((int)count,currentPage,pageSize);
        List<TbVideoEnshrine> list = videoEnshrineDao.selectAllByUserId(userId, page.getPageStartRow(), pageSize);
        if(count<=0){
            list=new ArrayList<TbVideoEnshrine>(pageSize);
        }
        //设置回复内容
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put("page", page);
        result.put("results", list);
        return result;
    }
}
