package com.ginkgocap.ywxt.video.service.impl;

import com.ginkgocap.ywxt.video.dao.VideoReportDao;
import com.ginkgocap.ywxt.video.model.TbVideoReport;
import com.ginkgocap.ywxt.video.service.VideoReportService;
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
@Service("videoReportService")
public class VideoReportServiceImpl implements VideoReportService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private VideoReportDao videoReportDao;


    @Override
    public TbVideoReport insertVideoReport(TbVideoReport tbVideoReport) {
        return videoReportDao.insertVideoReport(tbVideoReport);
    }

    @Override
    public TbVideoReport selectByPrimaryKey(Long id) {
        return videoReportDao.selectByPrimaryKey(id);
    }

    @Override
    public Map<String, Object> selectAllByVideoId(Long videoId, int currentPage, int pageSize) {

        long count = videoReportDao.selectAllByVideoIdCount(videoId);
        PageUtil page = new PageUtil((int)count,currentPage,pageSize);
        List<TbVideoReport> list = videoReportDao.selectAllByVideoId(videoId, page.getPageStartRow() , pageSize);
        if(count<=0){
            list=new ArrayList<TbVideoReport>();
        }
        //设置回复内容
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put("page", page);
        result.put("results", list);
        return result;
    }
}
