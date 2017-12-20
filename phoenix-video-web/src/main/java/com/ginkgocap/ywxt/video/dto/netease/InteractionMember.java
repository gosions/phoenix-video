package com.ginkgocap.ywxt.video.dto.netease;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 *
 * @author hzxuwen
 * @date 2016/7/15
 */
public class InteractionMember implements Serializable{
    private static final long serialVersionUID = 923288408568636896L;
    private String account;
    private String name;
    private String avatar;
    private AVChatType avChatType;
    private boolean isSelected;
    private MicStateEnum micStateEnum;

    public InteractionMember() {
    }

    public InteractionMember(String account, String name, String avatar, AVChatType avChatType) {
        this.account = account;
        this.name = name;
        this.avatar = avatar;
        this.avChatType = avChatType;
        this.isSelected = false;
        this.micStateEnum = MicStateEnum.NONE;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public AVChatType getAvChatType() {
        return avChatType;
    }

    public void setAvChatType(AVChatType avChatType) {
        this.avChatType = avChatType;
    }

    public MicStateEnum getMicStateEnum() {
        return micStateEnum;
    }

    public void setMicStateEnum(MicStateEnum micStateEnum) {
        this.micStateEnum = micStateEnum;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
