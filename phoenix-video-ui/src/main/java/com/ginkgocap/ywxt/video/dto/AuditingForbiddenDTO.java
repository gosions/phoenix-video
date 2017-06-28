package com.ginkgocap.ywxt.video.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by gintong on 2017/6/20.
 */
@ApiModel
public class AuditingForbiddenDTO implements Serializable {

    /**
     * 视频id
     */
    @ApiModelProperty(name = "id", value = "视频id")
    private Long id;

    /**
     * 禁用原因
     */
    @ApiModelProperty(name = "forbiddenReason", value = "视频禁用的原因")
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
