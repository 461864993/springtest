package com.example.demo.entity.Collect;

import javax.validation.constraints.NotNull;

public class UndoPic {
    @NotNull(message = "path不能为空")
    private String path;

    private String html;

    private String user_id;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
