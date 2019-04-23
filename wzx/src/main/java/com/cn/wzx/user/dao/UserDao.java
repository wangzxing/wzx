package com.cn.wzx.user.dao;

import org.springframework.stereotype.Repository;

import com.cn.web.shiro.UserInfo;

@Repository
public interface UserDao {
   
	UserInfo selectByName(String userName);

  

    int lockAccount(String username);
}