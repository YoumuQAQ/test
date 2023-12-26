package com.xmut.managementsystem.controller;

import com.xmut.managementsystem.entity.Result;
import com.xmut.managementsystem.entity.Shop;
import com.xmut.managementsystem.service.ShopService;
import com.xmut.managementsystem.tool.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private TokenService tokenService;

    public Result<List<Shop>> getList(Map<String, String> data) {
        Result<List<Shop>> result = new Result<>();
        Integer type = Integer.valueOf(data.get("type"));
        String username = data.get("username");
        if(type == 1)
            result.setData(shopService.list());
        else
        {
            Integer userId = Integer.valueOf(data.get("userId"));
            List<Integer> shopIds = shopService.listByUserId(userId);
            result.setData(shopService.listByIds(shopIds));
        }
        result.setToken(tokenService.create(username));
        return result;
    }

    @PostMapping("/list")
    public Result<List<Shop>> shopList(@RequestBody Map<String, String> data) {
        return getList(data);
    }

    @PostMapping("/updateShop")
    public Result<List<Shop>> updateShop(@RequestBody Map<String, String> data) {
        Integer shopId = Integer.valueOf(data.get("shopId"));
        Shop shop = shopService.getById(shopId);
        shop.setShopname(data.get("shopname"));
        shop.setAddress(data.get("address"));
        shopService.updateById(shop);
        return getList(data);
    }

    @PostMapping("/saveShop")
    public Result<List<Shop>> saveShop(@RequestBody Map<String, String> data) {
        Shop shop = new Shop();
        shop.setUserId(Integer.valueOf(data.get("userId")));
        shop.setTelephone(data.get("telephone"));
        shop.setShopname(data.get("shopname"));
        shop.setAddress(data.get("address"));
        LocalDateTime createTime = LocalDateTime.now();
        shop.setCreatTime(createTime);
        shopService.save(shop);
        return getList(data);
    }

    @PostMapping("/deleteShop")
    public Result<List<Shop>> deleteShop(@RequestBody Map<String, String> data) {
        Integer shopId = Integer.valueOf(data.get("shopId"));
        shopService.removeById(shopId);
        return getList(data);
    }
}
