package com.ginkgocap.ywxt.video.dao;

import com.ginkgocap.ywxt.video.model.TbVideoReport;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

/**
 * Created by gintong on 2017/5/25.
 */
public interface VideoReportDao extends ApplicationContextAware {

    TbVideoReport insertVideoReport(TbVideoReport tbVideoReport);

    TbVideoReport selectByPrimaryKey(Long id);

    TbVideoReport updateByPrimaryKey(TbVideoReport tbVideoReport);

    List<TbVideoReport> selectAllByVideoId(Long videoId, int currentPage, int pageSize);

    long selectAllByVideoIdCount(Long videoId);

}
