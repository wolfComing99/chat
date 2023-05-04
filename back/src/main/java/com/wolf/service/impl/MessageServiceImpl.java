package com.wolf.service.impl;

import com.wolf.entity.Message;
import com.wolf.mapper.MessageMapper;
import com.wolf.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wolf
 * @since 2023-04-28
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
