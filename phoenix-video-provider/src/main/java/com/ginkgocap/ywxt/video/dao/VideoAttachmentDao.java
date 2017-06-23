package com.ginkgocap.ywxt.video.dao;

import com.ginkgocap.ywxt.video.model.TbVideoAttachment;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by gintong on 2017/5/25.
 */
public interface VideoAttachmentDao extends ApplicationContextAware {

    TbVideoAttachment insertVideoAttachment(TbVideoAttachment tbVideoAttachment);

    TbVideoAttachment selectByPrimaryKey(Long id);

    TbVideoAttachment updateByPrimaryKey(TbVideoAttachment tbVideoAttachment);

}
