package com.example.demo.entity.Collect;

public class UpdateCollect {
    private String article_owner;
    private int collect_id;
    private String article_name;
    private String article_url;
    private int share_type;
    private int share_secrecy;

    public String getArticle_owner() {
        return article_owner;
    }

    public void setArticle_owner(String article_owner) {
        this.article_owner = article_owner;
    }

    public int getCollect_id() {
        return collect_id;
    }

    public void setCollect_id(int collect_id) {
        this.collect_id = collect_id;
    }

    public String getArticle_name() {
        return article_name;
    }

    public void setArticle_name(String article_name) {
        this.article_name = article_name;
    }

    public String getArticle_url() {
        return article_url;
    }

    public void setArticle_url(String article_url) {
        this.article_url = article_url;
    }

    public int getShare_type() {
        return share_type;
    }

    public void setShare_type(int share_type) {
        this.share_type = share_type;
    }

    public int getShare_secrecy() {
        return share_secrecy;
    }

    public void setShare_secrecy(int share_secrecy) {
        this.share_secrecy = share_secrecy;
    }
}
