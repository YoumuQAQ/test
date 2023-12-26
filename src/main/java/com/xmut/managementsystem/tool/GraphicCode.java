package com.xmut.managementsystem.tool;

import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class GraphicCode implements GraphicCodeService{

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public String createCaptcha() throws IOException, FontFormatException {
        SpecCaptcha captcha = new SpecCaptcha(130, 48);
        captcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        captcha.setFont(Captcha.FONT_1);
        String redisCode = captcha.text();
        redisTemplate.opsForValue().set("captcha", redisCode, 60, TimeUnit.SECONDS);
        return captcha.toBase64();
    }

    public Boolean checkCaptcha(String code, String redisCode) {
        return code.equals(redisCode);
    }
}
