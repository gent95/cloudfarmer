package com.jctl.cloud.manager.news.service;

import com.jctl.cloud.manager.news.entity.News;
import com.jctl.cloud.modules.sys.service.SystemService;
import com.jctl.cloud.utils.jsoup.JsoupUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/27.
 */
@Service
public class NewsServiceImpl implements INewsService{
    private static String BURL="http://pfsc.agri.cn/scdt/index";
    public List<News> newsData(HttpServletRequest request){
        List<News> lists=new ArrayList<News>();
       String url=BURL+".htm";
     //   for(int j=0;j<3;j++){
       //     if(j==0){
          //      url=BURL+".htm";
        //    }else{
        //        url=BURL+"_"+j+".htm";
       //     }
        Document doc= JsoupUtils.getDocument(url,request);
        Elements divlist=doc.select("body table:eq(1) tbody tr td:eq(1) table tbody tr:eq(2) td table tbody");
        Elements context=divlist.select("tr");
       for(int i=0;i<context.size();i++){
            News news=new News();
            news.setId(i+1);
            news.setTitle(divlist.select("tr:eq("+i+") td:eq(1) a").text());
            news.setDataTime(divlist.select("tr:eq("+i+") td:eq(2)").text());
            String ulrs=divlist.select("tr:eq("+i+") td:eq(1) a").attr("href");
            ulrs=ulrs.substring(1,ulrs.length());
           news.setUrl("http://pfsc.agri.cn/scdt"+ulrs);
           Document document=JsoupUtils.getDocument(news.getUrl(),request);
           Elements con_top=document.getElementsByClass("cont_midtop");
           String content=con_top.select("table:eq(1) tbody tr:eq(1) td div").text();
         news.setContent(content);
           lists.add(news);
        }
      //  }
        return lists;
    }
   /* public List<News> getDateString(){
        String url="http://search.sina.com.cn/?q=%E5%86%9C%E4%B8%9A%E5%8F%91%E5%B1%95&c=news&from=channel&ie=utf-8&col=&range=&source=&country=&size=&time=&a=&page=1&pf=2131425468&ps=2134309112&dpc=1";
        Document doc=JsoupUtils.getDocument(url);
        Elements con_top=doc.getElementsByClass("result");
        int newSize=con_top.select(".box-result").size();
        for(int i=0;i<newSize;i++){

        }
    }*/
}
