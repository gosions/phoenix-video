package com.ginkgocap.ywxt.video.constant;

/**
 * @author cinderella
 * @version 2017/11/16
 */
public enum DeviceTypeEnum {

    DEVICE_IOS(0,"ios"),
    DEVICE_ANDROID(1,"android");

    private int key;
    private String value;

    DeviceTypeEnum(int key, String value) {
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
