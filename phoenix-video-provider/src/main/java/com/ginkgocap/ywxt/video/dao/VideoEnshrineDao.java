package com.ginkgocap.ywxt.video.dao;

import com.ginkgocap.ywxt.video.model.TbVideoEnshrine;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

/**
 * Created by gintong on 2017/5/25.
 */
public interface VideoEnshrineDao extends ApplicationContextAware {

    TbVideoEnshrine insertVideoEnshrine(TbVideoEnshrine tbVideoEnshrine);

    TbVideoEnshrine selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    TbVideoEnshrine selectByUserIdAndVideoId(Long userId, Long videoId);

    TbVideoEnshrine updateByPrimaryKey(TbVideoEnshrine tbVideoEnshrine);

    List<TbVideoEnshrine> selectAllByVideoId(Long videoId, int currentPage, int pageSize);

    long selectAllByVideoIdCount(Long videoId);

    List<TbVideoEnshrine> selectAllByUserId(Long userId, int currentPage, int pageSize);

    long selectAllByUserIdCount(Long userId);

}
