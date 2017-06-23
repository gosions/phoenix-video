package com.ginkgocap.ywxt.video.service;

import com.ginkgocap.ywxt.video.model.TbVideoShare;

import java.util.Map;

/**
 * Created by gintong on 2017/5/31.
 */
public interface VideoShareService {

    TbVideoShare insertVideoShare(TbVideoShare tbVideoShare);

    TbVideoShare selectByPrimaryKey(Long id);

    Map<String, Object> selectAllByVideoId(Long videoId, int currentPage, int pageSize);
}
