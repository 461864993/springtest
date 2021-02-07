package com.example.demo.service;

import com.example.demo.entity.Collect.*;
import com.example.demo.entity.Param;
import com.example.demo.mapper.CollectMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.tool.Tool;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import java.net.HttpURLConnection;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.io.PrintWriter;
import java.util.HashMap;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class CollectService {
    @Autowired
    CollectMapper collectionMapper;
    @Autowired
    Tool tool;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    PartnerService partnerService;
    @Autowired
    UserMapper userMapper;
    @Value("${filepath}")
    private String filepath;
    @Value("${pagelist}")
    private int pagelist;
    private static final Logger log = LoggerFactory.getLogger(CollectService.class);
    public JSONObject GetAllColection(Param param, BindingResult bindingResult){
        log.info("CheckParam...");
        JSONObject data = new JSONObject(),result;
        List<Collect> collectionList;
        String return_code;
        if(tool.CheckParam(param,bindingResult, GetAllCollect_Param.class)){
            log.info("Authentication...");

            byte[] destr = tool.encrypt("test","key");
            log.info("destr：" + new String(destr));
            byte[] enstr = tool.decrypt(destr,"key");
            log.info("enstr:" + new String(enstr));
            if(tool.PartnerCheck(stringRedisTemplate,partnerService,param.getPartner_id(),param.getTime(),param.getToken(), String.valueOf(param.getParam()))){
                GetAllCollect_Param getAllCollect_param = (GetAllCollect_Param)JSONObject.toBean(param.getParam(),GetAllCollect_Param.class);
                if(getAllCollect_param.getLastid() == -1){
                    collectionList = collectionMapper.gettopcolection(getAllCollect_param.getUser_id(), pagelist);
                }else{
                    collectionList = collectionMapper.reloadcollect(getAllCollect_param.getUser_id(), getAllCollect_param.getLastid(), pagelist);
                }

                data.put("total",collectionList.size());
                data.put("CollectionList",tool.dealCollect(collectionList));
                return_code = "000000";//成功
            }else{
                return_code = "900008";//安全校验不通过
            }
        }else{
            return_code = "900000";//参数校验失败，缺少参数或部分参数为空
        }
        data = tool.JsonHandle(data);
        result = tool.SetReturnJson(return_code,"",data);
        log.info("Result:" + result.toString());
        return result;
    }

    public JSONObject AddCollect(Param param, BindingResult bindingResult){
        log.info("CheckParam...");
        JSONObject data = new JSONObject(),result;
        String return_code;
        if(tool.CheckParam(param,bindingResult, AddCollect.class)){
            log.info("Authentication...");
            if(tool.PartnerCheck(stringRedisTemplate,partnerService,param.getPartner_id(),param.getTime(),param.getToken(), String.valueOf(param.getParam()))){
                AddCollect addCollect = (AddCollect)JSONObject.toBean(param.getParam(),AddCollect.class);
                Collect collect = new Collect(addCollect.getArticle_name(),addCollect.getArticle_url(),addCollect.getArticle_owner(),
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),0,addCollect.getShare_type(), addCollect.getShare_secrecy(),param.getParam().get("pic_path").toString());
                if(collectionMapper.addcollect(collect)){
                    return_code = "000000";//成功
//                    if(addCollect.getShare_type() == 2){
//                        if(param.getParam().get("pic_path").toString() != "") {
//                            tool.Checkpiclist(addCollect.getArticle_url(),filepath + param.getParam().get("pic_path").toString());
//                        }
//                    }
                }else{
                    return_code = "999999";
                }
            }else{
                return_code = "900008";//安全校验不通过
            }
        }else{
            return_code = "900000";//参数校验失败，缺少参数或部分参数为空
        }
        data = tool.JsonHandle(data);
        result = tool.SetReturnJson(return_code,"",data);
        log.info("Result:" + result.toString());
        return result;
    }
//    @Async("taskExecutor")
    public JSONObject UndoPic(Param param,BindingResult bindingResult,String filepath) {
        log.info("Check Param...");
        JSONObject data = new JSONObject(),result;
        String return_code;
        if (tool.CheckParam(param, bindingResult, UndoPic.class)) {
            log.info("Authentication...");
            if (tool.PartnerCheck(stringRedisTemplate, partnerService, param.getPartner_id(), param.getTime(), param.getToken(), String.valueOf(param.getParam()))) {
                UndoPic undoPic = (UndoPic) JSONObject.toBean(param.getParam(), UndoPic.class);
                if(tool.Checkpiclist(undoPic.getHtml(),filepath + undoPic.getPath()) == 1){
                    return_code = "000000";//正常处理
                }else{
                    return_code = "400002";//图片目录已删除
                }
            }else{
                return_code = "900008";//安全校验不通过
            }
        }else{
            return_code = "900000";//参数校验失败，缺少参数或部分参数为空
        }
        data = tool.JsonHandle(data);
        result = tool.SetReturnJson(return_code,"",data);
        log.info("Result:" + result.toString());
        return result;
    }

    public JSONObject UpdateCollect(Param param,BindingResult bindingResult){
        log.info("Check Param...");
        JSONObject data = new JSONObject(),result;
        String return_code;
        if(tool.CheckParam(param,bindingResult, UpdateCollect.class)){
            log.info("Authentication...");
            if(tool.PartnerCheck(stringRedisTemplate,partnerService,param.getPartner_id(),param.getTime(),param.getToken(), String.valueOf(param.getParam()))){
                UpdateCollect updateCollect = (UpdateCollect)JSONObject.toBean(param.getParam(),UpdateCollect.class);
                Collect collect = new Collect(updateCollect.getCollect_id(),updateCollect.getArticle_name(),updateCollect.getArticle_url(),
                        updateCollect.getArticle_owner(),null,0,updateCollect.getShare_type(),
                        updateCollect.getShare_secrecy(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),param.getParam().get("pic_path").toString());
                if(collectionMapper.updatecollect(collect)){
                    return_code = "000000";//成功
//                    if(updateCollect.getShare_type() == 2){
//                        tool.Checkpiclist(updateCollect.getArticle_url(),filepath + param.getParam().get("pic_path"));
//                    }
                }else{
                    return_code = "300001";//修改失败，未找到分享或不是您的分享
                }
            }else{
                return_code = "900008";//安全校验不通过
            }
        }else{
            return_code = "900000";//参数校验失败，缺少参数或部分参数为空
        }
        data = tool.JsonHandle(data);
        result = tool.SetReturnJson(return_code,"",data);
        log.info("Result:" + result.toString());
        return result;
    }

    public JSONObject getfromURL(Param param, BindingResult bindingResult){
        log.info("Check Param...");
        JSONObject data = new JSONObject(),result;
        String return_code;
        if(tool.CheckParam(param,bindingResult, null)){
            log.info("Authentication...");
            if(tool.PartnerCheck(stringRedisTemplate,partnerService,param.getPartner_id(),param.getTime(),param.getToken(), String.valueOf(param.getParam()))){
                String url = tool.dealUrl(param.getParam().getString("url"));
                log.info(url);
                if(url != null && !url.equals("")) {
                    try {
                        Document doc= Jsoup.parse(new URL(url),5000);
                        Elements body = doc.getElementsByTag("body");
                        Elements title = doc.getElementsByTag("title");
                        body.select("script").remove();
                        data.put("url", url);
                        data.put("title", title.text());
                        data.put("body", body.toString());
                        return_code = "000000";//成功
                    } catch (IOException e) {
                        e.printStackTrace();
                        return_code = "999999";
                    }
                }else{
                    return_code = "900000";//参数校验失败，缺少参数或部分参数为空
                }
            }else{
                return_code = "900008";//安全校验不通过
            }
        }else{
            return_code = "900000";//参数校验失败，缺少参数或部分参数为空
        }
        data = tool.JsonHandle(data);
        result = tool.SetReturnJson(return_code,"",data);
        log.info("Result:" + result.toString());
        return result;
    }

    public JSONObject GetMyColection(Param param, BindingResult bindingResult){
        log.info("Check Param...");
        JSONObject data = new JSONObject(),result;
        List<Collect> collectionList;
        String return_code;
        if(tool.CheckParam(param,bindingResult, GetAllCollect_Param.class)){
            log.info("Authentication...");
            if(tool.PartnerCheck(stringRedisTemplate,partnerService,param.getPartner_id(),param.getTime(),param.getToken(), String.valueOf(param.getParam()))){
                GetAllCollect_Param getAllCollect_param = (GetAllCollect_Param)JSONObject.toBean(param.getParam(),GetAllCollect_Param.class);
                if(getAllCollect_param.getLastid() == -1){
                    collectionList = collectionMapper.getmycollect(getAllCollect_param.getUser_id(), pagelist);
                }else{
                    collectionList = collectionMapper.reloadmycollect(getAllCollect_param.getUser_id(), getAllCollect_param.getLastid(), pagelist);
                }
                data.put("total",collectionList.size());
                data.put("CollectionList",collectionList);
                return_code = "000000";//成功
            }else{
                return_code = "900008";//安全校验不通过
            }
        }else{
            return_code = "900000";//参数校验失败，缺少参数或部分参数为空
        }
        data = tool.JsonHandle(data);
        result = tool.SetReturnJson(return_code,"",data);
        log.info("Result:" + result.toString());
        return result;
    }

    public JSONObject DelCollect(Param param, BindingResult bindingResult){
        log.info("Check Param...");
        JSONObject data = new JSONObject(),result;
        String return_code;
        if(tool.CheckParam(param,bindingResult, DelCollect.class)){
            log.info("Authentication...");
            if(tool.PartnerCheck(stringRedisTemplate,partnerService,param.getPartner_id(),param.getTime(),param.getToken(), String.valueOf(param.getParam()))){
                DelCollect delCollect = (DelCollect)JSONObject.toBean(param.getParam(),DelCollect.class);
                if(collectionMapper.delcollect(delCollect.getUser_id(),delCollect.getCollect_id())){
                    return_code = "000000";//成功
                }else{
                    return_code = "600001";//分享不存在或者不属于您
                }
            }else{
                return_code = "900008";//安全校验不通过
            }
        }else{
            return_code = "900000";//参数校验失败，缺少参数或部分参数为空
        }
        data = tool.JsonHandle(data);
        result = tool.SetReturnJson(return_code,"",data);
        log.info("Result:" + result.toString());
        return result;
    }

    public JSONObject SearchCollect(Param param, BindingResult bindingResult){
        log.info("Check Param...");
        JSONObject data = new JSONObject(),result;
        List<Collect> collectionList;
        String return_code;
        if(tool.CheckParam(param,bindingResult, SearchCollect.class)){
            log.info("Authentication...");
            if(tool.PartnerCheck(stringRedisTemplate,partnerService,param.getPartner_id(),param.getTime(),param.getToken(), String.valueOf(param.getParam()))){
                SearchCollect searchCollect = (SearchCollect)JSONObject.toBean(param.getParam(),SearchCollect.class);
                if(searchCollect.getLast_id() == -1){
                    collectionList = collectionMapper.searchcollect(searchCollect.getSearch_text(), pagelist, searchCollect.getUser_id());
                }else{
                    collectionList = collectionMapper.reloadsearchcollect(searchCollect.getSearch_text(),searchCollect.getLast_id(), pagelist, searchCollect.getUser_id());
                }
                data.put("total",collectionList.size());
                data.put("CollectionList",collectionList);
                return_code = "000000";//成功
            }else{
                return_code = "900008";//安全校验不通过
            }
        }else{
            return_code = "900000";//参数校验失败，缺少参数或部分参数为空
        }
        data = tool.JsonHandle(data);
        result = tool.SetReturnJson(return_code,"",data);
        log.info("Result:" + result.toString());
        return result;
    }

    public JSONObject GetMyCollectNum(Param param,BindingResult bindingResult){
        log.info("Check Param...");
        JSONObject data = new JSONObject(),result;
        String return_code;
        if(bindingResult.hasErrors() || param.getParam().get("user_id") == ""){
            return_code = "900000";//参数校验失败，缺少参数或部分参数为空
        }else{
            log.info("Authentication...");
            if(tool.PartnerCheck(stringRedisTemplate,partnerService,param.getPartner_id(),param.getTime(),param.getToken(), String.valueOf(param.getParam()))){
                data.put("collectcount",collectionMapper.getmycollectnum(param.getParam().get("user_id").toString()));
                return_code = "000000";//成功
            }else{
                return_code = "900008";//安全校验不通过
            }
        }
        data = tool.JsonHandle(data);
        result = tool.SetReturnJson(return_code,"",data);
        log.info("Result:" + result.toString());
        return result;
    }
}
