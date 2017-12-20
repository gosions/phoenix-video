package com.ginkgocap.ywxt.video.dto.netease;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author cinderella
 * @version 2017/12/6
 */
public class NeteaseResult implements Serializable {

    private int code;

    private String msg;

    private JSONObject ret;

    public NeteaseResult() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JSONObject getRet() {
        return ret;
    }

    public void setRet(JSONObject ret) {
        this.ret = ret;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
