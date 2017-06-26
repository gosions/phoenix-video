package com.ginkgocap.ywxt.video.service.impl;

import com.ginkgocap.ywxt.video.dao.VideoPlayDao;
import com.ginkgocap.ywxt.video.model.TbVideoPlay;
import com.ginkgocap.ywxt.video.service.VideoPlayService;
import com.ginkgocap.ywxt.video.utils.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gintong on 2017/5/25.
 */
@Service("videoPlayService")
public class VideoPlayServiceImpl implements VideoPlayService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private VideoPlayDao videoPlayDao;


    @Override
    public TbVideoPlay insertVideoPlay(TbVideoPlay tbVideoPlay) {
        return videoPlayDao.insertVideoPlay(tbVideoPlay);
    }

    @Override
    public TbVideoPlay selectByPrimaryKey(Long id) {
        return videoPlayDao.selectByPrimaryKey(id);
    }

    @Override
    public Map<String, Object> selectAllByVideoId(Long videoId, int currentPage, int pageSize) {
        long count = videoPlayDao.selectAllByVideoIdCount(videoId);
        PageUtil page = new PageUtil((int)count,currentPage,pageSize);
        List<TbVideoPlay> list = videoPlayDao.selectAllByVideoId(videoId, page.getPageStartRow(), pageSize);
        if(count<=0){
            list=new ArrayList<TbVideoPlay>();
        }
        //设置回复内容
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put("page", page);
        result.put("results", list);
        return result;
    }

}
