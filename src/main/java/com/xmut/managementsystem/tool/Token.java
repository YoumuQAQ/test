package com.xmut.managementsystem.tool;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class Token implements TokenService{
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public String create(String username) {
        String newToken = JWT.create()
                .withClaim("username", username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 25 * 60 * 1000))
                .sign(Algorithm.HMAC256("ManagementSystem"));
        redisTemplate.opsForValue().set("token", newToken, 30, TimeUnit.MINUTES);
        return newToken;
    }

    public Boolean check(String checkToken) {
        String redisToken = (String) redisTemplate.opsForValue().get("token");
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("ManagementSystem"))
                    .build()
                    .verify(checkToken);
            return true;
        } catch (TokenExpiredException e) {
            return false;
        }
    }

    public String getUsername(String token){
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("ManagementSystem"))
                .build()
                .verify(token);
        return decodedJWT.getClaim("username").asString();
    }
}
