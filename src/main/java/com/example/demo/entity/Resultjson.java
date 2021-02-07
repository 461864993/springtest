package com.example.demo.entity;

import net.sf.json.JSONObject;

public class Resultjson {
    private String code;
    private String msg;
    private String time;


    public Resultjson(String code,String time){
        this.code = code;
        switch (code){
            case "000000":
                this.msg = "成功";
                break;
            case "900001":
                this.msg = "密码错误";
                break;
            case "900002":
                this.msg = "用户不存在";
                break;
            case "900008":
                this.msg = "安全校验不通过";
                break;
            case "900009":
                this.msg = "用户已存在";
                break;
            case "999999":
                this.msg = "失败";
                break;
            case "900000":
                this.msg = "参数校验失败";
                break;
            case "900003":
                this.msg = "用户账号已被封停";
                break;
            case "700001":
                this.msg = "激活码暂不可用";
                break;
            case "700002":
                this.msg = "激活码不存在";
                break;
            case "700003":
                this.msg = "激活码已失效";
                break;
            case "700004":
                this.msg = "激活码被抢光";
                break;
            case "700005":
                this.msg = "激活码预定失败";
                break;
            case "600001":
                this.msg = "分享不存在或者不属于您";
                break;
            case "500001":
                this.msg = "用户权限不够";
                break;
            case "400001":
                this.msg = "上传失败，请选择图片";
                break;
            case "400002":
                this.msg = "图片目录已删除";
                break;
            case "400009":
                this.msg = "上传失败";
                break;
            case "300001":
                this.msg = "修改失败，未找到分享或不是您的分享";
                break;
            case "200001":
                this.msg = "微信openid获取失败";
                break;
            case "200002":
                this.msg = "用户session已过期";
                break;
            case "200000":
                this.msg = "第三方登录接口调用失败";
                break;
        }
        this.time = time;
    }

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

    @Override
    public String toString() {
        return "{code:" + this.code + ",msg:" + this.msg + ",time:" + this.time +  "}";
    }
}
