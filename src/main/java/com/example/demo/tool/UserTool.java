package com.example.demo.tool;

import com.example.demo.controller.UserControler;
import com.example.demo.entity.User.User;
import com.example.demo.entity.User.WXcode;
import com.example.demo.mapper.UserMapper;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserTool {
    @Autowired
    UserMapper userMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    Tool tool;
    private static final Logger log = LoggerFactory.getLogger(UserTool.class);

    public User GetFromRedis(String user_name){
        User user;
        Map map = stringRedisTemplate.opsForHash().entries("user_info_" + user_name);
        log.info(map.toString());
        if(!map.isEmpty()){
            log.info("redis读取成功，开始校验登录信息...");
            user = new User(map.get("user_id").toString(),map.get("user_name").toString(),
                    map.get("user_pwd").toString(),map.get("register_time").toString(),Integer.parseInt(map.get("user_status").toString()));
        }else{
            log.info("redis读取失败，读库...");
            user = userMapper.getuserbyname(user_name);
        }
        return user;
    }

    public Map<String, Object> getWxUserOpenid(String code, String APPID, String APPSecret) {
        StringBuilder url = new StringBuilder("https://api.weixin.qq.com/sns/jscode2session?");
        //拼接url
        url.append("appid=");//appid设置
        url.append(APPID);
        url.append("&secret=");//secret设置
        url.append(APPSecret);
        url.append("&js_code=");//code设置
        url.append(code);
        url.append("&grant_type=authorization_code");
        log.info(url.toString());
        Map<String, Object> map = null;

        try {
            HttpClient client = HttpClientBuilder.create().build();//构建一个Client
            HttpGet get = new HttpGet(url.toString()); //构建一个GET请求
            HttpResponse response = client.execute(get);//提交GET请求
            HttpEntity result = response.getEntity();//拿到返回的HttpResponse的"实体"
            String content = EntityUtils.toString(result);
            System.out.println(content);//打印返回的信息
            JSONObject res = JSONObject.fromObject(content);//把信息封装为json

            //把信息封装到map
            map = tool.parseJSON2Map(res);//这个小工具的代码在下面
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}
