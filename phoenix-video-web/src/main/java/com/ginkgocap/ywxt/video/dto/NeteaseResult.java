package com.ginkgocap.ywxt.video.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author cinderella
 * @version 2017/12/6
 */
public class NeteaseResult implements Serializable {

    private Integer code;

    private String msg;

    private Object ret;

    public NeteaseResult() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getRet() {
        return ret;
    }

    public void setRet(Object ret) {
        this.ret = ret;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
