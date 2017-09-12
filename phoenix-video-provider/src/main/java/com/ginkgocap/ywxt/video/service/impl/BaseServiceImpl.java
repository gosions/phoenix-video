package com.ginkgocap.ywxt.video.service.impl;

import com.ginkgocap.ywxt.user.model.User;
import com.ginkgocap.ywxt.user.service.UserService;
import com.ginkgocap.ywxt.video.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by gintong on 2017/9/12.
 */
@Service("videoBaseService")
public class BaseServiceImpl implements BaseService{

    @Autowired
    private UserService userService;

    @Value("${nginx.root}")
    private String nginxRoot;

    @Override
    public User handleUserPicPath(Long userId) {
        User user = userService.findUserByUserId(userId);
        if(null != user) {
            if(null != user.getPicPath()) {
                if ( !user.getPicPath().contains("http")) {
                    user.setPicPath(nginxRoot + user.getPicPath());
                }
            }
            return user;
        }
        return null;
    }
}