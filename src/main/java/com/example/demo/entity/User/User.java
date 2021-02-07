package com.example.demo.entity.User;

public class User {
    private String user_id;
    private String user_name;
    private String user_pwd;
    private String register_time;
    private int user_status;
    private String user_pic;

    public User() {
    }

    public User(String user_id, String user_name, String user_pwd, String register_time, int user_status) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_pwd = user_pwd;
        this.register_time = register_time;
        this.user_status = user_status;
    }

    public User(String user_id, String user_name, String user_pwd, String register_time, int user_status, String user_pic) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_pwd = user_pwd;
        this.register_time = register_time;
        this.user_status = user_status;
        this.user_pic = user_pic;
    }

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

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }

    @Override
    public String toString() {
        return "{user_id:" + this.user_id + ";user_name:" + this.user_name + ";user_pwd:" + this.user_pwd + "register_time"
                + this.register_time + ";user_status:" + this.user_status + ";user_pic:" + this.user_pic + "}";
    }
}
