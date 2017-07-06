package com.ginkgocap.ywxt.video.service;

import com.ginkgocap.ywxt.video.model.TbVideo;
import com.ginkgocap.ywxt.video.utils.QueryReqBean;

import java.util.Map;

/**
 * Created by gintong on 2017/5/25.
 */
public interface VideoService {

    TbVideo insertVideo(TbVideo tbVideo);

    TbVideo selectByPrimaryKey(Long id);

    TbVideo selectByPrimaryKeyAndPersonId(Long id, Long personId);

    TbVideo updateVideo(TbVideo tbVideo);

    Map<String, Object> selectSearchPage(QueryReqBean queryReqBean);
}
