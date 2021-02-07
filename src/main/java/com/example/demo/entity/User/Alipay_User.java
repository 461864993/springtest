package com.example.demo.entity.User;

public class Alipay_User {
    private int id;
    private String alipay_user_id;
    private String access_token;
    private String expires_in;
    private String refresh_token;
    private String re_expires_in;
    private String user_id;

    public Alipay_User(String alipay_user_id, String access_token, String expires_in, String refresh_token, String re_expires_in, String user_id) {
        this.alipay_user_id = alipay_user_id;
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.refresh_token = refresh_token;
        this.re_expires_in = re_expires_in;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlipay_user_id() {
        return alipay_user_id;
    }

    public void setAlipay_user_id(String alipay_user_id) {
        this.alipay_user_id = alipay_user_id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getRe_expires_in() {
        return re_expires_in;
    }

    public void setRe_expires_in(String re_expires_in) {
        this.re_expires_in = re_expires_in;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "{id:" + this.id + ";alipay_user_id:" + this.alipay_user_id + ";access_token:" + this.access_token +
                ";expires_in:" + this.expires_in + ";refresh_token:" + this.refresh_token + ";re_expires_in:" + this.re_expires_in + ";user_id:" + this.user_id + "}";
    }
}
