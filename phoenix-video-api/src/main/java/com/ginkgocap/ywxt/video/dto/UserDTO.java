package com.ginkgocap.ywxt.video.dto;

import com.ginkgocap.ywxt.user.model.User;

import java.io.Serializable;

/**
 * Created by gintong on 2017/7/6.
 */
public class UserDTO implements Serializable {

    private User user;

    /**
     * 是否关注组织
     */
    private boolean isfollow=false;

    /**
     * 是否收藏视频
     */
    private boolean isEnshrine=false;

    public UserDTO() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isIsfollow() {
        return isfollow;
    }

    public void setIsfollow(boolean isfollow) {
        this.isfollow = isfollow;
    }

    public boolean isIsEnshrine() {
        return isEnshrine;
    }

    public void setIsEnshrine(boolean enshrine) {
        isEnshrine = enshrine;
    }
}
