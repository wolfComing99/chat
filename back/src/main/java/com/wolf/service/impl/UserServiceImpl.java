package com.wolf.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wolf.entity.User;
import com.wolf.mapper.UserMapper;
import com.wolf.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wolf
 * @since 2023-04-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    UserMapper userMapper;
    //图片上传路径
    @Value("${file.upload.dir}")
    private String uploadDir;
    @Value("${server.port}")
    private String port;

//    public final static String TYPE_AVATAR = "avatar";
//    public final static String TYPE_PHOTO = "photo";
//    public final static String TYPE_CHAT_PHOTO = "chatPhoto";
//    public final static String TYPE_CHAT_FILE = "chatFile";


    public String uploadAvatar(MultipartFile file, int uid) throws IOException {
        String fileName = file.getOriginalFilename();
        System.out.println("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("上传的后缀名为：" + suffixName);
        fileName = "/avatar/avatar_" + uid + suffixName;
        String filePath = uploadDir;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        String url="";
        try {
            file.transferTo(dest);
            System.out.println("上传成功后的文件路径：" + filePath + fileName);

            String path = filePath + fileName;
            url = "http://localhost:" + port + "/upload" + fileName;

            System.out.println("url ---> {}"+url);

        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return url;
    }
}
