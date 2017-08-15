package com.ginkgocap.ywxt.video.service;

import com.ginkgocap.ywxt.video.model.TbVideoEnshrine;

import java.util.Map;

/**
 * Created by gintong on 2017/5/31.
 */
public interface VideoEnshrineService {

    TbVideoEnshrine insertVideoEnshrine(TbVideoEnshrine tbVideoEnshrine);

    TbVideoEnshrine selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    TbVideoEnshrine selectByUserIdAndVideoId(Long userId, Long videoId);

    Map<String, Object> selectAllByVideoId(Long videoId, int currentPage, int pageSize);

    Map<String, Object> selectAllByUserId(Long videoId, int currentPage, int pageSize);

}
