package com.xmut.managementsystem.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GoodsMapperTest {

    @Autowired
    private GoodsMapper goodsMapper;
    @Test
    void selectByShopIds() {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(4);
        System.out.println(goodsMapper.selectByShopIds(ids));
    }
}