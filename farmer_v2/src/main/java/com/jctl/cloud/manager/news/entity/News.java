package com.jctl.cloud.manager.news.entity;

/**
 * Created by Administrator on 2017/3/27.
 */
public class News {
    private int id;
    private String title;
    private String url;
    private String content;
    private String photo;
    private String author;
    private String dataTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }
    public  String toString(){
        return "News:{" +
                "id+"+id+"" +
                ",title" +title+
                ",author" +author+
                ",dataTime"+dataTime +
                ",content"+ content +
                ",photo"+photo +
                ",url" +url+
                "}";
    }
}
