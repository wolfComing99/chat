package com.wolf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wolf.entity.Message;
import com.wolf.result.Result;
import com.wolf.result.ResultFactory;
import com.wolf.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/message")
public class MessageController {
    @Autowired
    IMessageService iMessageService;
    @GetMapping("/getMessage")
    public Result getMessage(@RequestParam(value = "uid") Integer uid,@RequestParam(value = "friendId") Integer friendId){
        System.out.println("uid:"+uid);
        System.out.println("friendId:"+friendId);
        QueryWrapper<Message> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("send_id",uid);
        queryWrapper.eq("receive_id",friendId);
        queryWrapper.or();
        queryWrapper.eq("send_id",friendId);
        queryWrapper.eq("receive_id",uid);
//        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
//        queryWrapper.and(
//                qw -> qw.eq("send_id", uid)
//                        .eq("receive_id", friendId)
//        ).or(
//                qw -> qw.eq("send_id", friendId)
//                        .eq("receive_id", uid)
//        );
        List<Message> messageList = iMessageService.list(queryWrapper);
        System.out.println(messageList);
        return ResultFactory.buildSuccessResult(messageList);
//        return ResultFactory.buildSuccessResult(null);
    }
    @GetMapping("getNotRead")
    public Result getNotRead(@RequestParam(value = "uid") Integer uid){
        QueryWrapper<Message> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("receive_id",uid);
        queryWrapper.eq("is_read",0);
        List<Message> messageList = iMessageService.list(queryWrapper);
        System.out.println(messageList);
        return ResultFactory.buildSuccessResult(messageList);
    }
}