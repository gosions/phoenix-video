package com.ginkgocap.ywxt.video.dao;

import com.ginkgocap.ywxt.video.model.TbVideo;
import org.springframework.context.ApplicationContextAware;

import java.util.List;
import java.util.Map;

/**
 * Created by gintong on 2017/5/25.
 */
public interface VideoDao extends ApplicationContextAware {

    TbVideo insertVideo(TbVideo tbVideo);

    TbVideo selectByPrimaryKey(Long id);

    List<TbVideo> selectByPrimary();

    TbVideo updateVideo(TbVideo tbVideo);

    Long selectSearchCount(Map<String, Object> searchParams);

    List<TbVideo> selectSearch(Map<String, Object> searchParams);


}
