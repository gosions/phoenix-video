package com.ginkgocap.ywxt.video.dao;

import com.ginkgocap.ywxt.video.model.TbVideoPlay;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

/**
 * Created by gintong on 2017/5/25.
 */
public interface VideoPlayDao extends ApplicationContextAware {

    TbVideoPlay insertVideoPlay(TbVideoPlay tbVideoPlay);

    TbVideoPlay selectByPrimaryKey(Long id);

    TbVideoPlay updateByPrimaryKey(TbVideoPlay tbVideoPlay);

    List<TbVideoPlay> selectAllByVideoId(Long videoId, int currentPage, int pageSize);

    long selectAllByVideoIdCount(Long videoId);

}
