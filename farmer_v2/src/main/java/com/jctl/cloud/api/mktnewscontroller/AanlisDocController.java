package com.jctl.cloud.api.mktnewscontroller;

import com.jctl.cloud.manager.marketnews.entity.MktDyn;
import com.jctl.cloud.manager.marketnews.service.AnalylsisDocumentImp;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by gent on 2017/3/24.
 */
@Controller
@RequestMapping("aAnlisDocController")
public class AanlisDocController {
    @Autowired
    private AnalylsisDocumentImp analylsisDocumentImp;

    @RequestMapping("getMktDyns")
    @ResponseBody
    public Map<String, Object> getMktDyns(String areaId, HttpServletRequest request) {
        List<MktDyn> mktDyns = analylsisDocumentImp.analysisMktDyn(areaId,request);
        Map<String,Object> result = new HashedMap();
        if (mktDyns!=null && Integer.parseInt(areaId) >0 && Integer.parseInt(areaId) <8){
            result.put("flag",1);
            result.put("mktDyns",mktDyns);
        }else{
            result.put("flag",0);
            result.put("mktDyns",null);
        }
        return result;
    }
}
