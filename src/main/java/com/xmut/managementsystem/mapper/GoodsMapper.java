package com.xmut.managementsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xmut.managementsystem.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
    @Select("<script>" +
            "SELECT id FROM xmut_goods WHERE shop_id IN" +
            "<foreach collection='ShopIds' item='shopId' open='(' separator=',' close=')'>" +
            "#{shopId}" +
            "</foreach>" +
            "</script>")
    List<Integer> selectByShopIds(List<Integer> ShopIds);
}
