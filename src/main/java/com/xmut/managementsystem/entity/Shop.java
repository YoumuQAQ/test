package com.xmut.managementsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("xmut_shop")
public class Shop {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String shopname;
    private String address;
    private String telephone;
    private Integer userId;
    private LocalDateTime creatTime;
}
