package com.example.demo.mapper;

import com.example.demo.entity.User.WXcode;
import org.springframework.stereotype.Repository;

@Repository
public interface WXcodeMapper {

    WXcode getuserbyopenid(String openid);

    boolean insertwxcode(WXcode wXcode);
    boolean updatewxcode(WXcode wXcode);

    WXcode getuserbyuserid(String user_id);
}
