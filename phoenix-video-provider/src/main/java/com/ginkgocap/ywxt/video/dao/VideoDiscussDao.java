package com.ginkgocap.ywxt.video.dao;

import com.ginkgocap.ywxt.video.model.TbVideoDiscuss;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

/**
 * Created by gintong on 2017/5/25.
 */
public interface VideoDiscussDao extends ApplicationContextAware {

    TbVideoDiscuss insertVideoDiscuss(TbVideoDiscuss tbVideoDiscuss);

    TbVideoDiscuss selectByPrimaryKey(Long id);

    TbVideoDiscuss updateByPrimaryKey(TbVideoDiscuss tbVideoDiscuss);

    List<TbVideoDiscuss> selectAllByVideoId(Long videoId, Integer currentPage, Integer pageSize);

    long selectAllByVideoIdCount(Long videoId);

}
