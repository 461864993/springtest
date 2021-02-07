package com.example.demo.service;

import com.example.demo.entity.Commet.AddCommet;
import com.example.demo.entity.Commet.Commet;
import com.example.demo.entity.Commet.GetCommet;
import com.example.demo.entity.Param;
import com.example.demo.mapper.CommetMapper;
import com.example.demo.tool.Tool;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommetService {
    @Autowired
    CommetMapper commetMapper;
    @Autowired
    Tool tool;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    PartnerService partnerService;
    @Value("${pagelist}")
    private int pagelist;
    private static final Logger log = LoggerFactory.getLogger(CollectService.class);

    public JSONObject GetCommet(Param param, BindingResult bindingResult){
        log.info("Check Param...");
        JSONObject data = new JSONObject(),result;
        String return_code;
        List<Commet> commetList;
        if (tool.CheckParam(param, bindingResult, GetCommet.class)) {
            log.info("Authentication...");
            if (tool.PartnerCheck(stringRedisTemplate, partnerService, param.getPartner_id(), param.getTime(), param.getToken(), String.valueOf(param.getParam()))) {
                GetCommet getCommet = (GetCommet)JSONObject.toBean(param.getParam(), GetCommet.class);
                log.info(getCommet.getLastid() + "");
                if(getCommet.getLastid() == -1){
                    commetList = commetMapper.getcommet(getCommet.getCollect_id(),pagelist);
                }else{
                    commetList = commetMapper.reloadcommet(getCommet.getCollect_id(),pagelist,getCommet.getLastid());
                }
                data.put("total", commetList.size());
                data.put("commetlist", tool.dealCommet(commetList));
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

    public JSONObject AddCommet(Param param, BindingResult bindingResult){
        log.info("Check Param...");
        JSONObject data = new JSONObject(),result;
        String return_code;
        if (tool.CheckParam(param, bindingResult, AddCommet.class)) {
            log.info("Authentication...");
            if (tool.PartnerCheck(stringRedisTemplate, partnerService, param.getPartner_id(), param.getTime(), param.getToken(), String.valueOf(param.getParam()))) {
                AddCommet addCommet = (AddCommet)JSONObject.toBean(param.getParam(), AddCommet.class);
                Commet commet = new Commet(addCommet.getCommet_context(), addCommet.getCollect_id(), addCommet.getCommet_owner(),addCommet.getOwner_pic(), addCommet.getCommet_time(),0);
                if(commetMapper.addcommet(commet)){
                    return_code = "000000";//成功
                    List<Commet> commets = new ArrayList<>();
                    commets.add(commet);
                    data.put("commet", tool.dealCommet(commets));
                }else{
                    return_code = "999999";//失败
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
}
