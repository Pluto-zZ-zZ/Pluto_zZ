package com.workwx;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.workwx.type.NewsRequest;
import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static com.workwx.MsgConstant.FileUploadType;
import static com.workwx.OkHttpUtils.ContentType;
import static com.workwx.OkHttpUtils.Response;

public class WorkWXRobotUtils {

    /**
     * 发件Text格式消息
     * @param webHookUrl
     * @param msg
     * @return
     */
    public static boolean sendTextMsg(String webHookUrl, String msg){
        return sendTextMsg(webHookUrl, msg, null ,null);
    }

    public static boolean sendTextMsg(String webHookUrl, String msg, List<String> mentionedList, List<String> mentionedMobileList){
        Response response = OkHttpUtils.httpPost(null, webHookUrl, ContentType.APPLICATION_JSON,
                MsgRequest.buildTextMsg(msg, mentionedList, mentionedMobileList));

        return response.success();
    }


    /**
     * 发送Markdown格式消息
     * @param webHookUrl
     * @param msg
     * @return
     */
    public static boolean sendMarkdownMsg(String webHookUrl, String msg){
        Response response = OkHttpUtils.httpPost(null, webHookUrl, ContentType.APPLICATION_JSON,
                MsgRequest.buildMarkdownMsg(msg));

        return response.success();
    }


    /**
     * 发送图片消息
     * @param webHookUrl
     * @param base64
     * @param md5
     * @return
     */
    public static boolean sendImageMsg(String webHookUrl, String base64, String md5){
        // base64不能有空格
        Response response = OkHttpUtils.httpPost(null, webHookUrl, ContentType.APPLICATION_JSON,
                MsgRequest.buildImageMsg(base64.replaceAll("\r\n",""), md5));

        return response.success();
    }

    public static boolean sendImageMsg(String webHookUrl, String absolutePath){
        byte[] data;
        try {
            data = Files.readAllBytes(Paths.get(absolutePath));
        } catch (IOException e) {
            throw new RuntimeException("图片获取失败", e);
        }
        String base64 = new BASE64Encoder().encode(data);
        String md5 = DigestUtils.md5Hex(data);

        return sendImageMsg(webHookUrl, base64, md5);
    }


    /**
     * 发送图文消息
     * @param webHookUrl
     * @param title
     * @param description
     * @param url
     * @param picurl
     * @return
     */
    public static boolean sendNewsMsg(String webHookUrl, String title, String description, String url, String picurl){
        return sendNewsMsg(webHookUrl, Collections.singletonList(new NewsRequest.Article(title, description, url, picurl)));
    }

    public static boolean sendNewsMsg(String webHookUrl, List<NewsRequest.Article> articleList){
        Response response = OkHttpUtils.httpPost(null, webHookUrl, ContentType.APPLICATION_JSON,
                MsgRequest.buildNewsMsg(articleList));

        return response.success();
    }


    /** 企业微信文件上传地址 */
    private static final String FILE_UPLOAD_URL = "https://qyapi.weixin.qq.com/cgi-bin/webhook/upload_media?key=%s&type=%s";

    /**
     * 发送文件消息
     * @param webHookUrl
     * @param fileType
     * @param file
     * @return
     */
    public static boolean sendFileMsg(String webHookUrl, FileUploadType fileType, File file){
        // 先上传
        String key = webHookUrl.split("key=")[1];
        Response response = OkHttpUtils.httpPostFile(null, String.format(FILE_UPLOAD_URL, key, fileType.getType()),
                null, file, "media");
        if(!response.success()){
            return false;
        }

        // 解析上传结果
        FileUploadResponse fileUploadResponse = JSON.parseObject(response.getContent(), FileUploadResponse.class);
        if(fileUploadResponse.getErrcode() != 0){
            return false;
        }

        // 发送文件消息
        response = OkHttpUtils.httpPost(null, webHookUrl, ContentType.APPLICATION_JSON,
                MsgRequest.buildFileMsg(fileUploadResponse.getMediaId()));

        return response.success();
    }


    public static <T> boolean sendExcelMsgByList(String webHookUrl, String filePath, Class<T> cls, List<T> list){
        // 写本地文件
        File file = new File(filePath);
        EasyExcel.write(file).head(cls).sheet().doWrite(list);

        // 发送文件消息
        if(sendFileMsg(webHookUrl, FileUploadType.FILE, file)){
            file.delete();
            return true;
        }
        return false;
    }


    public static void main(String[] args) throws Exception{
        String webHookUrl = "";
        sendTextMsg(webHookUrl, "TEXT格式内容测试");


//        sendMarkdownMsg(webHookUrl, "# 我是标题\n **加粗**\n <font color=\"info\">绿色</font>\n" +
//                "<font color=\"comment\">灰色</font>\n" +
//                "<font color=\"warning\">橙红色</font>");


        //sendImageMsg(webHookUrl, "E:\\Pluto\\images\\wallpaper\\126438.png");


        //sendNewsMsg(webHookUrl, "图文消息测试", "图文消息", "www.baidu.com", "https://images8.alphacoders.com/109/thumb-1920-1092729.jpg");


//        List<NewsRequest.Article> articleList = new ArrayList<>();
//        articleList.add(new NewsRequest.Article("批量图文消息测试111", "图文消息111", "www.google.com", "https://images.alphacoders.com/605/thumb-1920-605592.png"));
//        articleList.add(new NewsRequest.Article("批量图文消息测试222", "图文消息222", "www.bilibili.com", "https://images.alphacoders.com/598/thumb-1920-598846.jpg"));
//        articleList.add(new NewsRequest.Article("批量图文消息测试333", "图文消息333", "www.github.com", "https://images3.alphacoders.com/144/thumb-1920-144565.jpg"));
//        sendNewsMsg(webHookUrl, articleList);


        //sendFileMsg(webHookUrl, MsgConstant.FileUploadType.FILE, new File("C:\\Users\\admin\\Desktop\\新建 XLSX 工作表.xlsx"));

    }


}
