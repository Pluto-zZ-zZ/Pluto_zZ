package com.workwx;

import com.alibaba.fastjson.JSON;
import okhttp3.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OkHttpUtils {

    public enum ContentType{
        APPLICATION_JSON(okhttp3.MediaType.parse("application/json;charset=UTF-8")),
        MULTIPART_FORM(MultipartBody.FORM)
        ;

        private okhttp3.MediaType mediaType;

        ContentType(okhttp3.MediaType mediaType) {
            this.mediaType = mediaType;
        }

        public okhttp3.MediaType getMediaType() {
            return mediaType;
        }

        public void setMediaType(okhttp3.MediaType mediaType) {
            this.mediaType = mediaType;
        }
    }

    public static final int SUCCESS_CODE = 0;

    public static class Response{
        private int code;
        private String msg;
        private String content;

        public Response(int code, String msg, String content) {
            this.code = code;
            this.msg = msg;
            this.content = content;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        public String getContent() {
            return content;
        }

        public boolean success(){
            return this.code == SUCCESS_CODE;
        }
    }

    /**
     * 设置 HTTP 请求基本信息
     * @param requestBuilder
     * @param header
     * @param url
     * @return
     */
    private static OkHttpClient setHttpBase(Request.Builder requestBuilder, Map<String,String> header, String url){
        // 设置请求头
        if(Objects.nonNull(header)){
            Headers.Builder headerBuilder = new Headers.Builder();
            header.forEach(headerBuilder::add);
            requestBuilder.headers(headerBuilder.build());
        }

        // URL
        requestBuilder.url(url);
        // 超时时间
        return new OkHttpClient().newBuilder().connectTimeout(3, TimeUnit.SECONDS).build();
    }

    /**
     * Okhttp3 Get请求
     * @param header
     * @param url
     * @return
     */
    public static Response httpGet(Map<String,String> header, String url){
        Request.Builder requestBuilder = new Request.Builder();
        OkHttpClient okHttpClient = setHttpBase(requestBuilder, header, url);

        try {
            okhttp3.Response response = okHttpClient.newCall(requestBuilder.get().build()).execute();
            return new Response(SUCCESS_CODE, null, response.body().string());

        } catch (Exception e) {
            e.printStackTrace();
            return new Response(1, e.getMessage(), null);
        }
    }


    /**
     * Okhttp3 Post请求
     * @param header
     * @param url
     * @param params
     * @param contentType
     * @return
     */
    public static Response httpPost(Map<String,String> header, String url, ContentType contentType, Map<String, Object> params){
        Request.Builder requestBuilder = new Request.Builder();
        OkHttpClient okHttpClient = setHttpBase(requestBuilder, header, url);
        requestBuilder.addHeader("Content-Type", contentType.mediaType.toString());

        if(Objects.isNull(params)){
            params = new HashMap<>();
        }
        // Post Body
        RequestBody requestBody;
        if(contentType == ContentType.MULTIPART_FORM){
            // form 表单提交
            MultipartBody.Builder formBodyBuilder = new MultipartBody.Builder().setType(contentType.mediaType);
            params.forEach((k, v) -> formBodyBuilder.addFormDataPart(k, (String) v));
            requestBody = formBodyBuilder.build();
        }else if(contentType == ContentType.APPLICATION_JSON){
            // JSON 格式
            requestBody = RequestBody.create(contentType.getMediaType(), JSON.toJSONString(params));
        }else{
            throw new RuntimeException("非法的ContentType");
        }

        try {
            okhttp3.Response response = okHttpClient.newCall(requestBuilder.post(requestBody).build()).execute();
            return new Response(SUCCESS_CODE, null, response.body().string());

        } catch (Exception e) {
            e.printStackTrace();
            return new Response(1, e.getMessage(), null);
        }
    }


    /**
     * Okhttp3 Post表单上传文件
     * @param header
     * @param url
     * @param params
     * @param file
     * @param fileAttrName
     * @return
     */
    public static Response httpPostFile(Map<String,String> header, String url, Map<String, Object> params,
                                        File file, String fileAttrName){
        Request.Builder requestBuilder = new Request.Builder();
        OkHttpClient okHttpClient = setHttpBase(requestBuilder, header, url);
        requestBuilder.addHeader("Content-Type", MultipartBody.FORM.toString());

        if(Objects.isNull(params)){
            params = new HashMap<>();
        }
        // Post Body
        RequestBody requestBody;
        // form 表单提交
        MultipartBody.Builder formBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        params.forEach((k, v) -> formBodyBuilder.addFormDataPart(k, (String) v));
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        // 文件放入报文
        formBodyBuilder.addFormDataPart(fileAttrName, file.getName(), fileBody);

        requestBody = formBodyBuilder.build();
        try {
            okhttp3.Response response = okHttpClient.newCall(requestBuilder.post(requestBody).build()).execute();
            return new Response(SUCCESS_CODE, null, response.body().string());

        } catch (Exception e) {
            e.printStackTrace();
            return new Response(1, e.getMessage(), null);
        }
    }

}
