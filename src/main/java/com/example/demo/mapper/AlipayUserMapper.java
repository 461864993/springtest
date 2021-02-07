package com.example.demo.mapper;

import com.example.demo.entity.User.Alipay_User;
import org.springframework.stereotype.Repository;

@Repository
public interface AlipayUserMapper {
    Alipay_User getuserbyaliid (String alipay_user_id);
    boolean insertaliuser (Alipay_User alipay_user);
    boolean updatealicode (Alipay_User alipay_user);
    Alipay_User getuserbyuserid(String user_id);
}
