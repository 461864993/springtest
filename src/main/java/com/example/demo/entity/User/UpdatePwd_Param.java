package com.example.demo.entity.User;

import javax.validation.constraints.NotEmpty;

public class UpdatePwd_Param {
    @NotEmpty (message = "user_id不能为空")
    private String user_id;
    @NotEmpty (message = "user_pwd不能为空")
    private String user_pwd;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    @Override
    public String toString() {
        return "{user_id:" + this.user_id + ";user_pwd:" + this.user_pwd + "}";
    }
}
