package com.jctl.cloud.common.web;

import com.jctl.cloud.manager.region.entity.Region;
import com.jctl.cloud.utils.ChineseToPinyin;
import com.jctl.cloud.utils.WeatherUtils;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 天气预报接口
 * Created by LewKay on 2017/3/20.
 */
@Controller
public class WeatherController {

    @RequestMapping("weather")
    @ResponseBody
    public Map weather(Region region) throws Exception{
        Map result = new HashMap<>();
        region.setPinyin(ChineseToPinyin.getPingYin(region.getName()));
        try {
            Map map = null;
            if (region.getPinyin() != null) {
                map = WeatherUtils.getWeatherByName(region);
            }
            if (region.getCitycode() != null) {
                map = WeatherUtils.getWeather(region.getCitycode());
            }
            if (region.getName() != null) {
                map = WeatherUtils.getWeatherByName(region);
            }
            if (map == null || map.isEmpty()) {
                result.put("status", 0);
                return result;
            }
            
            result.put("info", map);
            result.put("status", 1);
        } catch (Exception e) {
            result.put("status", 0);
            e.printStackTrace();
        }
        return result;
    }
}
