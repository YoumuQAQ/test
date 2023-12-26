package com.xmut.managementsystem.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmut.managementsystem.entity.Shop;
import com.xmut.managementsystem.mapper.ShopMapper;
import com.xmut.managementsystem.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {
    @Autowired
    private ShopMapper shopMapper;

    @Override
    public List<Integer> listByUserId(Integer userId) {
        return shopMapper.selectByUserId(userId);
    }
}
