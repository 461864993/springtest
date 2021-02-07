package com.example.demo.entity.User;

import javax.validation.constraints.NotEmpty;

public class Register_Param {
    @NotEmpty(message = "user_name不能为空")
    private String user_name;
    @NotEmpty(message = "user_pwd不能为空")
    private String user_pwd;
    @NotEmpty(message = "activation_code不能为空")
    private String activation_code;

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

    public String getActivation_code() {
        return activation_code;
    }

    public void setActivation_code(String activation_code) {
        this.activation_code = activation_code;
    }

    @Override
    public String toString() {
        return "{user_name:" + this.user_name + ";user_pwd:" + this.user_pwd + ";activation_code:" + this.activation_code + "}";
    }
}
