package com.example.demo.entity.Collect;

import javax.validation.constraints.NotEmpty;

public class GetAllCollect_Param {
    @NotEmpty(message = "user_id不能为空")
    private String user_id;

    private int lastid;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getLastid() {
        return lastid;
    }

    public void setLastid(int lastid) {
        this.lastid = lastid;
    }

    @Override
    public String toString() {
        return "{user_id:" + this.user_id + "}";
    }
}
