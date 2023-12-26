package com.xmut.managementsystem.tool;

import com.xmut.managementsystem.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test() {
        User user = new User();
        user.setId(1);
        user.setUsername("username");
        redisTemplate.boundValueOps("userKey").set(user);

        User rs = (User) redisTemplate.boundValueOps("userKey").get();
        System.out.println("rs = " + rs.toString());

        User rs2 = (User) redisTemplate.opsForValue().get("userKey");
        System.out.println("rs2 = " + rs2.toString());
    }
}
