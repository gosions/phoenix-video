package com.ginkgocap.ywxt.video.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author cinderella
 * @version 2017/12/11
 */
public class ChatRoomMsg implements Serializable{

    private static final long serialVersionUID = 9213813176944804886L;

    /**
     * 必须 聊天室id
     */
    private Long roomid;

    /**
     * 必须 客户端消息id，使用uuid等随机串，msgId相同的消息会被客户端去重
     */
    private String msgId;

    /**
     * 必须 消息发出者的账号accid
     */
    private String fromAccid;

    /**
     * 必须   消息类型：
             0: 表示文本消息，
             1: 表示图片，
             2: 表示语音，
             3: 表示视频，
             4: 表示地理位置信息，
             6: 表示文件，
             10: 表示Tips消息，
             100: 自定义消息类型（特别注意，对于未对接易盾反垃圾功能的应用，该类型的消息不会提交反垃圾系统检测）
     */
    private Integer msgType;

    /**
     * 非必须 重发消息标记，0：非重发消息，1：重发消息，如重发消息会按照msgid检查去重逻辑
     */
    private Integer resendFlag;

    /**
     * 非必须 消息内容，格式同消息格式示例中的body字段,长度限制4096字符
      */
    private String attach;

    /**
     * 非必须 消息扩展字段，内容可自定义，请使用JSON格式，长度限制4096字符
     */
    private String ext;

    /**
     * 非必须 对于对接了易盾反垃圾功能的应用，本消息是否需要指定经由易盾检测的内容（antispamCustom）。
            true或false, 默认false。
            只对消息类型为：100 自定义消息类型 的消息生效。
     */
    private String antispam;

    /**
     * 非必须 在antispam参数为true时生效。
             自定义的反垃圾检测内容, JSON格式，长度限制同body字段，不能超过5000字符，要求antispamCustom格式如下：

             {"type":1,"data":"custom content"}

             字段说明：
             1. type: 1：文本，2：图片。
             2. data: 文本内容or图片地址。
     */
    private String  antispamCustom;

    /**
     * 非必须 是否跳过存储云端历史，0：不跳过，即存历史消息；1：跳过，即不存云端历史；默认0
     */
    private Integer skipHistory;

    public ChatRoomMsg() {
    }

    public Long getRoomid() {
        return roomid;
    }

    public void setRoomid(Long roomid) {
        this.roomid = roomid;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getFromAccid() {
        return fromAccid;
    }

    public void setFromAccid(String fromAccid) {
        this.fromAccid = fromAccid;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Integer getResendFlag() {
        return resendFlag;
    }

    public void setResendFlag(Integer resendFlag) {
        this.resendFlag = resendFlag;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getAntispam() {
        return antispam;
    }

    public void setAntispam(String antispam) {
        this.antispam = antispam;
    }

    public String getAntispamCustom() {
        return antispamCustom;
    }

    public void setAntispamCustom(String antispamCustom) {
        this.antispamCustom = antispamCustom;
    }

    public Integer getSkipHistory() {
        return skipHistory;
    }

    public void setSkipHistory(Integer skipHistory) {
        this.skipHistory = skipHistory;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
