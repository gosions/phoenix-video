package com.ginkgocap.ywxt.video.service;

import com.ginkgocap.ywxt.video.model.TbVideoPlay;

import java.util.Map;

/**
 * Created by gintong on 2017/5/31.
 */
public interface VideoPlayService {

    TbVideoPlay insertVideoPlay(TbVideoPlay tbVideoPlay);

    TbVideoPlay selectByPrimaryKey(Long id);

    Map<String, Object> selectAllByVideoId(Long videoId, int currentPage, int pageSize);

}
