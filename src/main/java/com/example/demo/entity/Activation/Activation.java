package com.example.demo.entity.Activation;

public class Activation {
    private int id;
    private String activation_code;
    private int activation_status;
    private String activation_account;
    private String code_owner;

    public Activation() {
    }

    public Activation(String activation_code, int activation_status, String activation_account) {
        this.activation_code = activation_code;
        this.activation_status = activation_status;
        this.activation_account = activation_account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivation_code() {
        return activation_code;
    }

    public void setActivation_code(String activation_code) {
        this.activation_code = activation_code;
    }

    public int getActivation_status() {
        return activation_status;
    }

    public void setActivation_status(int activation_status) {
        this.activation_status = activation_status;
    }

    public String getActivation_account() {
        return activation_account;
    }

    public void setActivation_account(String activation_account) {
        this.activation_account = activation_account;
    }

    public String getCode_owner() {
        return code_owner;
    }

    public void setCode_owner(String code_owner) {
        this.code_owner = code_owner;
    }

    @Override
    public String toString() {
        return "{id:" + this.id + ";activation_code:" + this.activation_code + ";activation_status:" + this.activation_status
                + ";activation_account:" + this.activation_account + ";code_owner:" + this.code_owner + "}";
    }
}
