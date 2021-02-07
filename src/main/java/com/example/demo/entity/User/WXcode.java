package com.example.demo.entity.User;

public class WXcode {
    private int id;
    private String openid;
    private String session_key;
    private String expires_in;
    private String user_id;

    public WXcode(String openid, String session_key, String expires_in, String user_id) {
        this.openid = openid;
        this.session_key = session_key;
        this.expires_in = expires_in;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "{id:" + this.id + ";openid;" + this.openid + ";session_key;" + this.session_key +
                ";expires_in;" + this.expires_in + ";user_id;" + this.user_id + "}";
    }
}
