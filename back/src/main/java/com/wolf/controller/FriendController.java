package com.wolf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wolf.entity.Friend;
import com.wolf.entity.User;
import com.wolf.result.Result;
import com.wolf.result.ResultFactory;
import com.wolf.service.IFriendService;
import com.wolf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wolf
 * @since 2023-04-23
 */
@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    IFriendService iFriendService;
    @Autowired
    IUserService iUserService;
    @GetMapping("/getAll")
    public Result getAll(@RequestParam(value = "uid") Integer uid){
        List<User> friends=new ArrayList<>();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid",uid);
        List<Friend> friendsId = iFriendService.list(queryWrapper);
        for (Friend friend : friendsId) {
            System.out.println(friend.getFriendId());
            QueryWrapper queryWrapper2 = new QueryWrapper();
            queryWrapper2.eq("uid",friend.getFriendId());
            System.out.println(iUserService.getOne(queryWrapper2));
            friends.add(iUserService.getOne(queryWrapper2));
        }
        return ResultFactory.buildSuccessResult(friends);
    }
}
