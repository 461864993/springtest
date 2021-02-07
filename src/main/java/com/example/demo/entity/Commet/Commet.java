package com.example.demo.entity.Commet;

public class Commet {
    private int id;
    private String commet_context;
    private int collect_id;
    private String commet_owner;
    private String owner_pic;
    private String commet_time;
    private int praise_count;

    public Commet() {
    }

    public Commet(String commet_context, int collect_id, String commet_owner, String owner_pic, String commet_time, int praise_count) {
        this.commet_context = commet_context;
        this.collect_id = collect_id;
        this.commet_owner = commet_owner;
        this.owner_pic = owner_pic;
        this.commet_time = commet_time;
        this.praise_count = praise_count;
    }

    public Commet(String commet_context, int collect_id, String commet_owner, String commet_time, int praise_count) {
        this.commet_context = commet_context;
        this.collect_id = collect_id;
        this.commet_owner = commet_owner;
        this.commet_time = commet_time;
        this.praise_count = praise_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommet_context() {
        return commet_context;
    }

    public void setCommet_context(String commet_context) {
        this.commet_context = commet_context;
    }

    public int getCollect_id() {
        return collect_id;
    }

    public void setCollect_id(int collect_id) {
        this.collect_id = collect_id;
    }

    public String getCommet_owner() {
        return commet_owner;
    }

    public void setCommet_owner(String commet_owner) {
        this.commet_owner = commet_owner;
    }

    public String getOwner_pic() {
        return owner_pic;
    }

    public void setOwner_pic(String owner_pic) {
        this.owner_pic = owner_pic;
    }

    public String getCommet_time() {
        return commet_time;
    }

    public void setCommet_time(String commet_time) {
        this.commet_time = commet_time;
    }

    public int getPraise_count() {
        return praise_count;
    }

    public void setPraise_count(int praise_count) {
        this.praise_count = praise_count;
    }
}
