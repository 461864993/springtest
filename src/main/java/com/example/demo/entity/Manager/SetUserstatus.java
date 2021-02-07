package com.example.demo.entity.Manager;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SetUserstatus {
    @NotEmpty(message = "user_id不能为空")
    private String user_id;
    @NotEmpty (message = "partner_id不能为空")
    private String partner_id;
    @NotNull (message = "user_status不能为空")
//    @NotEmpty (message = "user_status不能为空")
    private int user_status;
    @NotEmpty (message = "time不能为空")
    private String time;
    @NotEmpty (message = "token不能为空")
    private String token;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
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
        return "{user_id:" + this.user_id + ";user_status:" + this.user_status + ";partner_id:" + this.partner_id
                + ";time:" + this.time + ";token:" + this.token;
    }
}
