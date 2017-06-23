package com.ginkgocap.ywxt.video.constant;

/**
 * Created by gintong on 2017/6/6.
 */
public enum VideoStatusType {

    no_audit(0,"未审核"),
    pass_audit(1,"审核通过"),
    rebut_audit(2,"审核驳回"),
    forbidden_audit(3,"禁用"),
    delete_audit(4,"删除");

    private int key;
    private String value;

    VideoStatusType(int key, String value) {
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
