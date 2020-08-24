package com.workwx;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 企业微信文件上传返回Body
 * @author lwg
 * 2020-08-07
 */

public class FileUploadResponse {

    private int errcode;
    private String errmsg;
    private String type;
    @JSONField(name = "media_id")
    private String mediaId;

    public FileUploadResponse(int errcode, String errmsg, String type, String mediaId) {
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.type = type;
        this.mediaId = mediaId;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
