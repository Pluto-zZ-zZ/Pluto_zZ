package com.workwx.type;

/**
 * 企业微信发送消息请求参数
 * @author lwg
 * 2020-08-07
 */

public class MarkDownRequest {

    private String content;

    public MarkDownRequest(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
