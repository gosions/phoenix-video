package com.ginkgocap.ywxt.video.service.impl;

import com.ginkgocap.ywxt.user.model.User;
import com.ginkgocap.ywxt.user.service.UserService;
import com.ginkgocap.ywxt.video.dao.VideoShareDao;
import com.ginkgocap.ywxt.video.model.TbVideoShare;
import com.ginkgocap.ywxt.video.service.VideoShareService;
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
@Service("videoShareService")
public class VideoShareServiceImpl implements VideoShareService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private VideoShareDao videoShareDao;

    @Autowired
    private UserService userService;

    @Value("${nginx.root}")
    private String nginxRoot;

    @Override
    public TbVideoShare insertVideoShare(TbVideoShare tbVideoShare) {
        return videoShareDao.insertVideoShare(tbVideoShare);
    }

    @Override
    public TbVideoShare selectByPrimaryKey(Long id) {
        return videoShareDao.selectByPrimaryKey(id);
    }

    @Override
    public Map<String, Object> selectAllByVideoId(Long videoId, int currentPage, int pageSize) {
        long count = videoShareDao.selectAllByVideoIdCount(videoId);
        PageUtil page = new PageUtil((int)count,currentPage,pageSize);
        List<TbVideoShare> list = videoShareDao.selectAllByVideoId(videoId, page.getPageStartRow(), pageSize);
        for (TbVideoShare temp:list) {
            if(null != temp.getUserId()) {
                User user = userService.findUserByUserId(temp.getUserId());
                if(null != user) {
                    user.setPicPath(nginxRoot + user.getPicPath());
                    temp.setUser(user);
                }
            }
        }
        if(count<=0){
            list=new ArrayList<TbVideoShare>();
        }
        //设置回复内容
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put("page", page);
        result.put("results", list);
        return result;
    }
}
