package com.xmut.managementsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmut.managementsystem.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends IService<User> {
    Integer getUserIdByUsername(String username);
}
