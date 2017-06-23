package com.ginkgocap.ywxt.video.dao;

import com.ginkgocap.ywxt.video.model.TbVideoLike;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

/**
 * Created by gintong on 2017/5/25.
 */
public interface VideoLikeDao extends ApplicationContextAware {

    TbVideoLike insertVideoLike(TbVideoLike tbVideoLike);

    TbVideoLike selectByPrimaryKey(Long id);

    TbVideoLike selectByPrimaryUserIdAndVideoId(Long userId, Long videoId);

    TbVideoLike updateByPrimaryKey(TbVideoLike tbVideoLike);

    List<TbVideoLike> selectAllByVideoId(Long videoId, int currentPage, int pageSize);

    long selectAllByVideoIdCount(Long videoId);

}
