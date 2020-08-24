package com.workwx.type;


import com.alibaba.fastjson.annotation.JSONField;

/**
 * 企业微信发送消息请求参数
 * @author lwg
 * 2020-08-07
 */

public class FileRequest {

    @JSONField(name = "media_id")
    private String mediaId;

    public FileRequest(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
