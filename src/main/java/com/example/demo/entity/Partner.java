package com.example.demo.entity;

public class Partner {
    private int id;
    private String partner_name;
    private String partner_id;
    private String partner_secret;

    public Partner(){}

    public Partner(String partner_id,String partner_secret){
        this.partner_id = partner_id;
        this.partner_secret = partner_secret;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPartner_name() {
        return partner_name;
    }

    public void setPartner_name(String partner_name) {
        this.partner_name = partner_name;
    }

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public String getPartner_secret() {
        return partner_secret;
    }

    public void setPartner_secret(String partner_secret) {
        this.partner_secret = partner_secret;
    }

    @Override
    public String toString() {
        return "{id:" + this.id + ";partner_name:" + this.partner_name + ";partner_id:" + this.partner_id + ";partner_secret:" + this.getPartner_secret() + "}";
    }
}
