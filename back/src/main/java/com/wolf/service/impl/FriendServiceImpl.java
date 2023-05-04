package com.wolf.service.impl;

import com.wolf.entity.Friend;
import com.wolf.mapper.FriendMapper;
import com.wolf.service.IFriendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wolf
 * @since 2023-04-23
 */
@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements IFriendService {

}
