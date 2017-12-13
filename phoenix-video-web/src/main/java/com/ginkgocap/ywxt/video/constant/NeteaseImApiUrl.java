package com.ginkgocap.ywxt.video.constant;

/**
 * @author cinderella
 * @version 2017/12/5
 */
public class NeteaseImApiUrl {

    /**
     * 创建网易云通信ID
     */
    public static final String CREATE_ACCOUNT = "https://api.netease.im/nimserver/user/create.action";

    /**
     * 更新并获取新token
     */
    public static final String REFRESH_TOKEN = "https://api.netease.im/nimserver/user/refreshToken.action";

    /**
     * ****************************************************************************************************
     * 创建聊天室
     */
    public static final String CREATE_CHAT_ROOM = "https://api.netease.im/nimserver/chatroom/create.action";

    /**
     * 请求聊天室地址
     */
    public static final String REQUEST_ADDR = "https://api.netease.im/nimserver/chatroom/requestAddr.action";

    /**
     * 发送聊天室消息
     */
    public static final String SEND_MSG = "https://api.netease.im/nimserver/chatroom/sendMsg.action";

    /**
     * 查询聊天室信息
     */
    public static final String CHAT_ROOM_GET = "https://api.netease.im/nimserver/chatroom/get.action";

    /**
     * 分页获取成员列表
     */
    public static final String MEMBERS_BY_PAGE = "https://api.netease.im/nimserver/chatroom/membersByPage.action";

    /**
     * 查询聊天室统计指标TopN
     */
    public static final String CHAT_ROOM_TOPN = " https://api.netease.im/nimserver/stats/chatroom/topn.action";

    /**
     * 聊天室云端历史消息查询
     */
    public static final String QUERY_CHAT_ROOM_MSG = "https://api.netease.im/nimserver/history/queryChatroomMsg.action";

    /**
     * ***********************************************************************************************************
     * 创建频道
     */
    public static final String CREATE_CHANNEL = "https://vcloud.163.com/app/channel/create";

    /**
     * 禁用频道
     */
    public static final String PAUSE_CHANNEL = "https://vcloud.163.com/app/channel/pause";

    /**
     * 恢复频道
     */
    public static final String RESUME_CHANNEL = "https://vcloud.163.com/app/channel/resume";

    /**
     * 获取频道状态
     */
    public static final String CHANNEL_STATS = "https://vcloud.163.com/app/channelstats";


}
