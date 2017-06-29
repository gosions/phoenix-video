package com.ginkgocap.ywxt.video.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.ginkgocap.ywxt.user.model.User;
import com.ginkgocap.ywxt.user.service.UserService;
import com.ginkgocap.ywxt.video.dao.VideoDao;
import com.ginkgocap.ywxt.video.model.TbVideo;
import com.ginkgocap.ywxt.video.service.VideoService;
import com.ginkgocap.ywxt.video.utils.PageUtil;
import com.ginkgocap.ywxt.video.utils.QueryReqBean;
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
@Service("videoService")
public class VideoServiceImpl implements VideoService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private VideoDao videoDao;

    @Autowired
    private UserService userService;

    @Value("${nginx.root}")
    private String nginxRoot;

    @Override
    public TbVideo insertVideo(TbVideo tbVideo) {
        return videoDao.insertVideo(tbVideo);
    }

    @Override
    public TbVideo selectByPrimaryKey(Long id) {
        TbVideo tbVideo = videoDao.selectByPrimaryKey(id);
        if(null != tbVideo && null != tbVideo.getUserId()) {
            User user = userService.findUserByUserId(tbVideo.getUserId());
            if(null != user) {
                user.setPicPath(nginxRoot + user.getPicPath());
                tbVideo.setUser(user);
            }
        }
        return tbVideo;
    }

    @Override
    public TbVideo updateVideo(TbVideo tbVideo) {
        return videoDao.updateVideo(tbVideo);
    }

    @Override
    public Map<String, Object> selectSearchPage(QueryReqBean queryReqBean) {
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam = queryReqBean.getSearchParams();
        String sortExp = queryReqBean.getSortExp();
        if(StringUtils.isEmpty(sortExp)) {
            sortExp = "create_time DESC";
        }
        mapParam.put("sortExp", sortExp);
        long count = videoDao.selectSearchCount(mapParam);
        PageUtil page = new PageUtil((int)count, queryReqBean.getPageParameter().getCurrentPage(), queryReqBean.getPageParameter().getPageSize());
        mapParam.put("startRow", page.getPageStartRow());
        mapParam.put("pageSize", page.getPageSize());
        List<TbVideo> list = videoDao.selectSearch(mapParam);
        for (TbVideo temp:list) {
            if(null != temp.getUserId()) {
                User user = userService.findUserByUserId(temp.getUserId());
                if(null != user) {
                    user.setPicPath(nginxRoot + user.getPicPath());
                    temp.setUser(user);
                }
            }
        }
        if(count<=0){
            list=new ArrayList<TbVideo>();
        }
        //设置回复内容
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put("page", page);
        result.put("results", list);
        return result;
    }


}
