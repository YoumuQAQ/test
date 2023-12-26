package com.xmut.managementsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xmut.managementsystem.entity.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RecordMapper extends BaseMapper<Record> {
    @Select("SELECT id FROM xmut_record WHERE user_id=#{userId}")
    List<Integer> selectByUserId(Integer userId);
}
