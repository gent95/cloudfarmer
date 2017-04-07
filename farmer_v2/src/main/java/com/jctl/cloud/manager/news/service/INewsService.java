package com.jctl.cloud.manager.news.service;

import com.jctl.cloud.manager.news.entity.News;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2017/3/27.
 */
public interface INewsService {
    //解析新闻
    List<News> newsData(HttpServletRequest request);
}
