package com.czscloud.czs.service;

import com.czscloud.czs.entity.User;

public interface UserService {
    User findByLogin(String loginId, String password);
}
