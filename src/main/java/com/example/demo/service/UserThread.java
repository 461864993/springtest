package com.example.demo.service;

import com.example.demo.entity.Activation.Activation;
import com.example.demo.entity.User.User;
import com.example.demo.mapper.ActivationMapper;
import com.example.demo.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 描述：用于用户处理Service异步处理线程存放
 */
@Service
public class UserThread {

    @Autowired
    UserMapper userMapper;
    @Autowired
    ActivationMapper activationMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Async("taskExecutor")
    public void RegisterUserDo(User user, Activation activation){
        log.info("独立注册线程开始....");
        userMapper.insertuser(user);
        activation.setActivation_account(user.getUser_id());
        activationMapper.activatyaccount(activation);
        log.info( "用户:"  + user.toString() + "注册流程处理完成,激活码信息:" + activation.toString() + "，线程结束...");
    }

    @Async("taskExecutor")
    public void LoginDo(User user){
        log.info("独立注册线程开始....");
        log.info("写入用户信息到redis > user_info_" + user.getUser_name());
        stringRedisTemplate.opsForHash().put("user_info_" + user.getUser_name(),"user_id",user.getUser_id());
        stringRedisTemplate.opsForHash().put("user_info_" + user.getUser_name(),"user_name",user.getUser_name());
        stringRedisTemplate.opsForHash().put("user_info_" + user.getUser_name(),"user_pwd",user.getUser_pwd());
        stringRedisTemplate.opsForHash().put("user_info_" + user.getUser_name(),"register_time",user.getRegister_time());
        stringRedisTemplate.opsForHash().put("user_info_" + user.getUser_name(),"user_status",user.getUser_status() + "");
        stringRedisTemplate.opsForValue().set("userid_name_relation_" + user.getUser_id(), user.getUser_name());
        log.info("redis写入成功...");
        log.info( "用户:"  + user.toString() + "登录redis写入流程处理完成,线程结束...");
    }

}
