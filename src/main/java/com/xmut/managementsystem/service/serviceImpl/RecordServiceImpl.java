package com.xmut.managementsystem.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmut.managementsystem.entity.Record;
import com.xmut.managementsystem.mapper.RecordMapper;
import com.xmut.managementsystem.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public List<Integer> listByUserId(Integer userId) {
        return recordMapper.selectByUserId(userId);
    }
}
