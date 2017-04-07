package com.jctl.cloud.api.news;

import com.google.common.collect.Maps;
import com.jctl.cloud.common.utils.Reflections;
import com.jctl.cloud.manager.news.entity.News;
import com.jctl.cloud.manager.news.service.NewsServiceImpl;
import com.jctl.cloud.mina.main.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/27.
 */
@Controller
@RequestMapping("news")
public class ANewsController {
    @Autowired
    private NewsServiceImpl newsService;

    @RequestMapping("list")
    @ResponseBody
    public Map listNews(HttpServletRequest request){
        Map result= Maps.newHashMap();
      try {
          List<News> list = newsService.newsData(request);
          if (list != null) {
          String[] proper=new String[]{"id","title","content","dataTime"};
          List newsList=new ArrayList();
          for(News news:list) {
              Map maps = Maps.newHashMap();
              for (String property : proper) {
                  maps.put(property, Reflections.invokeGetter(news,property));
              }
              newsList.add(maps);
          }

              result.put("info", newsList);
              result.put("flag", "1");
          } else {
              result.put("flag", "0");
              result.put("msg", "抱歉，没有查询到该数据");
          }
      }catch (Exception e){
          result.put("flag","0");
          result.put("msg","操作失败");
          e.printStackTrace();
      }
        return result;
    }


}
