package com.example.demo.entity.User;

import javax.validation.constraints.NotEmpty;

public class WXAddname {
    @NotEmpty(message = "partner_id不能为空")
    private String partner_id;
    @NotEmpty(message = "user_id不能为空")
    private String user_id;
    @NotEmpty(message = "encryptedData不能为空")
    private String encryptedData;
    @NotEmpty(message = "iv不能为空")
    private String iv;
    @NotEmpty(message = "time不能为空")
    private String time;
    @NotEmpty(message = "token不能为空")
    private String token;

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "{partner_id:" + this.partner_id + ";user_id:" + this.user_id + ";encryptedData:" + this.encryptedData +
                ";iv:" + this.iv + ";time:" + this.time + ";token:" + this.token + "}";
    }
}
