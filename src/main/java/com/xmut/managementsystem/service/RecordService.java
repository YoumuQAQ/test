package com.xmut.managementsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmut.managementsystem.entity.Record;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecordService extends IService<Record> {
    List<Integer> listByUserId(Integer userId);
}
