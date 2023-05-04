package com.wolf.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wolf.entity.User;
import com.wolf.result.Result;
import com.wolf.result.ResultFactory;
import com.wolf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wolf
 * @since 2023-04-24
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService iUserService;

    @PostMapping("/updateAvatar")
    public Result updateAvatar(@RequestBody JSONObject requestJson){
//        System.out.println(param);
//        Integer uid = (Integer)param.get("uid");
//        String avatar = (String)param.get("avatar");
        System.out.println(requestJson);
        Integer uid=requestJson.getInteger("uid");
        String avatar=requestJson.getString("avatar");
        System.out.println(uid+"...................");
        System.out.println(avatar+"...................");
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("uid", uid);
        updateWrapper.set("avatar", avatar);
        iUserService.update(null, updateWrapper);
        return ResultFactory.buildSuccessResult(avatar);
    }
    @PostMapping("/uploadAvatar")
    public Result uploadAvatar(@RequestParam(value = "uid") Integer uid,
                    @RequestParam(value = "file") MultipartFile file) throws IOException {
        String url = iUserService.uploadAvatar(file, uid);
        System.out.println("controller:"+url);
        return ResultFactory.buildSuccessResult(url);
    }
    @PostMapping("updateUser")
    public Result updateUser(@RequestBody User user){
        System.out.println(user);
//        return ResultFactory.buildSuccessResult(iUserService.updateById(user));
        if(iUserService.updateById(user))
            return ResultFactory.buildSuccessResult(user);
        else
            return ResultFactory.buildFailResult("保存失败");
    }
    @GetMapping("getByUid")
    public Result getByUid(@RequestParam(value = "uid") Integer uid){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("uid",uid);
        return ResultFactory.buildSuccessResult(iUserService.getOne(queryWrapper));
    }
}
