package com.ginkgocap.ywxt.video.service;

import com.ginkgocap.ywxt.video.model.TbVideoLike;

import java.util.Map;


/**
 * Created by gintong on 2017/5/31.
 */
public interface VideoLikeService {

    TbVideoLike insertVideoLike(TbVideoLike tbVideoLike);

    TbVideoLike selectByPrimaryKey(Long id);

    Map<String, Object> selectAllByVideoId(Long videoId, int currentPage, int pageSize);

}
