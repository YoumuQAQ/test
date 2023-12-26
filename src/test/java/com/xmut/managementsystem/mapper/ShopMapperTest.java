package com.xmut.managementsystem.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShopMapperTest {

    @Autowired
    private ShopMapper shopMapper;
    @Test
    void selectByUserid() {
        System.out.println(shopMapper.selectByUserId(2));
    }
}