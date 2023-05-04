package com.wolf.service.impl;

import com.wolf.entity.Article;
import com.wolf.mapper.ArticleMapper;
import com.wolf.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wolf
 * @since 2023-05-04
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
