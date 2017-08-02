package com.ginkgocap.ywxt.video.constant;

/**
 * Created by gintong on 2017/6/6.
 */
public enum VideoStatusEnum {

    NO_AUDIT(0,"未审核"),
    PASS_AUDIT(1,"审核通过"),
    REBUT_AUDIT(2,"审核驳回"),
    FORBIDDEN_AUDIT(3,"禁用"),
    DELETE_AUDIT(4,"删除");

    private int key;
    private String value;

    VideoStatusEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
            this.value = value;
        }

}
