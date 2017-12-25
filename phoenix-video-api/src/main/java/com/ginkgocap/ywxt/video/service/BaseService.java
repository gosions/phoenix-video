package com.ginkgocap.ywxt.video.service;

import com.ginkgocap.ywxt.user.model.User;

/**
 * Created by gintong on 2017/9/12.
 */
public interface BaseService {

    User handleUserPicPath(Long userId);

    Boolean isFollowOrganization(Long userId, Long personId);
}
