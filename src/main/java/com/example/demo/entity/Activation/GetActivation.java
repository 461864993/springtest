package com.example.demo.entity.Activation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GetActivation {
    @NotEmpty(message = "partner_id不能为空")
    private String partner_id;
    @NotNull(message = "activation_num不能为空")
    private int activation_num;
    @NotNull(message = "activation_status不能为空")
    private int activation_status;
    @NotEmpty(message = "activation_owner不能为空")
    private String activation_owner;
    @NotNull(message = "get_mode不能为空")
    private int get_mode;
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

    public int getActivation_num() {
        return activation_num;
    }

    public void setActivation_num(int activation_num) {
        this.activation_num = activation_num;
    }

    public int getActivation_status() {
        return activation_status;
    }

    public void setActivation_status(int activation_status) {
        this.activation_status = activation_status;
    }

    public String getActivation_owner() {
        return activation_owner;
    }

    public void setActivation_owner(String activation_owner) {
        this.activation_owner = activation_owner;
    }

    public int getGet_mode() {
        return get_mode;
    }

    public void setGet_mode(int get_mode) {
        this.get_mode = get_mode;
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
        return "{activation_num:" + this.activation_num + ";activation_status:" + this.activation_status + ";activation_owner:" + this.activation_owner
                + ";get_mode:" + this.get_mode + ";partner_id:" + this.partner_id + ";time:" + this.time + ";token:" + this.token;
    }
}
