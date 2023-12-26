package com.xmut.managementsystem.controller;

import com.xmut.managementsystem.entity.Goods;
import com.xmut.managementsystem.entity.Record;
import com.xmut.managementsystem.entity.Result;
import com.xmut.managementsystem.service.GoodsService;
import com.xmut.managementsystem.service.RecordService;
import com.xmut.managementsystem.tool.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private RecordService recordService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private TokenService tokenService;

    public Result<List<Record>> getRecordList(@RequestBody Map<String, String> data) {
        Result<List<Record>> result = new Result<>();
        Integer type = Integer.valueOf(data.get("type"));
        String username = data.get("username");
        if (type == 1)
            result.setData(recordService.list());
        else {
            Integer userId = Integer.valueOf(data.get("userId"));
            List<Integer> RecordIds = recordService.listByUserId(userId);
            result.setData(recordService.listByIds(RecordIds));
        }
        result.setToken(tokenService.create(username));
        return result;
    }

    @PostMapping("/list")
    public Result<List<Record>> list(@RequestBody Map<String, String> data) {
        return getRecordList(data);
    }

    @PostMapping("/operate")
    public Result<List<Record>> operate(@RequestBody Map<String,String> data){
        Record record = new Record();
        record.setNumber(Integer.valueOf(data.get("number")));
        record.setOperate(Integer.valueOf(data.get("operate")));
        record.setGoodsId(Integer.valueOf(data.get("goodsId")));
        record.setUserId(Integer.valueOf(data.get("userId")));
        record.setPerson(data.get("person"));
        LocalDateTime createTime = LocalDateTime.now();
        record.setCreateTime(createTime);
        recordService.save(record);
        if(data.get("operate").equals("1"))
        {
            Integer goodsId = Integer.valueOf(data.get("goodsId"));
            Goods goods = goodsService.getById(goodsId);
            Integer integer = goods.getInventory() + Integer.parseInt(data.get("number"));
            goods.setInventory(integer);
            goodsService.updateById(goods);
        }
        else if(data.get("operate").equals("2"))
        {
            Integer goodsId = Integer.valueOf(data.get("goodsId"));
            Goods goods = goodsService.getById(goodsId);
            Integer integer = goods.getInventory() - Integer.parseInt(data.get("number"));
            goods.setInventory(integer);
            goodsService.updateById(goods);
        }
        return getRecordList(data);
    }
}
