package com.workwx.type;


/**
 * 企业微信发送消息请求参数
 * @author lwg
 * 2020-08-07
 */

public class ImageRequest {

    private String base64;
    private String md5;

    public ImageRequest(String base64, String md5) {
        this.base64 = base64;
        this.md5 = md5;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
