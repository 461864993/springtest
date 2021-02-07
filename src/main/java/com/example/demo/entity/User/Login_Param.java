package com.example.demo.entity.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Login_Param {
    @NotEmpty(message = "user_name不能为空")
    private String user_name;
    @NotEmpty(message = "user_pwd不能为空")
    private String user_pwd;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    @Override
    public String toString() {
        return "{user_name:" + this.user_name + ";user_pwd:" + this.user_pwd + "}";
    }
}
