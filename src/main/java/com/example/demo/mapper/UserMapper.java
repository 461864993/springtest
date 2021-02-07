package com.example.demo.mapper;

import com.example.demo.entity.User.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    User getuserbyid(String user_id);

    User getuserbyname(String user_name);

    boolean insertuser(User user);

    boolean updateuserpwd(String user_id,String user_pwd);

    boolean setuserstatus(String user_id,int user_status);

    boolean wxaddname(String user_id,String user_name);

    boolean updateuser(User user);
}
