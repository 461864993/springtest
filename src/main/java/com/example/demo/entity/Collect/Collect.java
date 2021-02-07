package com.example.demo.entity.Collect;

public class Collect {
    private int id;
    private String Article_Name;
    private String Article_URL;
    private String Article_Context;
    private String Article_Owner;
    private String Collect_time;
    private int Browse_Count;
    private int Share_Type;
    private  int Share_Secrecy;
    private String Update_time;
    private String Pic_path;

    public Collect() {
    }

    public Collect(String article_Name, String article_URL, String article_Owner, String collect_time, int browse_Count, int share_Type, int share_Secrecy, String pic_path) {
        Article_Name = article_Name;
        Article_URL = article_URL;
        Article_Owner = article_Owner;
        Collect_time = collect_time;
        Browse_Count = browse_Count;
        Share_Type = share_Type;
        Share_Secrecy = share_Secrecy;
        Pic_path = pic_path;
    }

    public Collect(int id, String article_Name, String article_URL, String article_Owner, String collect_time, int browse_Count, int share_Type, int share_Secrecy, String update_time, String pic_path) {
        this.id = id;
        Article_Name = article_Name;
        Article_URL = article_URL;
        Article_Owner = article_Owner;
        Collect_time = collect_time;
        Browse_Count = browse_Count;
        Share_Type = share_Type;
        Share_Secrecy = share_Secrecy;
        Update_time = update_time;
        Pic_path = pic_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArticle_Name() {
        return Article_Name;
    }

    public void setArticle_Name(String article_Name) {
        Article_Name = article_Name;
    }

    public String getArticle_URL() {
        return Article_URL;
    }

    public void setArticle_URL(String article_URL) {
        Article_URL = article_URL;
    }

    public String getArticle_Owner() {
        return Article_Owner;
    }

    public void setArticle_Owner(String article_Owner) {
        Article_Owner = article_Owner;
    }

    public String getCollect_time() {
        return Collect_time;
    }

    public void setCollect_time(String collect_time) {
        Collect_time = collect_time;
    }

    public int getBrowse_Count() {
        return Browse_Count;
    }

    public void setBrowse_Count(int browse_Count) {
        Browse_Count = browse_Count;
    }

    public int getShare_Type() {
        return Share_Type;
    }

    public void setShare_Type(int share_Type) {
        Share_Type = share_Type;
    }

    public int getShare_Secrecy() {
        return Share_Secrecy;
    }

    public void setShare_Secrecy(int share_Secrecy) {
        Share_Secrecy = share_Secrecy;
    }

    public String getUpdate_time() {
        return Update_time;
    }

    public void setUpdate_time(String update_time) {
        Update_time = update_time;
    }

    public String getArticle_Context() {
        return Article_Context;
    }

    public void setArticle_Context(String article_Context) {
        Article_Context = article_Context;
    }

    public String getPic_path() {
        return Pic_path;
    }

    public void setPic_path(String pic_path) {
        Pic_path = pic_path;
    }

    @Override
    public String toString() {
        return "{id:" + this.id + ";Article_Name:" + this.Article_Name + ";Article_URL:" + this.Article_URL + ";Article_Context:" + this.Article_Context +
                ";Article_Owner:" + this.Article_Owner + ";Collect_time:" + this.Collect_time + ";Browse_Count:" + this.Browse_Count +
                 ";Share_Type:" + this.Share_Type + ";Share_Secrecy:" + this.Share_Secrecy  + ";Update_time:" + this.Update_time+ "}";
    }
}
