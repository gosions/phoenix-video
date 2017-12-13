package com.ginkgocap.ywxt.video.controller;

import com.ginkgocap.ywxt.video.dto.ChatRoomMsg;
import com.ginkgocap.ywxt.video.dto.LiveUserDTO;
import com.ginkgocap.ywxt.video.manager.NeteaseManager;
import com.ginkgocap.ywxt.video.service.VideoService;
import com.gintong.frame.util.dto.CommonResultCode;
import com.gintong.frame.util.dto.InterfaceResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * @author cinderella
 * @version 2017/12/5
 */
@RestController
@RequestMapping("/v1/netease")
public class NeteaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NeteaseController.class);

    @Resource
    private NeteaseManager neteaseManager;

    @Resource
    private VideoService videoService;

    @ApiOperation(value = "获取用户昵称，头像", notes = "")
    @ApiImplicitParam(name = "userId", value = "userId", required = true, dataType  = "Long", paramType = "path")
    @RequestMapping(value = { "/user/{userId}" }, method = { RequestMethod.GET })
    public InterfaceResult getLiveUserDTO(@PathVariable("userId") Long userId) {
        LiveUserDTO liveUserDTO = videoService.getLiveUserDTO(userId);
        if (null != liveUserDTO) {
            return InterfaceResult.getSuccessInterfaceResultInstance(liveUserDTO);
        }
        return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
    }

    /**
     *******************************************************************************************************************
     */
    @ApiOperation(value = "创建网易云通信ID", notes = "http://dev.netease.im/docs/product/IM即时通讯/服务端API文档/网易云通信ID?#创建网易云通信ID")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accId", value = "网易云通信ID，最大长度32字符，必须保证一个,APP内唯一（只允许字母、数字、半角下划线_、@、半角点以及半角-组成，不区分大小写，会统一小写处理",
                    required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "token", value = "网易云通信ID可以指定登录token值，最大长度128字符，并更新，如果未指定，会自动生成token，并在创建成功后返回",
                    required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = { "/user/create/{accId}/{token}" }, method = { RequestMethod.PUT })
    public String createAccount(@PathVariable("accId") String accId, @PathVariable("token") String token) {
       return neteaseManager.createAccount(accId, token);
    }

    @ApiOperation(value = "更新并获取新token", notes = "http://dev.netease.im/docs/product/IM即时通讯/服务端API文档/网易云通信ID?#更新并获取新token")
    @ApiImplicitParam(name = "accid", value = "网易云通信ID，最大长度32字符，必须保证一个APP内唯一", required = true, dataType = "ChatRoomMsg")
    @RequestMapping(value = { "/user/refreshToken/{accId}" }, method = { RequestMethod.POST })
    public String refreshToken(@PathVariable("accId") String accId) {
        return neteaseManager.refreshToken(accId);
    }

    /**
     *******************************************************************************************************************
     */
    @ApiOperation(value = "创建聊天室", notes = "http://dev.netease.im/docs/product/IM即时通讯/服务端API文档/聊天室?#创建聊天室")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "creator", value = "聊天室属主的账号accid", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "roomName", value = "聊天室名称，长度限制128个字符", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = { "/chatroom/create/{creator}/{roomName}" }, method = { RequestMethod.POST })
    public String createChatRoom(@PathVariable("creator") String creator, @PathVariable("roomName") String roomName) {
        return neteaseManager.createChatRoom(creator, roomName);
    }

    @ApiOperation(value = "请求聊天室地址", notes = "http://dev.netease.im/docs/product/IM即时通讯/服务端API文档/聊天室?#请求聊天室地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roomId", value = "聊天室id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "accId", value = "进入聊天室的账号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "clientType", value = "1:weblink（客户端为web端时使用）; 2:commonlink（客户端为非web端时使用）, 默认1", required = true, dataType = "int", paramType = "path")
    })
    @RequestMapping(value = { "/chatroom/requestAddr/{roomId}/{accId}/{clientType}" }, method = { RequestMethod.POST })
    public String requestAddr(
            @PathVariable("roomId") Long roomId,
            @PathVariable("accId") String accId,
            @PathVariable("clientType") Integer clientType) {
        return neteaseManager.requestAddr(roomId, accId, clientType);
    }

    @ApiOperation(value="发送聊天室消息", notes="http://dev.netease.im/docs/product/IM即时通讯/服务端API文档/聊天室?#发送聊天室消息")
    @ApiImplicitParam(name = "chatRoomMsg", value = "详细实体ChatRoomMsgo", required = true, dataType = "ChatRoomMsg")
    @RequestMapping(value = { "/chatroom/sendMsg" }, method = { RequestMethod.PUT })
    public String sendMsg(@RequestBody ChatRoomMsg chatRoomMsg) {
        return neteaseManager.sendMsg(chatRoomMsg);
    }

    @ApiOperation(value = "查询聊天室信息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roomId", value = "聊天室id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "needOnlineUserCount", value = "是否需要返回在线人数，true或false，默认false", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = { "/chatroom/get/{roomId}/{needOnlineUserCount}" }, method = { RequestMethod.GET })
    public String getChatRoomInfo(@PathVariable("roomId") Long roomId, @PathVariable("needOnlineUserCount") String needOnlineUserCount) {
        return neteaseManager.getChatRoomInfo(roomId, needOnlineUserCount);
    }

    @ApiOperation(value = "分页获取成员列表", notes = "http://dev.netease.im/docs/product/IM即时通讯/服务端API文档/聊天室?#分页获取成员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roomId", value = "聊天室id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "type", value = "需要查询的成员类型,0:固定成员;1:非固定成员;2:仅返回在线的固定成员", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "endTime", value = "单位毫秒，按时间倒序最后一个成员的时间戳,0表示系统当前时间", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "limit", value = "返回条数，<=100", required = true, dataType = "Long", paramType = "path")
    })
    @RequestMapping(value = { "/chatroom/membersByPage/{roomId}/{type}/{endTime}/{limit}" }, method = { RequestMethod.GET })
    public String membersByPage(
            @PathVariable("roomId") Long roomId,
            @PathVariable("type") Integer type,
            @PathVariable("endTime") Long endTime,
            @PathVariable("limit") Long limit) {
        return neteaseManager.membersByPage(roomId, type, endTime, limit);
    }

    @ApiIgnore
    @ApiOperation(value = "查询聊天室统计指标TopN", notes = "http://dev.netease.im/docs/product/IM即时通讯/服务端API文档/聊天室?#查询聊天室统计指标TopN")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "topN", value = "topn值，可选值 1~500，默认值100", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "timestamp", value = "需要查询的指标所在的时间坐标点，不提供则默认当前时间，单位秒/毫秒皆可", required = true, dataType = "long", paramType = "path"),
            @ApiImplicitParam(name = "period", value = "统计周期，可选值包括 hour/day, 默认hour", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "orderBy", value = "取排序值,可选值 active/enter/message,分别表示按日活排序，进入人次排序和消息数排序， 默认active", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = { "/stats/chatroom/topn/{topN}/{timestamp}/{period}/{orderBy}" }, method = { RequestMethod.GET })
    public String topN(
            @PathVariable("topN") Integer topN,
            @PathVariable("timestamp") Long timestamp,
            @PathVariable("period") String period,
            @PathVariable("orderBy") String orderBy) {
        return neteaseManager.topN(topN, timestamp, period, orderBy);
    }

    @ApiOperation(value = "聊天室云端历史消息查询", notes = "http://dev.netease.im/docs/product/IM即时通讯/服务端API文档/历史记录?kw=云端&pg=1&pid=4#聊天室云端历史消息查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roomId", value = "聊天室id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "accId", value = "用户账号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "timeTag", value = "查询的时间戳锚点，13位。reverse=1时timetag为起始时间戳，reverse=2时timetag为终止时间戳", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "limit", value = "本次查询的消息条数上限(最多200条),小于等于0，或者大于200，会提示参数错误", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "reverse", value = "1按时间正序排列，2按时间降序排列。其它返回参数414错误。默认是2按时间降序排列", required = true, dataType = "int", paramType = "path")
    })
    @RequestMapping(value = { "/history/queryChatroomMsg/{roomId}/{accId}/{timeTag}/{limit}/{reverse}" }, method = { RequestMethod.GET })
    public String queryChatRoomMsg(
            @PathVariable("roomId") Long roomId,
            @PathVariable("accId") String accId,
            @PathVariable("timeTag") Long timeTag,
            @PathVariable("limit") Integer limit,
            @PathVariable("reverse") Integer reverse) {
        return neteaseManager.queryChatRoomMsg(roomId, accId, timeTag, limit, reverse);
    }

    /**
     *******************************************************************************************************************
     */
    @ApiOperation(value = "创建一个直播频道", notes = "http://dev.netease.im/docs/product/直播/服务端API文档?pos=toc-1-0")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "频道名称（最大长度64个字符，只支持中文、字母、数字和下划线）", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "type", value = "频道类型（0:rtmp）", required = true, dataType = "Integer", paramType = "path")
    })
    @RequestMapping(value = { "/channel/create/{name}/{type}" }, method = { RequestMethod.PUT })
    public String createChatRoom(@PathVariable("name") String name, @PathVariable("type") Integer type) {
        return neteaseManager.createChannel(name, type);
    }

    @ApiOperation(value = "禁用频道", notes = "http://dev.netease.im/docs/product/直播/服务端API文档?pos=toc-1-7")
    @ApiImplicitParam(name = "cid", value = "频道ID，32位字符串", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = { "/channel/pause/{cid}" }, method = { RequestMethod.POST })
    public String pauseChannel(@PathVariable("cid") String cid) {
        return neteaseManager.pauseChannel(cid);
    }

    @ApiOperation(value = "获取频道状态", notes = "http://dev.netease.im/docs/product/直播/服务端API文档?pos=toc-1-3")
    @ApiImplicitParam(name = "cid", value = "频道ID，32位字符串", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = { "/channel/resume/{cid}" }, method = { RequestMethod.GET })
    public String resumeChannel(@PathVariable("cid") String cid) {
        return neteaseManager.getChannelStatus(cid);
    }

}
