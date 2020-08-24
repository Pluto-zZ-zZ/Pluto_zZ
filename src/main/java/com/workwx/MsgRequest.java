package com.workwx;

import com.alibaba.fastjson.JSONObject;
import com.lrts.workwx.type.*;
import com.workwx.type.*;

import java.util.List;


/**
 * 企业微信发送消息请求参数
 * @author lwg
 * 2020-08-07
 */

public class MsgRequest {

    /** 公共参数 属性名 */
    private static final String MSG_TYPE_NAME = "msgtype";

    /**
     * 公共构建引用
     * @param msgType
     * @param t
     * @param <T>
     * @return
     */
    private static <T> JSONObject build(MsgConstant.MsgType msgType, T t){
        JSONObject json = new JSONObject();
        json.put(MSG_TYPE_NAME, msgType.getType());
        json.put(msgType.getType() , t);

        return json;
    }



    /**
     * 构建Text格式消息
     * @param content
     * @param mentionedList
     * @param mentionedMobileList
     * @return
     */
    public static JSONObject buildTextMsg(String content, List<String> mentionedList, List<String> mentionedMobileList){
        return build(MsgConstant.MsgType.TEXT, new TextRequest(content, mentionedList, mentionedMobileList));
    }


    /**
     * 构建Markdown格式消息
     * @param content
     * @return
     */
    public static JSONObject buildMarkdownMsg(String content){
        return build(MsgConstant.MsgType.MARKDOWN, new MarkDownRequest(content));
    }


    /**
     * 构建图片消息
     * @param base64
     * @param md5
     * @return
     */
    public static JSONObject buildImageMsg(String base64, String md5){
        return build(MsgConstant.MsgType.IMAGE, new ImageRequest(base64, md5));
    }


    /**
     * 构建图文消息
     * @param articleList
     * @return
     */
    public static JSONObject buildNewsMsg(List<NewsRequest.Article> articleList){
        return build(MsgConstant.MsgType.NEWS, new NewsRequest(articleList));
    }


    /**
     * 构建文件消息
     * @param mediaId
     * @return
     */
    public static JSONObject buildFileMsg(String mediaId){
        return build(MsgConstant.MsgType.FILE, new FileRequest(mediaId));
    }

}
