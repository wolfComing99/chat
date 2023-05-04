package com.wolf.service;

import com.wolf.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wolf
 * @since 2023-04-24
 */
public interface IUserService extends IService<User> {

    String uploadAvatar(MultipartFile file, int uid) throws IOException;
}
