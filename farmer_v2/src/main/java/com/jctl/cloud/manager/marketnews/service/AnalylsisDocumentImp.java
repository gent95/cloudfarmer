package com.jctl.cloud.manager.marketnews.service;

import com.jctl.cloud.manager.marketnews.entity.MktDyn;
import com.jctl.cloud.utils.jsoup.JsoupUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gent on 2017/3/23.
 */
@Service
public class AnalylsisDocumentImp implements AnalylsisDocument {
    @Override
    public List<MktDyn> analysisMktDyn(String areaId,HttpServletRequest request) {
        String url = "http://nc.mofcom.gov.cn/channel/gxdj/jghq/";
        Document document = JsoupUtils.getDocument(url,request);
        Elements area = document.select(".pmCon li");
        Elements context = document.select(".inCon tbody tr td");
        List<String> list = new ArrayList<String>();
        list.add("占位");
        String context1 = context.text();
        String[] strings = context1.split("   ");
        for (String string : strings) {
            String[] fields = string.split(" ");
            for (int i = 0; i < fields.length; i++) {

                if (!fields[i].equals("...")){
                    list.add(fields[i]);
                }else{
                    continue;
                }
            }
        }

        List<MktDyn> mktDyns = new ArrayList<MktDyn>();
        Map<String,Object> mktDynMp = new HashedMap();
        int i =1;
        int num = 1;
        while(i<197){
            MktDyn mktDyn = new MktDyn();
            mktDyn.setProductName(list.get(i));
            mktDyn.setPrice(list.get(++i));
            mktDyn.setMarketName(list.get(++i));
            mktDyn.setReleaseDate(list.get(++i));
            if (i%4==0) {
                mktDyns.add(mktDyn);
                if (mktDyns.size()==7) {
                        mktDynMp.put(num+"",mktDyns);
                        mktDyns = new ArrayList<>();
                        num++;
                }
                i++;
                continue;
            }
        }
        return (List)mktDynMp.get(areaId);
    }

//    public static void main(String[] args){
//        AnalylsisDocumentImp analylsisDocumentImp = new AnalylsisDocumentImp();
//        List<MktDyn> mktDyns =  analylsisDocumentImp.analysisMktDyn("3");
//        for (MktDyn mktDyn:mktDyns) {
//            System.out.print(mktDyn);
//        }
//
//
//        System.out.println(Thread.currentThread().getContextClassLoader().getResource("WEB-INF/proxyip.txt"));
//    }
}
