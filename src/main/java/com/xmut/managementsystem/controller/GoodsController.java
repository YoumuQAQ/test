package com.xmut.managementsystem.controller;

import com.xmut.managementsystem.entity.Goods;
import com.xmut.managementsystem.entity.Result;
import com.xmut.managementsystem.service.GoodsService;
import com.xmut.managementsystem.service.ShopService;
import com.xmut.managementsystem.tool.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private TokenService tokenService;

    public Result<List<Goods>> getList(@RequestBody Map<String, String> data) {
        Result<List<Goods>> result = new Result<>();
        Integer type = Integer.valueOf(data.get("type"));
        String username = data.get("username");
        if (type == 1)
            result.setData(goodsService.list());
        else {
            Integer userId = Integer.valueOf(data.get("userId"));
            List<Integer> shopIds = shopService.listByUserId(userId);
            List<Integer> goodsIds = goodsService.listByShopIds(shopIds);
            result.setData(goodsService.listByIds(goodsIds));
        }
        result.setToken(tokenService.create(username));
        return result;
    }

    @PostMapping("/list")
    public Result<List<Goods>> goodsList(@RequestBody Map<String, String> data) {
        return getList(data);
    }

    @PostMapping("/updateGoods")
    public Result<List<Goods>> updateGoods(@RequestBody Map<String, String> data) {
        Integer goodsId = Integer.valueOf(data.get("goodsId"));
        Goods goods = goodsService.getById(goodsId);
        goods.setName(data.get("goodsName"));
        goods.setPrice(Double.valueOf(data.get("price")));
        goods.setPic(data.get("pic"));
        goods.setInventory(Integer.valueOf(data.get("inventory")));
        goodsService.updateById(goods);
        return getList(data);
    }

    @PostMapping("/saveGoods")
    public Result<List<Goods>> saveGoods(@RequestBody Map<String, String> data) {
        Goods goods = new Goods();
        goods.setShopId(Integer.valueOf(data.get("shopId")));
        goods.setInventory(Integer.valueOf(data.get("inventory")));
        goods.setPrice(Double.valueOf(data.get("price")));
        goods.setName(data.get("goodsName"));
        goods.setPic(data.get("pic"));
        LocalDateTime createTime = LocalDateTime.now();
        goods.setCreatTime(createTime);
        goodsService.save(goods);
        return getList(data);
    }

    @PostMapping("/deleteGoods")
    public Result<List<Goods>> deleteGoods(@RequestBody Map<String, String> data) {
        Integer goodsId = Integer.valueOf(data.get("goodsId"));
        goodsService.removeById(goodsId);
        return getList(data);
    }
}
