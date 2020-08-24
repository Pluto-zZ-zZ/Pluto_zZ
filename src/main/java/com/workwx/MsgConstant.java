package com.workwx;

/**
 * 消息类型枚举
 * @author lwg
 * 2020-08-07
 */

public class MsgConstant{

    public enum MsgType{
        TEXT("text"),
        MARKDOWN("markdown"),
        IMAGE("image"),
        NEWS("news"),
        FILE("file");

        private String type;

        MsgType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public enum FileUploadType{
        IMAGE("image"),
        VOICE("voice"),
        VIDEO("video"),
        FILE("file");

        private String type;

        FileUploadType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

}
