package com.xmut.managementsystem.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecordMapperTest {

    @Autowired
    private RecordMapper recordMapper;
    @Test
    void selectByUserId() {
        System.out.println(recordMapper.selectByUserId(2));
    }
}