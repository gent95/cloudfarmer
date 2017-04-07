package com.jctl.cloud.manager.marketnews.service;
import com.jctl.cloud.manager.marketnews.entity.MktDyn;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by gent on 2017/3/23.
 */
public interface AnalylsisDocument {
    //解析市场动态
    List<MktDyn> analysisMktDyn(String areaId,HttpServletRequest request);
}
