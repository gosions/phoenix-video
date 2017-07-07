package com.ginkgocap.ywxt.video.service;

import com.ginkgocap.ywxt.video.model.TbVideoDiscuss;

import java.util.Map;

/**
 * Created by gintong on 2017/5/31.
 */
public interface VideoDiscussService {

    TbVideoDiscuss insertVideoDiscuss(TbVideoDiscuss tbVideoDiscuss);

    TbVideoDiscuss selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    Map<String, Object> selectAllByVideoId(Long videoId, int currentPage, int pageSize);
}
