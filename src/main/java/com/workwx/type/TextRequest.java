package com.workwx.type;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 企业微信发送消息请求参数
 * @author lwg
 * 2020-08-07
 */

public class TextRequest {

    private String content;
    @JSONField(name = "mentioned_list")
    private List<String> mentionedList;
    @JSONField(name = "mentioned_mobile_list")
    private List<String> mentionedMobileList;

    public TextRequest(String content, List<String> mentionedList, List<String> mentionedMobileList) {
        this.content = content;
        this.mentionedList = mentionedList;
        this.mentionedMobileList = mentionedMobileList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getMentionedList() {
        return mentionedList;
    }

    public void setMentionedList(List<String> mentionedList) {
        this.mentionedList = mentionedList;
    }

    public List<String> getMentionedMobileList() {
        return mentionedMobileList;
    }

    public void setMentionedMobileList(List<String> mentionedMobileList) {
        this.mentionedMobileList = mentionedMobileList;
    }
}
