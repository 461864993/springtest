package com.example.demo.service;

import com.example.demo.entity.Activation.Activation;
import com.example.demo.entity.Activation.GetActivation;
import com.example.demo.mapper.ActivationMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.tool.MQService;
import com.example.demo.tool.Tool;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class ActivationService {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    ActivationMapper activationMapper;
    @Autowired
    PartnerService partnerService;
    @Autowired
    Tool tool;
//    @Autowired
//    MQService mqService;
    @Autowired
    UserMapper userMapper;
    private static final Logger log = LoggerFactory.getLogger(ActivationService.class);

    public Activation getactivationstatus(String activation_code){
        return activationMapper.getactivationstatus(activation_code);
    }

    public Boolean activationadd(List<Activation> activationList){
        return activationMapper.activationadd(activationList);
    }

//    @Transactional
//    public JSONObject getcode(GetActivation getActivation, BindingResult bindingResult){
//        log.info("开始校验参数...");
//        String return_code = "";
//        List<Activation> activationList = null;
//        JSONObject data = new JSONObject(),result;
//        if(bindingResult.hasErrors()){
//            return_code = "900000";//参数校验失败
//        }else{
//            if(tool.PartnerCheck(stringRedisTemplate,partnerService,getActivation.getPartner_id(),getActivation.getTime(),getActivation.getToken(), "")){
//                if(getActivation.getGet_mode() == 0){
//                    log.info("get_mode = " + getActivation.getGet_mode() + "，Sending Activate code to MQ...");
//                    mqService.MQSend("GetActivation",getActivation);
//                    return_code = "000000";
//                }else{
//                    log.info("get_mode = " + getActivation.getGet_mode() + "，Getting Code of " + getActivation.getActivation_owner() + "...");
//                    activationList = activationMapper.getcodebyowner(getActivation.getActivation_num(),getActivation.getActivation_status(),getActivation.getActivation_owner());
//                    data.put("total",activationList.size());
//                    data.put("activation_code",activationList);
//                    return_code = "000000";
//                }
//            }else{
//                return_code = "900008";//安全校验不通过
//            }
//        }
//        data = tool.JsonHandle(data);
//        result = tool.SetReturnJson(return_code,"",data);
//        log.info("Result:" + result.toString());
//        return result;
//    }

    public Boolean setowner(List<Activation> activationList,String owner){
        return activationMapper.setowner(activationList,owner);
    }

    public void UpdateActivation(GetActivation getActivation){
//        List<Activation> activationList = null;
//        log.info("查询剩余可抢激活码...");
//        activationList = activationMapper.getcodebynoowner(getActivation.getActivation_num(),getActivation.getActivation_status());
//        if(activationList.size() > 0){
//            log.info("有可抢激活码，进入抢购流程...");
//            if(activationMapper.setowner(activationList,getActivation.getActivation_owner())){
//                log.info("抢购成功,激活码" + activationList.get(0).toString() + "...");
//            }else{
//                log.info("抢购失败...");
//            }
//        }else{
//            log.info("无可抢激活码，结束流程...");
//        }
//        if(activationList.size() > 0){
//            log.info("开始给对应激活码写入owner...");
//            if(){
//                return_code = "000000";//成功
//                log.info("写入完成");
//            }else{
//                return_code = "700005";//预定失败
//                activationList.clear();
//            }
//        }else{
//            log.info("激活码被抢光...");
//            return_code = "700004";//激活码被抢光
//        }
    }
}
