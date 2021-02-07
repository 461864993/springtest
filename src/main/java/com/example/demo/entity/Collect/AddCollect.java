package com.example.demo.entity.Collect;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddCollect {
    @NotEmpty(message = "article_name不能为空")
    private String article_name;
    @NotEmpty(message = "article_url不能为空")
    private String article_url;
    @NotEmpty(message = "article_owner不能为空")
    private String article_owner;
    @NotNull(message = "share_type不能为空")
    private int share_type;
    @NotNull(message = "share_secrecy不能为空")
    private int share_secrecy;

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

    public String getArticle_owner() {
        return article_owner;
    }

    public void setArticle_owner(String article_owner) {
        this.article_owner = article_owner;
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

    @Override
    public String toString() {
        return "{Article_Name:" + this.article_name + ";Article_URL:" + this.article_url + ";Article_Owner:" + this.article_owner +
                ";share_type:" + this.share_type + ";share_secrecy:" + this.share_secrecy + "}";
    }
}
