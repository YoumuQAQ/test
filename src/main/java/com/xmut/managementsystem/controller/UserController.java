package com.xmut.managementsystem.controller;

import com.xmut.managementsystem.entity.Result;
import com.xmut.managementsystem.entity.User;
import com.xmut.managementsystem.service.UserService;
import com.xmut.managementsystem.tool.GraphicCodeService;
import com.xmut.managementsystem.tool.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private GraphicCodeService graphicCodeService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Result<String> register(@RequestBody Map<String, String> data) {
        Result<String> result = new Result<>();
        String username = data.get("username");
        String password = data.get("password");
        String captchaCode = data.get("captchaCode");
        Integer checkId = userService.getUserIdByUsername(username);
        String redisCode = (String) redisTemplate.opsForValue().get("captcha");
        if (redisCode == null)
            result.setMessage("验证码已过期");
        else {
            if (graphicCodeService.checkCaptcha(captchaCode, redisCode)) {
                if (userService.getById(checkId) != null) {
                    result.setMessage("用户名已存在");
                } else {
                    User user = new User();
                    user.setUsername(username);
                    user.setNickname(username);
                    String securityPassword = passwordEncoder.encode(password);
                    user.setPassword(securityPassword);
                    user.setType(2);
                    user.setUseful(1);
                    LocalDateTime createTime = LocalDateTime.now();
                    user.setCreatTime(createTime);
                    userService.save(user);
                }
            } else
                result.setMessage("验证码错误");
        }
        return result;
    }

    @GetMapping("/getCaptcha")
    public String getCaptcha() throws IOException, FontFormatException {
        return graphicCodeService.createCaptcha();
    }

    @PostMapping("/login")
    public Result<User> login(@RequestBody Map<String, String> loginData) {
        redisTemplate.opsForValue().set("token", "222", 30, TimeUnit.MINUTES);
        String username = loginData.get("username");
        String password = loginData.get("password");
        String captchaCode = loginData.get("captchaCode");
        Result<User> result = new Result<>();
        String redisCode = (String) redisTemplate.opsForValue().get("captcha");
        if (redisCode == null)
            result.setMessage("验证码已过期");
        else {
            if (graphicCodeService.checkCaptcha(captchaCode, redisCode)) {
                Integer id = userService.getUserIdByUsername(username);
                User user = userService.getById(id);
                if (user == null)
                    result.setMessage("用户名不存在");
                else {
                    if(user.getUseful() == 2)
                        result.setMessage("用户已被禁用");
                    else
                    {
                        if (passwordEncoder.matches(password, user.getPassword())) {
                            result.setToken(tokenService.create(username));
                            result.setMessage("登录成功");
                            result.setData(user);
                        } else
                            result.setMessage("密码错误");
                    }
                }
            } else
                result.setMessage("验证码错误");
        }
        return result;
    }

    @PostMapping("/exit")
    public Result<String> exit(@RequestBody Map<String, String> data) {
        List<String> keys = new ArrayList<>();
        keys.add("token");
        keys.add("captcha");
        redisTemplate.delete(keys);
        Result<String> result = new Result<>();
        result.setMessage("退出成功");
        return result;
    }

    @PostMapping("/resetPassword")
    public Boolean resetPassword(@RequestBody Map<String, String> data) {
        Integer userId = Integer.valueOf(data.get("userId"));
        User changeUser = userService.getById(userId);
        changeUser.setPassword(passwordEncoder.encode("123456"));
        return userService.updateById(changeUser);
    }

    @PostMapping("/changePassword")
    public Boolean changePassword(@RequestBody Map<String, String> data) {
        Integer userId = Integer.valueOf(data.get("userId"));
        String newPassword = data.get("newPassword");
        Integer redisUserId = (Integer) redisTemplate.opsForValue().get("userId");
        if (redisUserId.equals(userId)) {
            User changeUser = userService.getById(userId);
            changeUser.setPassword(passwordEncoder.encode(newPassword));
            return userService.updateById(changeUser);
        }
        Integer type = (Integer) redisTemplate.opsForValue().get("type");
        if (type == 1) {
            User changeUser = userService.getById(userId);
            changeUser.setPassword(passwordEncoder.encode(newPassword));
            return userService.updateById(changeUser);
        } else {
            return false;
        }
    }

    @PostMapping("/updateInformation")
    public Result<User> updateInformation(@RequestBody Map<String, String> data) {
        Result<User> result = new Result<>();
        Integer userId = Integer.valueOf(data.get("userId"));
        String nickname = data.get("nickname");
        String telephone = data.get("telephone");
        Integer sex = Integer.valueOf(data.get("sex"));
        User user = userService.getById(userId);
        user.setNickname(nickname);
        user.setTelephone(telephone);
        user.setSex(sex);
        userService.updateById(user);
        result.setData(userService.getById(userId));
        String username = data.get("username");
        result.setToken(tokenService.create(username));
        return result;
    }

    @PostMapping("/getUserList")
    public Result<List<User>> getUserList(@RequestBody Map<String, String> data) {
        Result<List<User>> result = new Result<>();
        String nowUsername = data.get("nowUsername");
        result.setToken(tokenService.create(nowUsername));
        result.setData(userService.list());
        return result;
    }

    @PostMapping("/changeUseful")
    public Result<List<User>> changeUseful(@RequestBody Map<String, String> data) {
        Integer userId = Integer.valueOf(data.get("userId"));
        Integer useful = Integer.valueOf(data.get("useful"));
        Integer newUseful;
        if (useful == 1)
            newUseful = 2;
        else
            newUseful = 1;
        User changeUser = userService.getById(userId);
        changeUser.setUseful(newUseful);
        userService.updateById(changeUser);
        return getUserList(data);
    }

    @PostMapping("/saveUser")
    public Result<List<User>> saveUser(@RequestBody Map<String, String> data) {
        String username = data.get("username");
        String password = data.get("password");
        User user = new User();
        user.setUsername(username);
        user.setNickname(username);
        String securityPassword = passwordEncoder.encode(password);
        user.setPassword(securityPassword);
        user.setType(2);
        user.setUseful(1);
        LocalDateTime createTime = LocalDateTime.now();
        user.setCreatTime(createTime);
        userService.save(user);
        return getUserList(data);
    }

    @PostMapping("/deleteUser")
    public Result<List<User>> deleteUser(@RequestBody Map<String, String> data) {
        Integer id = Integer.valueOf(data.get("userId"));
        userService.removeById(id);
        return getUserList(data);
    }

    @PostMapping("/updateUser")
    public Result<List<User>> updateUser(@RequestBody Map<String, String> data) {
        Integer userId = Integer.valueOf(data.get("userId"));
        String nickname = data.get("nickname");
        String telephone = data.get("telephone");
        Integer sex = Integer.valueOf(data.get("sex"));
        User user = userService.getById(userId);
        user.setNickname(nickname);
        user.setTelephone(telephone);
        user.setSex(sex);
        userService.updateById(user);
        return getUserList(data);
    }
}
