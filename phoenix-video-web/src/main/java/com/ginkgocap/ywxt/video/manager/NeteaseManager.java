package com.ginkgocap.ywxt.video.manager;

import com.alibaba.fastjson.JSONObject;
import com.ginkgocap.ywxt.video.constant.Charsets;
import com.ginkgocap.ywxt.video.constant.MediaTypes;
import com.ginkgocap.ywxt.video.constant.NeteaseImApiUrl;
import com.ginkgocap.ywxt.video.dto.ChatRoomMsg;
import com.ginkgocap.ywxt.video.dto.LiveUserDTO;
import com.ginkgocap.ywxt.video.service.VideoService;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cinderella
 * @version 2017/12/5
 */
@Component
@Transactional(propagation = Propagation.NEVER, rollbackFor = Exception.class)
public class NeteaseManager extends BaseManager{

    private static final Logger LOGGER = LoggerFactory.getLogger(NeteaseManager.class);

    private static final String NULL_TOKEN = "null";

    @Resource
    private VideoService videoService;

    private String getHttpPostResult(final HttpPost httpPost, final List<NameValuePair> nvps) {
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charsets.UTF8));
            return doPost(httpPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getHttpPostResult(final HttpPost httpPost, final String jsonObject) {
        try {
            httpPost.setEntity(new StringEntity(jsonObject, Charsets.UTF8));
            return doPost(httpPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建网易云通信ID
     * @param accId
     * @return
     */
    public String createAccount(final String accId, final String token) {
        HttpPost httpPost = getHeader(NeteaseImApiUrl.CREATE_ACCOUNT, MediaTypes.NETEASE_UTF_8);
        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<>(4);
        nvps.add(new BasicNameValuePair("accid", accId));
        if (!NULL_TOKEN.equals(token)) {
            nvps.add(new BasicNameValuePair("token", token));
        }
        LiveUserDTO liveUserDTO = videoService.getLiveUserDTO(Long.parseLong(accId));
        if (null != liveUserDTO) {
            nvps.add(new BasicNameValuePair("name", liveUserDTO.getName()));
            nvps.add(new BasicNameValuePair("icon", liveUserDTO.getPicPath()));
        }
        return getHttpPostResult(httpPost, nvps);
    }

    /**
     * 更新并获取新token
     * @param accId
     * @return
     */
    public String refreshToken(final String accId) {
        HttpPost httpPost = getHeader(NeteaseImApiUrl.REFRESH_TOKEN, MediaTypes.NETEASE_UTF_8);
        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<>(1);
        nvps.add(new BasicNameValuePair("accid", accId));
        return getHttpPostResult(httpPost, nvps);
    }

    /**
     * 创建聊天室
     * @param creator
     * @param roomName
     * @return
     */
    public String createChatRoom(
            final String creator,
            final String roomName,
            final String announcement,
            final String broadCastUrl,
            final String ext) {
        HttpPost httpPost = getHeader(NeteaseImApiUrl.CREATE_CHAT_ROOM, MediaTypes.NETEASE_UTF_8);
        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<>(5);
        nvps.add(new BasicNameValuePair("creator", creator));
        nvps.add(new BasicNameValuePair("name", roomName));
        nvps.add(new BasicNameValuePair("announcement", announcement));
        nvps.add(new BasicNameValuePair("broadcasturl", broadCastUrl));
        nvps.add(new BasicNameValuePair("ext", ext));
        return getHttpPostResult(httpPost, nvps);
    }

    /**
     * 请求聊天室地址
     * @param roomId
     * @param accId
     * @return
     */
    public String requestAddr(final long roomId, final String accId, final Integer clientType) {
        // + "?roomid=" + roomId + "&accid=" + accId + "&clienttype=" + clientType
        HttpPost httpPost = getHeader(NeteaseImApiUrl.REQUEST_ADDR, MediaTypes.NETEASE_UTF_8);
        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<>(3);
        nvps.add(new BasicNameValuePair("roomid", roomId + ""));
        nvps.add(new BasicNameValuePair("accid", accId));
        nvps.add(new BasicNameValuePair("clienttype", clientType + ""));
        return getHttpPostResult(httpPost, nvps);
    }

    /**
     * 发送聊天室消息
     * @param chatRoomMsg
     * @return
     */
    public String sendMsg(final ChatRoomMsg chatRoomMsg) {
        HttpPost httpPost = getHeader(NeteaseImApiUrl.SEND_MSG, MediaTypes.NETEASE_UTF_8);
        return getHttpPostResult(httpPost, getFiledsInfo(chatRoomMsg));
    }

    /**
     * 查询聊天室信息
     * @param roomId 聊天室id
     * @param needOnlineUserCount 是否需要返回在线人数，true或false，默认false
     * @return
     */
    public String getChatRoomInfo(final long roomId, final String needOnlineUserCount) {
        HttpPost httpPost = getHeader(NeteaseImApiUrl.CHAT_ROOM_GET, MediaTypes.NETEASE_UTF_8);
        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<>(2);
        nvps.add(new BasicNameValuePair("roomid", roomId + ""));
        nvps.add(new BasicNameValuePair("needOnlineUserCount", needOnlineUserCount));
        return getHttpPostResult(httpPost, nvps);
    }

    /**
     * 分页获取成员列表
     * @param roomId 聊天室id
     * @param type 需要查询的成员类型,0:固定成员;1:非固定成员;2:仅返回在线的固定成员
     * @param endTime 单位毫秒，按时间倒序最后一个成员的时间戳,0表示系统当前时间
     * @param limit 返回条数，<=100
     * @return
     */
    public String membersByPage(final long roomId, final int type, final long endTime, final long limit) {
        HttpPost httpPost = getHeader(NeteaseImApiUrl.MEMBERS_BY_PAGE, MediaTypes.NETEASE_UTF_8);
        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<>(4);
        nvps.add(new BasicNameValuePair("roomid", roomId + ""));
        nvps.add(new BasicNameValuePair("type", type + ""));
        nvps.add(new BasicNameValuePair("endtime", endTime + ""));
        nvps.add(new BasicNameValuePair("limit", limit + ""));
        return getHttpPostResult(httpPost, nvps);
    }

    /**
     * 查询聊天室统计指标TopN
     * @param topN topn值，可选值 1~500，默认值100
     * @param timestamp 需要查询的指标所在的时间坐标点，不提供则默认当前时间，单位秒/毫秒皆可
     * @param period 统计周期，可选值包括 hour/day, 默认hour
     * @param orderBy 取排序值,可选值 active/enter/message,分别表示按日活排序，进入人次排序和消息数排序， 默认active
     * @return
     */
    public String topN(final int topN, final long timestamp, final String period, final String orderBy) {
        HttpPost httpPost = getHeader(NeteaseImApiUrl.CHAT_ROOM_TOPN, MediaTypes.NETEASE_UTF_8);
        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<>(4);
        nvps.add(new BasicNameValuePair("topn", topN + ""));
        nvps.add(new BasicNameValuePair("timestamp", timestamp + ""));
        nvps.add(new BasicNameValuePair("period", period));
        nvps.add(new BasicNameValuePair("orderby", orderBy));
        return getHttpPostResult(httpPost, nvps);
    }

    /**
     * 聊天室云端历史消息查询
     * @param roomId 聊天室id
     * @param accId 用户账号
     * @param timeTag 查询的时间戳锚点，13位。reverse=1时timetag为起始时间戳，reverse=2时timetag为终止时间戳
     * @param limit 本次查询的消息条数上限(最多200条),小于等于0，或者大于200，会提示参数错误
     * @param reverse 1按时间正序排列，2按时间降序排列。其它返回参数414错误。默认是2按时间降序排列
     * @return
     */
    public String queryChatRoomMsg(final long roomId, final String accId, final long timeTag, final long limit, final int reverse) {
        HttpPost httpPost = getHeader(NeteaseImApiUrl.QUERY_CHAT_ROOM_MSG, MediaTypes.NETEASE_UTF_8);
        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<>(5);
        nvps.add(new BasicNameValuePair("roomid", roomId + ""));
        nvps.add(new BasicNameValuePair("accid", accId));
        nvps.add(new BasicNameValuePair("timetag", timeTag + ""));
        nvps.add(new BasicNameValuePair("limit", limit + ""));
        nvps.add(new BasicNameValuePair("reverse", reverse + ""));
        return getHttpPostResult(httpPost, nvps);
    }

    /**
     * 创建一个直播频道
     * @param name
     * @param type
     * @return
     */
    public String createChannel(final String name, final Integer type) {
        HttpPost httpPost = getHeader(NeteaseImApiUrl.CREATE_CHANNEL, MediaTypes.JSON_UTF_8);
        // 设置请求的参数
        JSONObject jsonObject = new JSONObject(2);
        jsonObject.put("name", name);
        jsonObject.put("type", type);
        return getHttpPostResult(httpPost, jsonObject.toJSONString());
    }

    /**
     * 禁用频道
     * @param cid
     * @return
     */
    public String pauseChannel(final String cid) {
        HttpPost httpPost = getHeader(NeteaseImApiUrl.PAUSE_CHANNEL, MediaTypes.JSON_UTF_8);
        // 设置请求的参数
        JSONObject jsonObject = new JSONObject(1);
        jsonObject.put("cid", cid);
        return getHttpPostResult(httpPost, jsonObject.toJSONString());
    }

    /**
     * 恢复频道
     * @param cid
     * @return
     */
    public String resumeChannel(final String cid) {
        HttpPost httpPost = getHeader(NeteaseImApiUrl.RESUME_CHANNEL, MediaTypes.JSON_UTF_8);
        // 设置请求的参数
        JSONObject jsonObject = new JSONObject(1);
        jsonObject.put("cid", cid);
        return getHttpPostResult(httpPost, jsonObject.toJSONString());
    }

    /**
     * 获取频道状态
     * @param cid
     * @return
     */
    public String getChannelStatus(final String cid) {
        HttpPost httpPost = getHeader(NeteaseImApiUrl.CHANNEL_STATS, MediaTypes.JSON_UTF_8);
        // 设置请求的参数
        JSONObject jsonObject = new JSONObject(1);
        jsonObject.put("cid", cid);
        return getHttpPostResult(httpPost, jsonObject.toJSONString());
    }

    /**
     * 重新获取推流地址
     * @param cid
     * @return
     */
    public String getChannelAddress(final String cid) {
        HttpPost httpPost = getHeader(NeteaseImApiUrl.CHANNEL_ADDRESS, MediaTypes.JSON_UTF_8);
        // 设置请求的参数
        JSONObject jsonObject = new JSONObject(1);
        jsonObject.put("cid", cid);
        return getHttpPostResult(httpPost, jsonObject.toJSONString());
    }

}
