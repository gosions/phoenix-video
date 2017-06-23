package com.ginkgocap.ywxt.video.service.impl;

import com.ginkgocap.ywxt.util.PageUtil;
import com.ginkgocap.ywxt.video.dao.VideoLikeDao;
import com.ginkgocap.ywxt.video.model.TbVideoLike;
import com.ginkgocap.ywxt.video.service.VideoLikeService;
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
@Service("videoLikeService")
public class VideoLikeServiceImpl implements VideoLikeService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private VideoLikeDao videoLikeDao;

    @Override
    public TbVideoLike insertVideoLike(TbVideoLike tbVideoLike) {
        return videoLikeDao.insertVideoLike(tbVideoLike);
    }

    @Override
    public TbVideoLike selectByPrimaryKey(Long id) {
        return videoLikeDao.selectByPrimaryKey(id);
    }

    @Override
    public Map<String, Object> selectAllByVideoId(Long videoId, int currentPage, int pageSize) {
        long count = videoLikeDao.selectAllByVideoIdCount(videoId);
        PageUtil page = new PageUtil((int)count,currentPage,pageSize);
        List<TbVideoLike> list = videoLikeDao.selectAllByVideoId(videoId, page.getPageStartRow(), pageSize);
        if(count<=0){
            list=new ArrayList<TbVideoLike>(pageSize);
        }
        //设置回复内容
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put("page", page);
        result.put("results", list);
        return result;
    }
}
