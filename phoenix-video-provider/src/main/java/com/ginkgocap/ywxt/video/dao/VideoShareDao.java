package com.ginkgocap.ywxt.video.dao;

import com.ginkgocap.ywxt.video.model.TbVideoShare;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

/**
 * Created by gintong on 2017/5/25.
 */
public interface VideoShareDao extends ApplicationContextAware {

    TbVideoShare insertVideoShare(TbVideoShare tbVideoShare);

    TbVideoShare selectByPrimaryKey(Long id);

    TbVideoShare updateByPrimaryKey(TbVideoShare tbVideoShare);

    List<TbVideoShare> selectAllByVideoId(Long videoId, int currentPage, int pageSize);

    long selectAllByVideoIdCount(Long videoId);

}
