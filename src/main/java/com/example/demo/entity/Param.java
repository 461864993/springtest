package com.example.demo.entity;

import net.sf.json.JSONObject;

import javax.validation.constraints.NotEmpty;

public class Param {
    @NotEmpty(message = "partner_id不能为空")
    private String partner_id;
    @NotEmpty(message = "time不能为空")
    private String time;
    @NotEmpty(message = "token不能为空")
    private String token;
    @NotEmpty(message = "param不能为空")
    private JSONObject param;

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
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

    public JSONObject getParam() {
        return param;
    }

    public void setParam(JSONObject param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "{partner_id:" + this.partner_id + ";time:" + this.time + ";token:" + this.token + ";param:" + this.param + "}";
    }
}
