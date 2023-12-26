package com.xmut.managementsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmut.managementsystem.entity.Goods;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GoodsService extends IService<Goods> {
    List<Integer> listByShopIds(List<Integer> ShopIds);
}
