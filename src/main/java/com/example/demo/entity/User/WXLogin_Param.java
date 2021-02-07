package com.example.demo.entity.User;

import javax.validation.constraints.NotEmpty;

public class WXLogin_Param {
    @NotEmpty(message = "code不能为空")
    private String code;
    @NotEmpty(message = "nickName不能为空")
    private String nickName;
    @NotEmpty(message = "avatarUrl不能为空")
    private String avatarUrl;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "{pcode:" + this.code + ";nickName:" + this.nickName + ";avatarUrl:" + this.avatarUrl + "}";
    }
}
