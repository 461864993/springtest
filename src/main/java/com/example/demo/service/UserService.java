package com.example.demo.service;

//import com.alipay.api.AlipayApiException;
//import com.alipay.api.AlipayClient;
//import com.alipay.api.DefaultAlipayClient;
//import com.alipay.api.request.AlipaySystemOauthTokenRequest;
//import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.example.demo.entity.Activation.Activation;
import com.example.demo.entity.Manager.SetUserstatus;
import com.example.demo.entity.Param;
import com.example.demo.entity.User.*;
import com.example.demo.mapper.AlipayUserMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.WXcodeMapper;
import com.example.demo.tool.AESCBCUtil;
import com.example.demo.tool.MQService;
import com.example.demo.tool.Tool;
import com.example.demo.tool.UserTool;
import net.sf.json.JSONObject;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserMapper usermapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    ActivationService activationService;
    @Autowired
    PartnerService partnerService;
    @Autowired
    Tool tool;
    @Autowired
    UserTool userTool;
//    @Autowired
//    MQService mqService;
    @Autowired
    UserThread userThread;
    @Autowired
    WXcodeMapper wXcodeMapper;
    @Autowired
    AlipayUserMapper alipayUserMapper;
    AESCBCUtil aescbcUtil = new AESCBCUtil();
    @Value("${AppID}")
    String appid;
    @Value("${AppSecret}")
    String appsecret;
    @Value("${alipay.app_private_key}")
    String app_private_key;
    @Value("${alipay.public_key}")
    String public_key;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public User getuserbyid(String user_id){
        return usermapper.getuserbyid(user_id);
    }

    public JSONObject Login(Param param,BindingResult bindingResult){
        log.info("Check Param...");
        User realuser;
        JSONObject data = new JSONObject(),result;
        String return_code;
        if(tool.CheckParam(param,bindingResult, Login_Param.class)){
            log.info("Authentication...");
            if(tool.PartnerCheck(stringRedisTemplate,partnerService,param.getPartner_id(),param.getTime(),param.getToken(), String.valueOf(param.getParam()))){
                Login_Param login_param = (Login_Param)JSONObject.toBean(param.getParam(),Login_Param.class);
                realuser = userTool.GetFromRedis(login_param.getUser_name());
                if(realuser != null){
                    if(realuser.getUser_pwd().equals(login_param.getUser_pwd())){
                        if(realuser.getUser_status() == 1){
                            log.info("Check Successfully，Write redis...");
                            return_code = "000000";//成功
                            userThread.LoginDo(realuser);
                            data.put("user_id",realuser.getUser_id());
                        }else{
                            return_code = "900003";//用户账号被封停
                        }
                    }else{
                        log.info("PWD match Failed，" + login_param.getUser_pwd() + " =? " + realuser.getUser_pwd());
                        return_code = "900001";//用户账号已被封停
                    }
                }else {
                    log.info("User " + login_param.getUser_name() + " Not Exist");
                    return_code = "900002";//用户不存在
                }
            }else{
                log.info("Security Check Failed");
                return_code = "900008";//安全校验不通过
            }
        }else {
            return_code = "900000";//参数校验失败，缺少参数或部分参数为空
        }
        data = tool.JsonHandle(data);
        result = tool.SetReturnJson(return_code,"",data);
        log.info("Result:" + result.toString());
        return result;
    }

    public JSONObject RegisterUser(Param param,BindingResult bindingResult) {
        log.info("Check Param");
        JSONObject data = new JSONObject(),result;
        String return_code = null;
        if(tool.CheckParam(param,bindingResult, Register_Param.class)){
            log.info("Authentication...");
            if(tool.PartnerCheck(stringRedisTemplate,partnerService,param.getPartner_id(),param.getTime(),param.getToken(), String.valueOf(param.getParam()))){
                log.info("开始校验激活码...");
                Register_Param register_param = (Register_Param)JSONObject.toBean(param.getParam(), Register_Param.class);
                Activation activation = activationService.getactivationstatus(register_param.getActivation_code());
                if(activation == null){
                    return_code = "700002";//激活码不存在
                }else if(activation.getActivation_status() == 0 && activation.getActivation_account() == null) {
                    if(activation.getCode_owner() != null){
                        if(usermapper.getuserbyname(register_param.getUser_name()) == null){
                            User user = new User("",register_param.getUser_name(),register_param.getUser_pwd(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),1);
                            user.setUser_id(Tool.MD5(register_param.getUser_name() + user.getRegister_time(),32));
                            userThread.RegisterUserDo(user,activation);//进入独立线程写库，当前线程直接返回
                            return_code = "000000";//成功
                        }else{
                            return_code = "900009";
                        }
                    }else{
                        return_code = "700001";
                    }
                }else{
                    return_code = "700003";//激活码已使用
                }
            }
        }else{
            return_code = "900000";
        }
        data = tool.JsonHandle(data);
        result = tool.SetReturnJson(return_code,"",data);
        log.info("处理结束,返回:" + result.toString());
        return result;
    }

    public JSONObject UpdatePwd(Param param,BindingResult bindingResult){
        log.info("开始校验参数");
        JSONObject data = new JSONObject(),result;
        String return_code;
        if(tool.CheckParam(param,bindingResult, UpdatePwd_Param.class)){
            log.info("开始鉴权...");
            if (tool.PartnerCheck(stringRedisTemplate, partnerService, param.getPartner_id(), param.getTime(), param.getToken(), String.valueOf(param.getParam()))) {
                log.info("开始改库...");
                UpdatePwd_Param updatePwd_param = (UpdatePwd_Param)JSONObject.toBean(param.getParam(), UpdatePwd_Param.class);
                log.info(updatePwd_param.getUser_id() + ";" + updatePwd_param.getUser_pwd());
                if (usermapper.updateuserpwd(updatePwd_param.getUser_id(), updatePwd_param.getUser_pwd())) {
                    log.info("开始修改redis...");
                    stringRedisTemplate.opsForHash().put(
                            "user_info_" + stringRedisTemplate.opsForValue().get("userid_name_relation_" + updatePwd_param.getUser_id()),
                            "user_pwd", updatePwd_param.getUser_pwd());
                    log.info("修改完成");
                    return_code = "000000";//成功
                } else {
                    return_code = "999999";//失败
                }
            } else {
                return_code = "900008";//安全校验不通过
            }
        }else{
            return_code = "900000";//缺少参数
        }
        data = tool.JsonHandle(data);
        result = tool.SetReturnJson(return_code,"",data);
        return result;
    }

    public JSONObject SetUserStatus(SetUserstatus setUserstatus,BindingResult bindingResult,String manager_id){
        log.info("Check Param...");
        JSONObject data = new JSONObject(),result;
        String return_code = null;
        if(bindingResult.hasErrors()){
            return_code = "900000";
            data.put("ParamCheck",bindingResult.getAllErrors().get(0).getDefaultMessage());
        }else{
            log.info("Authentication...");
            if(setUserstatus.getPartner_id().equals(manager_id)){
                if(tool.PartnerCheck(stringRedisTemplate,partnerService,setUserstatus.getPartner_id(),setUserstatus.getTime(),setUserstatus.getToken(),"")){
                    log.info("Update mysql...");
                    User checkuser = usermapper.getuserbyid(setUserstatus.getUser_id());
                    if(checkuser != null){
                        if(usermapper.setuserstatus(setUserstatus.getUser_id(),setUserstatus.getUser_status())){
                            log.info("mysql Update complete，Write redis...");
                            String user_name = stringRedisTemplate.opsForValue().get("userid_name_relation_" + setUserstatus.getUser_id());
                            stringRedisTemplate.opsForHash().put("user_info_" + user_name,"user_status",setUserstatus.getUser_status() + "");
                            return_code = "000000";
                            log.info("redis update complete");
                        }
                    }else{
                        return_code = "900002";
                    }
                }else{
                    return_code = "900008";
                }
            }else{
                return_code = "500001";
            }
        }
        data = tool.JsonHandle(data);
        result = tool.SetReturnJson(return_code,"",data);
        log.info("Result:" + result.toString());
        return result;
    }

    public JSONObject WXLogin(Param param, BindingResult bindingResult){
        log.info("Check Param...");
        JSONObject data = new JSONObject(),result;
        String return_code = null;
        if(tool.CheckParam(param,bindingResult, WXLogin_Param.class)){
            log.info("Authentication...");
            if(tool.PartnerCheck(stringRedisTemplate,partnerService,param.getPartner_id(),param.getTime(),param.getToken(), String.valueOf(param.getParam()))){
                WXLogin_Param wxLogin_param = (WXLogin_Param)JSONObject.toBean(param.getParam(),WXLogin_Param.class);
                Map<String,Object> map = userTool.getWxUserOpenid(wxLogin_param.getCode(),appid,appsecret);
                log.info("Wechat Result:" + map.toString());
                if(map.containsKey("openid")){
                    WXcode wXcode = wXcodeMapper.getuserbyopenid(map.get("openid").toString());
                    String decrypts= null;
//                    try {
//                        decrypts = AESCBCUtil.decrypt(wxLogin_param.getEncryptedData(),map.get("session_key").toString(),wxLogin_param.getIv(),"utf-8");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        return_code = "200002";//用户session已过期
//                    }
//                    JSONObject jsons = JSONObject.fromObject(decrypts);
                    if(wXcode == null){
                        User user = new User();
                        user.setRegister_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                        user.setUser_status(1);
                        user.setUser_name(wxLogin_param.getNickName());
                        user.setUser_id(Tool.MD5(map.get("openid").toString() + user.getRegister_time(),32));
                        user.setUser_pic(wxLogin_param.getAvatarUrl());
                        wXcode = new WXcode(map.get("openid").toString(),map.get("session_key").toString(),map.get("expires_in").toString(),user.getUser_id());
                        usermapper.insertuser(user);
                        wXcodeMapper.insertwxcode(wXcode);
                        return_code = "000000";//微信新用户注册成功
                        data.put("user_id",user.getUser_id());
                        data.put("user_name",wxLogin_param.getNickName());
                    }else{
                        usermapper.wxaddname(wXcode.getUser_id(),wxLogin_param.getNickName());
                        wXcode.setSession_key(map.get("session_key").toString());
                        wXcode.setExpires_in(map.get("expires_in").toString());
                        wXcodeMapper.updatewxcode(wXcode);
                        return_code = "000000";//更新用户session成功
                        data.put("user_id",wXcode.getUser_id());
                        data.put("user_name",wxLogin_param.getNickName());
                    }
                    String access_token = Tool.MD5(wXcode.getUser_id() + wXcode.getOpenid() + new SimpleDateFormat("yyyy-MM").format(new Date()),32);
                    stringRedisTemplate.opsForValue().set("access_token_" + data.get("user_id"),access_token);
                    data.put("access_token", access_token);
                }else{
                    return_code = "200001";//微信openid获取失败
                }
            } else {
                return_code = "900008";//安全校验不通过
            }
        }else{
            return_code = "900000";
        }
        data = tool.JsonHandle(data);
        result = tool.SetReturnJson(return_code,"",data);
        log.info("Result:" + result.toString());
        return result;
    }

//    public JSONObject AlipayLogin(Param param,BindingResult bindingResult){
//        log.info("Check Param...");
//        JSONObject data = new JSONObject(),result;
//        String return_code = null,return_msg = "";
//        if(tool.CheckParam(param,bindingResult,Ali_Login.class)){
//            log.info("Authentication...");
//            if(tool.PartnerCheck(stringRedisTemplate,partnerService,param.getPartner_id(),param.getTime(),param.getToken(), String.valueOf(param.getParam()))){
//                Ali_Login ali_login = (Ali_Login) JSONObject.toBean(param.getParam(),Ali_Login.class);
////                String auth_code = JSONObject.fromObject(param.getParam()).getString("auth_code");
//                log.info(app_private_key);
//                log.info(public_key);
//                AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","2019071065833135", app_private_key, "json","GBK", public_key, "RSA2");
//                AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
//                request.setGrantType("authorization_code");
//                request.setCode(ali_login.getAuth_code());
////                request.setRefreshToken("201208134b203fe6c11548bcabd8da5bb087a83b");
//                AlipaySystemOauthTokenResponse response = null;
//                try {
//                    response = alipayClient.execute(request);
//                } catch (AlipayApiException e) {
//                    e.printStackTrace();
//                }
//                if(response.isSuccess()){
//                    log.info("支付宝登录调用成功,user_id:" + response.getAlipayUserId() + "；access_token:" + response.getAccessToken());
//                    Alipay_User alipay_user = alipayUserMapper.getuserbyaliid(response.getAlipayUserId());
//                    if(alipay_user == null){
//                        alipay_user = new Alipay_User(response.getAlipayUserId(),response.getAccessToken(),response.getExpiresIn(),response.getRefreshToken(),response.getReExpiresIn(),"");
//                        alipay_user.setUser_id(Tool.MD5(alipay_user.getAlipay_user_id() + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),32));
//                        log.info(alipay_user.toString());
//                        if(alipayUserMapper.insertaliuser(alipay_user)){
//                            return_code = "000000";//阿里新用户注册成功
//                            data.put("user_id",alipay_user.getUser_id());
//                            User user = new User(alipay_user.getUser_id(),ali_login.getUser_name(),"",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),1);
//                            usermapper.insertuser(user);
//                        }else{
//                            return_code = "999999";//写库失败
//                        }
//                    }else{
//                        alipay_user.setAlipay_user_id(response.getAlipayUserId());
//                        alipay_user.setAccess_token(response.getAccessToken());
//                        alipay_user.setExpires_in(response.getExpiresIn());
//                        alipay_user.setRefresh_token(response.getRefreshToken());
//                        alipay_user.setExpires_in(response.getReExpiresIn());
//                        log.info(alipay_user.toString());
//                        if(alipayUserMapper.updatealicode(alipay_user)){
//                            return_code = "000000";//阿里老用户更新access_token成功
//                            data.put("user_id",alipay_user.getUser_id());
//                        }else{
//                            return_code = "999999";//写库失败
//                        }
//                    }
//                } else {
//                    System.out.println("支付宝登录接口调用失败");
//                    return_code = "200000";//第三方登录接口调用失败
//                    return_msg = response.getSubMsg();
//                }
//            }
//        }else{
//            return_code = "900000";//缺少参数
//        }
//        data = tool.JsonHandle(data);
//        result = tool.SetReturnJson(return_code,return_msg,data);
//        log.info("处理结束，返回:" + result.toString());
//        return result;
//    }

    public JSONObject CheckAccessToken(Param param, BindingResult bindingResult){
        log.info("Check Param...");
        JSONObject data = new JSONObject(),result;
        String return_code;
        if(tool.CheckParam(param,bindingResult, CheckToken.class)){
            log.info("Authentication...");
            if (tool.PartnerCheck(stringRedisTemplate, partnerService, param.getPartner_id(), param.getTime(), param.getToken(), String.valueOf(param.getParam()))) {
                CheckToken checkToken = (CheckToken)JSONObject.toBean(param.getParam(),CheckToken.class);
                WXcode wXcode = wXcodeMapper.getuserbyuserid(checkToken.getUser_id());
                if(wXcode != null) {
                    if(!stringRedisTemplate.hasKey("access_token_" + checkToken.getUser_id()) ||
                            !Tool.MD5(wXcode.getUser_id() + wXcode.getOpenid() + new SimpleDateFormat("yyyy-MM").format(new Date()),32).equals(checkToken.getAccess_token())){
                        checkToken.setAccess_token(Tool.MD5(wXcode.getUser_id() + wXcode.getOpenid() + new SimpleDateFormat("yyyy-MM").format(new Date()),32));
                    }
                    data.put("user_id", wXcode.getUser_id());
                    data.put("access_token", checkToken.getAccess_token());
                    return_code = "000000";//成功
                }else{
                    return_code = "900002";//用户不存在
                }
            } else {
                return_code = "900008";//安全校验不通过
            }
        }else{
            return_code = "900000";//缺少参数
        }
        data = tool.JsonHandle(data);
        result = tool.SetReturnJson(return_code,"",data);
        log.info("Result:" + result.toString());
        return result;
    }

    public JSONObject UpdateUser(Param param, BindingResult bindingResult){
        log.info("Check Param");
        JSONObject data = new JSONObject(),result;
        String return_code;
        if(tool.CheckParam(param,bindingResult, UpdateUser_Param.class)){
            log.info("Authentication...");
            if (tool.PartnerCheck(stringRedisTemplate, partnerService, param.getPartner_id(), param.getTime(), param.getToken(), String.valueOf(param.getParam()))) {
                UpdateUser_Param updateUser_param  = (UpdateUser_Param) JSONObject.toBean(param.getParam(),UpdateUser_Param.class);
                User user = new User();
                user.setUser_id(updateUser_param.getUser_id());
                user.setUser_name(updateUser_param.getUser_name());
                user.setUser_pic(updateUser_param.getUser_pic());
                usermapper.updateuser(user);
                return_code = "000000";//更新成功
            } else {
                return_code = "900008";//安全校验不通过
            }
        }else{
            return_code = "900000";//缺少参数
        }
        data = tool.JsonHandle(data);
        result = tool.SetReturnJson(return_code,"",data);
        log.info("Result:" + result.toString());
        return result;
    }
}
