package com.example.demo.entity.User;

import javax.validation.constraints.NotEmpty;

public class UpdateUser_Param {
    @NotEmpty(message = "user_id不能为空")
    private String user_id;
    @NotEmpty(message = "user_name不能为空")
    private String user_name;
    @NotEmpty (message = "user_pic不能为空")
    private  String user_pic;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }

    @Override
    public String toString() {
        return "{user_id:" + this.user_id + ";user_name:" + this.user_name + ";user_pic:" + this.user_pic + "}";
    }
}
