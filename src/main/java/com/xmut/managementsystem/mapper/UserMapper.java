package com.xmut.managementsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xmut.managementsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT id FROM xmut_user WHERE username=#{username}")
    Integer getUserIdByUsername(String username);
}
