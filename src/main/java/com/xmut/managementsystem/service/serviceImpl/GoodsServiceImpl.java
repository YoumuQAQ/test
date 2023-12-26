package com.xmut.managementsystem.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmut.managementsystem.entity.Goods;
import com.xmut.managementsystem.mapper.GoodsMapper;
import com.xmut.managementsystem.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<Integer> listByShopIds(List<Integer> ShopIds) {
        return goodsMapper.selectByShopIds(ShopIds);
    }
}
