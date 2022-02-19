package com.czscloud.czs.service.impl;

import com.czscloud.czs.entity.User;
import com.czscloud.czs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public User findByLogin(String loginId, String password) {
        User user = jdbcTemplate.queryForObject("select * from user where loginid = ? and password = ? ",new Object[]{
                loginId,password
        },new BeanPropertyRowMapper<>(User.class));
        return user;
    }
}
