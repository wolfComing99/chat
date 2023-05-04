package com.wolf.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wolf.entity.User;
import com.wolf.mail.Mail;
import com.wolf.result.*;
import com.wolf.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController {
    public final static Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    IUserService iUserService;

    String code;

    @RequestMapping("/loginByPassword")
    public Result login(@RequestBody JSONObject requestJson) {
        String username = requestJson.getString("username");
        String password = requestJson.getString("password");
        Subject subject = SecurityUtils.getSubject();
//        subject.getSession().setTimeout(10000);
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        try {
            subject.login(usernamePasswordToken);
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username", username);
            User user = iUserService.getOne(wrapper);
            return ResultFactory.buildSuccessResult(user);
        } catch (UnknownAccountException e2) {
            return ResultFactory.buildFailResult("账号不存在");
        } catch (AuthenticationException e1) {
            String message = "密码错误";
            return ResultFactory.buildFailResult(message);
        }
        /*
        //没加密
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        User user = iUserService.getOne(wrapper);
//        System.out.println("login..........");
//        System.out.println("username:"+username);
//        System.out.println("password:"+password);
        log.info("login.........");
        log.info("username:"+username);
        log.info("password:"+password);
        if(user==null)
            return ResultFactory.buildFailResult("账号不存在");
        if(user.getPassword().equals(password)){
            user.setPassword("");
            return ResultFactory.buildSuccessResult(user);
        }
        else return ResultFactory.buildFailResult("密码错误");

         */
    }

    /**
     * 发送验证码到用户邮箱，进行登录
     *
     * @param email
     * @return
     */
    @GetMapping("/sendCode")
    public Result sendCode(@RequestParam String email) {
        System.out.println(email);
        code = Mail.codeGenerator(6);
        String verificationCode = "<p style=\"color: red\">" + code + "</p>";
        Mail mail = new Mail(email, verificationCode);
        mail.verificationCode = verificationCode;
        mail.receiveMailAccount = email;
        mail.forWhat = "登录";

        try {
            mail.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        TempCode tempCode = new TempCode(email,code);
//        List<TempCode> tempCodes = tempCodeMapper.queryTempCodeByUserEmail(email);
//
//        //更新数据库中的temp_code表
//        if(tempCodes.isEmpty()){
//            tempCodeMapper.addTempCode(tempCode);
//        }else {
//            tempCodeMapper.updateTempCode(tempCode);
//        }
        return ResultFactory.buildSuccessResult(null);
    }

    /**
     * 通过邮箱和验证码进行登录
     *
     * @param
     * @return
     */
    @PostMapping("/loginByVerification")
    public Result loginByVerification(@RequestBody JSONObject requestJson, HttpSession session) {
        String username = requestJson.getString("username");
        if (!requestJson.getString("code").equals(code)) {
            return ResultFactory.buildFailResult("验证码错误");
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = iUserService.getOne(wrapper);
        if (user == null) {
            user = new User();
            user.setUsername(username);
            iUserService.save(user);
        }
        return ResultFactory.buildSuccessResult(user);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        User one = iUserService.getOne(wrapper);
        String password = user.getPassword();
        if (one != null) {
            return ResultFactory.buildFailResult("该账号已存在");
        }
        // 生成盐,默认长度 16 位
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        // 设置 hash 算法迭代次数
        int times = 2;
        // 得到 hash 后的密码
        String encodedPassword = new SimpleHash("md5", password, salt, times).toString();
        // 存储用户信息，包括 salt 与 hash 后的密码
        user.setSalt(salt);
        user.setPassword(encodedPassword);
        iUserService.save(user);
        return ResultFactory.buildSuccessResult(user);
    }

    @GetMapping("/logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        String message = "成功登出";
        return ResultFactory.buildSuccessResult(message);
    }

    @GetMapping(value = "/authentication")
    public String authentication() {
        return "身份认证成功";
    }


}
