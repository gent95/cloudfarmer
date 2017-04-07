package com.jctl.cloud.api.help;

import com.jctl.cloud.common.utils.Reflections;
import com.jctl.cloud.manager.helpmessage.entity.HelpMessage;
import com.jctl.cloud.manager.helpmessage.service.HelpMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/23.
 */

@Controller
@RequestMapping("help")
public class AHelpController {


    @Autowired
    private HelpMessageService helpMessageService;


    @RequestMapping("search")
    @ResponseBody
    public Map search(HelpMessage search) {
        Map result = new HashMap();
        try {
          List<HelpMessage> list = helpMessageService.findList(search);
          String [] propertys = new String[]{"id","question","answer"};
            if(list != null){
                List messages = new ArrayList();
                for (HelpMessage helpMessage: list) {
                    Map message = new HashMap();
                    for (String property :propertys) {
                        message.put(property, Reflections.invokeGetter(helpMessage,property));
                    }
                    messages.add(message);
                }
                result.put("info",messages);
            }else {
                result.put("flag", 0);
                result.put("msg", "很抱歉，暂未查询到这类问题的答案！您可以变换查询词语，再试一次！");
                return result;
            }
            result.put("flag", 1);
        } catch (Exception e) {
            result.put("flag", 0);
            result.put("msg", "操作失败");
            e.printStackTrace();
        }
        return result;
    }

}
