package com.xmut.managementsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmut.managementsystem.entity.Shop;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShopService extends IService<Shop> {
    List<Integer> listByUserId(Integer userId);

}
