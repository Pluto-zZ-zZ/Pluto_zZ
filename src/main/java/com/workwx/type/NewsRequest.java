package com.workwx.type;

import java.util.List;

/**
 * 企业微信发送消息请求参数
 * @author lwg
 * 2020-08-07
 */

public class NewsRequest {

    private List<Article> articles;

    public NewsRequest(List<Article> articles) {
        this.articles = articles;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public static class Article{
        private String title;
        private String description;
        private String url;
        private String picurl;

        public Article(String title, String description, String url, String picurl) {
            this.title = title;
            this.description = description;
            this.url = url;
            this.picurl = picurl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPicurl() {
            return picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }
    }
}
