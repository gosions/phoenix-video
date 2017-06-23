package com.ginkgocap.ywxt.video.dto;

import java.io.Serializable;

/**
 * Created by gintong on 2017/6/20.
 */
public class AuditingForbiddenDTO implements Serializable {

    private Long id;

    private String forbiddenReason;

    public AuditingForbiddenDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getForbiddenReason() {
        return forbiddenReason;
    }

    public void setForbiddenReason(String forbiddenReason) {
        this.forbiddenReason = forbiddenReason;
    }
}
